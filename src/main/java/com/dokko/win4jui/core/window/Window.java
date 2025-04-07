package com.dokko.win4jui.core.window;

import com.dokko.win4jui.Win4JUI;
import com.dokko.win4jui.Logger4JUI;
import com.dokko.win4jui.core.Input;
import com.dokko.win4jui.core.error.Error;
import com.dokko.win4jui.core.window.element.Element;
import com.dokko.win4jui.engine.Color4;
import com.dokko.win4jui.engine.render.Renderer;
import lombok.Getter;
import lombok.Setter;
import org.lwjgl.BufferUtils;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.Callback;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.nio.IntBuffer;
import java.util.ArrayList;

/**
 * A class that represents a Window
 */
@Getter
@Setter
public class Window {
    public static ArrayList<Window> windowsToRemove;
    public static ArrayList<Window> registeredWindows;
    /**
     * The OpenGL handle to the window
     */
    private long window;
    /**
     * The title of the window
     */
    private String title;
    /**
     * The default width of the window
     */
    private int targetWidth;
    /**
     * The default height of the window
     */
    private int targetHeight;
    /**
     * The content of the window, represented as an element
     */
    private Element content;
    /**
     * If true, then the window was closed manually by pressing the close button. Otherwise, it was closed through an error or {@link Win4JUI#exit(int)}
     */
    private boolean closing;
    private boolean cleaned;

    public Window(String title, int width, int height) {
        if(Win4JUI.getDeveloperScreenSize() == null){
            throw new Error("Win4JUI Error", "Win4JUI was not initialized. Run Win4JUI.initialize()", "User error").getException();
        }
        registeredWindows.add(this);
        int iw = width, ih = height;
        if(Win4JUI.getDeveloperScreenSize().getWidth() != 0){
            float initialScalingFactorWidth = (float) Win4JUI.getScreenWidth() / Win4JUI.getDeveloperScreenSize().width;
            float initialScalingFactorHeight = (float) Win4JUI.getScreenHeight() / Win4JUI.getDeveloperScreenSize().height;
            width = (int) (width * initialScalingFactorWidth);
            height = (int) (height * initialScalingFactorHeight);
        }
        setTitle(title);
        setTargetWidth(width);
        setTargetHeight(height);

        Logger4JUI.infoDebug("Creating window with title \"%{0}\"", title);
        initialize();
        Logger4JUI.infoDebug("Created window (handle %{0})", window);
        Logger4JUI.infoDebug("Size: X - %{0}/%{1} Y - %{2}/%{3}", width, iw, height, ih);

        content = new Element(0, 0, 0, 0, Anchors.TOP_LEFT) {
            @Override
            protected void doRender(float scalingFactorW, float scalingFactorH) {
                for(Element element : getForegroundChildren()) {
                    element.setParent(null);
                }
                for(Element element : getBackgroundChildren()) {
                    element.setParent(null);
                }
                Renderer.color(Color4.DARK_GRAY.darker(1));
                Renderer.rect(0, 0, Renderer.getWindowWidth(), Renderer.getWindowHeight());
                Renderer.color(Color4.BLACK);
            }
        };
    }
    /**
     * Cleans up code created from the OpenGL and Win4JUI frameworks
     */
    public void cleanUp() {
        if(isCleaned())return;
        content.cleanUp();
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        Logger4JUI.infoDebug("Window destroyed");

        // Terminate GLFW and free the error callback
        glfwTerminate();
        Logger4JUI.infoDebug("Terminated GLFW");
        Callback callback = glfwSetErrorCallback(null);
        if(callback == null) throw new Error("OpenGL Error", "Couldn't set error callback", "GLFW.glfwSetErrorCallback").getException();
        callback.free();
        Logger4JUI.infoDebug("Callbacks freed successfully");
        windowsToRemove.add(this);
        setCleaned(true);
    }

    /**
     * The last dimensions of the screen, used to update the viewport when it is resized
     */
    int lastWidth = -1, lastHeight = -1;

