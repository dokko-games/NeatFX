package com.dokko.neatfx.engine.render.shader;

import com.dokko.neatfx.NeatLogger;
import com.dokko.neatfx.core.window.Window;
import com.dokko.neatfx.engine.util.NeatFiles;

import java.io.File;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private final int programId;
    private final Map<String, Integer> uniformLocations = new HashMap<>();

    public Shader(String vertexFile, String fragmentFile) {
        this(new File(vertexFile), new File(fragmentFile));
    }

    public Shader(File vertexFile, File fragmentFile) {
        // Read shader source
        String vertexSource = NeatFiles.read(vertexFile);
        String fragmentSource = NeatFiles.read(fragmentFile);

        // Compile shaders
        int vertexShader = compileShader(vertexSource, GL_VERTEX_SHADER);
        int fragmentShader = compileShader(fragmentSource, GL_FRAGMENT_SHADER);

        // Link program
        programId = glCreateProgram();
        glAttachShader(programId, vertexShader);
        glAttachShader(programId, fragmentShader);
        glLinkProgram(programId);

        // Check link status
        if (glGetProgrami(programId, GL_LINK_STATUS) == GL_FALSE) {
            throw new RuntimeException("Shader linking failed: " + glGetProgramInfoLog(programId));
        }

        // Cleanup
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
    }

    private int compileShader(String source, int type) {
        int shaderId = glCreateShader(type);
        glShaderSource(shaderId, source);
        glCompileShader(shaderId);

        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == GL_FALSE) {
            throw new RuntimeException("Shader compile failed: " + glGetShaderInfoLog(shaderId));
        }

        return shaderId;
    }
    public void updateDefaultUniforms() {
        setUniform("s_time", System.currentTimeMillis());
        setUniform("s_resolution", Window.getActiveWindow().getWidth(), Window.getActiveWindow().getHeight());
    }

    public void bind() {
        glUseProgram(programId);
    }

    public void unbind() {
        glUseProgram(0);
    }

    public void setUniform(String name, float value) {
        glUniform1f(getUniformLocation(name), value);
    }

    public void setUniform(String name, int value) {
        glUniform1i(getUniformLocation(name), value);
    }

    public void setUniform(String name, float x, float y) {
        glUniform2f(getUniformLocation(name), x, y);
    }

    public void setUniform(String name, float x, float y, float z) {
        glUniform3f(getUniformLocation(name), x, y, z);
    }

    public void setUniformMatrix4(String name, FloatBuffer matrix) {
        glUniformMatrix4fv(getUniformLocation(name), false, matrix);
    }

    private int getUniformLocation(String name) {
        if (uniformLocations.containsKey(name)) {
            return uniformLocations.get(name);
        }

        int location = glGetUniformLocation(programId, name);
        if (location == -1) {
            NeatLogger.warn("Uniform '%{0}' not found.", name);
        }

        uniformLocations.put(name, location);
        return location;
    }

    public void destroy() {
        glDeleteProgram(programId);
    }
}
