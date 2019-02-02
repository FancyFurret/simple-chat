package main.java.simplechat.gui.cli;

import main.java.simplechat.core.SimpleChat;
import main.java.simplechat.core.interfaces.ISimpleChatController;

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

        ISimpleChatController chat = SimpleChat.newController();
        chat.registerListener(gui);
        chat.start();

        gui.waitUntilClosed();

        System.out.println("Cleaning up...");
        chat.stop();
    }
}