    /**
     * Initializes the window and the OpenGL renderer
     */
    public void initialize() {
        // Set up an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();
        if(!glfwInit()) {
            throw new Error("OpenGL Error", "Couldn't initialize GLFW", "GLFW.glfwInit").getException();
        }
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        window = glfwCreateWindow(targetWidth, targetHeight, title, 0, 0);
        if(window == 0) {
            throw new Error("OpenGL Error", "Couldn't initialize window", "GLFW.glfwCreateWindow").getException();
        }

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            if(vidMode == null){
                throw new Error("OpenGL Error", "Couldn't initialize video mode", "GLFW.glfwGetVideoMode").getException();
            }

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidMode.width() - pWidth.get(0)) / 2,
                    (vidMode.height() - pHeight.get(0)) / 2
            );
            glfwSetCursorPosCallback(window, (win, xpos, ypos) -> {
                Input.setMouseX((int)xpos);
                Input.setMouseY((int)ypos);
            });

            // Initialize GLFW input callbacks
            glfwSetKeyCallback(window, new GLFWKeyCallback() {
                @Override
                public void invoke(long window, int key, int scancode, int action, int mods) {
                    if (action == GLFW_PRESS) {
                        Input.getJustPressedKeys().add(key);
                        Input.getCurrentPressedKeys().add(key);
                    } else if (action == GLFW_RELEASE) {
                        Input.getJustReleasedKeys().add(key);
                        Input.getCurrentPressedKeys().remove(key);
                        Input.getJustPressedKeys().remove(key);  // Remove from just pressed as it was released
                    }
                }
            });
            glfwSetMouseButtonCallback(window, new GLFWMouseButtonCallback() {
                @Override
                public void invoke(long window, int button, int action, int mods) {
                    if (action == GLFW_PRESS) {
                        Input.getJustPressedButtons().add(button);
                        Input.getCurrentPressedButtons().add(button);
                    } else if (action == GLFW_RELEASE) {
                        Input.getJustReleasedButtons().add(button);
                        Input.getCurrentPressedButtons().remove(button);
                        Input.getJustPressedButtons().remove(button);  // Remove from just pressed as it was released
                    }
                }
            });

        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

        // creates the GLCapabilities instance and makes the OpenGL bindings available for use.
        GL.createCapabilities();
        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // Enable blending (for transparency)
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    /**
     * Starts rendering the window. Keep in mind this code is a loop and will only finish running when the window is closed.
     */
    public void renderLoop() {
        while ( !glfwWindowShouldClose(window) ) {
            int width = getSize().width;
            int height = getSize().height;
            // Only update the projection if the size actually changed
            if (width != lastWidth || height != lastHeight) {
                glViewport(0, 0, width, height);
                glMatrixMode(GL_PROJECTION);
                glLoadIdentity();
                glOrtho(0, width, height, 0, -1, 1);
                glMatrixMode(GL_MODELVIEW);

                lastWidth = width;
                lastHeight = height;
            }

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the frame buffer

            doRender();

            glfwSwapBuffers(window); // swap the color buffers

            Input.update();

            glfwPollEvents();
        }
        setClosing(true);
    }

    /**
     * Actually renders the content
     */
    private void doRender() {
        float scalingFactorW = (float) getSize().width / targetWidth;
        float scalingFactorH = (float) getSize().height / targetHeight;
        content.setX(0).setY(0).setWidth(getSize().width).setHeight(getSize().height);
        Renderer.setWindowWidth(getSize().width);
        Renderer.setWindowHeight(getSize().height);
        content.render(content.getX(), content.getY(), content.getWidth(), content.getHeight(), scalingFactorW, scalingFactorH);
        content.input();
    }

    /**
     * Returns the size of the screen
     * @return the size of the screen
     */
    public Dimension getSize() {
        IntBuffer w = BufferUtils.createIntBuffer(1);
        IntBuffer h = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(window, w, h);
        int width = w.get(0);
        int height = h.get(0);
        return new Dimension(width, height);
    }

    /**
     * Adds an element to the content
     * @param element the element to add
     */
    public void add(Element element){
        content.addForeground(element);
    }

    /**
     * Removes all closed windows from the registry
     */
    public void updateWindows() {
        for(Window w : windowsToRemove){
            registeredWindows.remove(w);
        }
        windowsToRemove.clear();
    }

    /**
     * Sets the windows' visibility
     * @param visible true if visible
     */
    public void setVisible(boolean visible){
        if(visible){
            glfwShowWindow(window);
        } else {
            glfwHideWindow(window);
        }
    }

    /**
     * Cleans the window
     */
    public void destroy() {
        cleanUp();
        setVisible(false);
        updateWindows();
    }
}
