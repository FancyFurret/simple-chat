package main.java.simplechat.gui.cli.controls;

import com.googlecode.lanterna.gui2.TextBox;

public class UnfocusableTextBox extends TextBox {
    @Override
    public boolean isFocusable() {
        return false;
    }
}
