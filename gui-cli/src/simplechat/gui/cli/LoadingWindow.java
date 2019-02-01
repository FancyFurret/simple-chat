package simplechat.gui.cli;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;

import java.util.Collections;

public class LoadingWindow extends BasicWindow {
    LoadingWindow() {
        setHints(Collections.singletonList(Hint.CENTERED));

        Panel panel = new Panel();
        panel.addComponent(new Label("Loading..."));
        setComponent(panel);
    }
}
