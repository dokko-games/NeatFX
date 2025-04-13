package impl.dokko;

import com.dokko.neatfx.NeatFX;
import com.dokko.neatfx.NeatLogger;
import com.dokko.neatfx.core.window.Anchors;
import com.dokko.neatfx.core.window.Window;
import com.dokko.neatfx.core.window.element.impl.basic.Dropdown;
import com.dokko.neatfx.core.window.element.impl.basic.Panel;
import com.dokko.neatfx.core.window.element.impl.basic.Text;

public class SimpleApp {
    public void run(String[] args) {
        int monitorWidth = 1920;
        int monitorHeight = 1080;
        NeatFX.setDeveloperScreenSize(monitorWidth, monitorHeight);
        NeatFX.initialize(args);

        String windowTitle = "Simple Application";
        int windowWidth = 800;
        int windowHeight = 600;
        Window window = new Window(windowTitle, windowWidth, windowHeight);

        Panel leftPanel = new Panel(4, 4, 250, 592, Anchors.SCALE_TOP_LEFT);
        window.add(leftPanel);
        Text text = new Text("Please log in", 0, 10, 0, 0, Anchors.BOTTOM_CENTER_SCALE);
        leftPanel.addForeground(text);
        Dropdown user = Dropdown.from(14, 24, 0, 0, Anchors.TOP_LEFT, "Not logged in", "Dokko", "Owner", "Guest");
        user.setOnSelect(s -> {
            NeatLogger.info("Selected %{0}", s);
            if(s.equalsIgnoreCase("not logged in")){
                text.setText("Please log in");
            } else {
                text.setText("Welcome, "+s+"!");
            }
        });
        leftPanel.addForeground(user);
        window.renderLoop();
        window.cleanUp();
        NeatFX.exit(0);
    }
}
