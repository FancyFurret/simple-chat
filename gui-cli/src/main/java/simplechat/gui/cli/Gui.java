package main.java.simplechat.gui.cli;

import com.googlecode.lanterna.bundle.LanternaThemes;
import com.googlecode.lanterna.graphics.PropertyTheme;
import com.googlecode.lanterna.gui2.AbstractTextGUI;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import main.java.simplechat.core.UserList;
import main.java.simplechat.core.interfaces.ISimpleChatEventListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class Gui implements ISimpleChatEventListener {

    private MultiWindowTextGUI gui;

    private ChatWindow chatWindow;

    void init() throws IOException {
        Screen screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();

        Properties themeProperties = loadPropTheme("simplechat-theme.properties");
        if (themeProperties != null)
            LanternaThemes.registerTheme("simplechat", new PropertyTheme(themeProperties));

        gui = new MultiWindowTextGUI(screen);
        gui.setTheme(LanternaThemes.getRegisteredTheme("simplechat"));
        gui.addWindow(new LoadingWindow());

        chatWindow = new ChatWindow();
    }

    ChatWindow getChatWindow() {
        return chatWindow;
    }

    void waitUntilClosed() {
        gui.getActiveWindow().waitUntilClosed();
    }

    @Override
    public void started(UserList users) {
        gui.addWindow(chatWindow);
        chatWindow.welcome(users);
    }

    private static Properties loadPropTheme(String resourceFileName) {
        Properties properties = new Properties();
        try {
            ClassLoader classLoader = AbstractTextGUI.class.getClassLoader();
            InputStream resourceAsStream = classLoader.getResourceAsStream(resourceFileName);
            if (resourceAsStream == null)
                resourceAsStream = new FileInputStream("gui-cli/src/main/resources/" + resourceFileName);

            properties.load(resourceAsStream);
            resourceAsStream.close();
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
