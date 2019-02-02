package main.java.simplechat.gui.cli.controls;

import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.util.ArrayList;

public class SubmittableTextBox extends TextBox {

    private ArrayList<Runnable> listeners;

    public SubmittableTextBox() {
        listeners = new ArrayList<>();
    }

    @Override
    public synchronized Result handleKeyStroke(KeyStroke keyStroke) {
        if (keyStroke.getKeyType() == KeyType.Enter) {
            for (Runnable listener : listeners)
                listener.run();
            return Result.HANDLED;
        }

        return super.handleKeyStroke(keyStroke);
    }

    public void addListener(Runnable listener) {
        listeners.add(listener);
    }

    public void removeListener(Runnable listener) {
        listeners.remove(listener);
    }
}
