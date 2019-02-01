package main.java.simplechat.gui.cli;

import com.googlecode.lanterna.gui2.*;
import main.java.simplechat.gui.cli.controls.UnfocusableTextBox;
import main.java.simplechat.core.Message;
import main.java.simplechat.core.User;
import main.java.simplechat.core.UserList;
import main.java.simplechat.core.interfaces.IChatListener;

import java.util.Collections;

class ChatWindow extends BasicWindow implements IChatListener {
    private UnfocusableTextBox chatTextBox;
    private TextBox newMessageTextBox;

    ChatWindow() {
        super("Simple Chat");
        setHints(Collections.singletonList(Hint.FULL_SCREEN));

        Panel panel = new Panel(new BorderLayout());
        Panel centerPanel = new Panel(new BorderLayout());

        chatTextBox = new UnfocusableTextBox();
        chatTextBox.setReadOnly(true);
        chatTextBox.setLayoutData(BorderLayout.Location.CENTER);

        centerPanel.addComponent(chatTextBox);
        centerPanel.setLayoutData(BorderLayout.Location.CENTER);
        panel.addComponent(centerPanel.withBorder(Borders.singleLineBevel()));

        newMessageTextBox = new TextBox();
        newMessageTextBox.setLayoutData(BorderLayout.Location.BOTTOM);
        newMessageTextBox.takeFocus();

        panel.addComponent(newMessageTextBox);
        setComponent(panel);
    }

    @Override
    public void userJoined(User user) {

    }

    @Override
    public void userLeft(User user) {

    }

    @Override
    public void newMessage(Message message) {
        chatTextBox.addLine(message.getContents());
    }

    public void welcome(UserList users) {
        chatTextBox.addLine("Welcome to Simple Chat! There are " + users.getAmount() + " users connected:");
        for (User user : users.getUsers())
            chatTextBox.addLine("* " + user.getName());

        chatTextBox.addLine("");
    }
}
