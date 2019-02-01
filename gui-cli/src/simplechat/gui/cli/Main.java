package simplechat.gui.cli;

import simplechat.core.SimpleChat;

import java.io.IOException;

public class Main
{
    public static void main(String... args) {
        Gui gui = new Gui();

        try {
            gui.init();
        } catch (IOException e) {
            System.out.println("Could not init gui!");
            e.printStackTrace();
        }

        SimpleChat chat = new SimpleChat();
        chat.registerEventListener(gui);
        chat.registerChatListener(gui.getChatWindow());
        chat.start();

        gui.waitUntilClosed();
    }
}
