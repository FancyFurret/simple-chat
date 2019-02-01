package simplechat.gui.cli;

import com.googlecode.lanterna.bundle.LanternaThemes;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import simplechat.core.UserList;
import simplechat.core.interfaces.ISimpleChatEventListener;

import java.io.IOException;

class Gui implements ISimpleChatEventListener {

    private MultiWindowTextGUI gui;

    private ChatWindow chatWindow;

    void init() throws IOException {
        Screen screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();

        for (String theme : LanternaThemes.getRegisteredThemes()) {
            System.out.println(theme);
        }

        gui = new MultiWindowTextGUI(screen);
        gui.setTheme(LanternaThemes.getRegisteredTheme("businessmachine"));
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
}
