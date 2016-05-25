package presentation.utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class AutoClearTextField extends JTextField implements FocusListener {

    private String title;

    public AutoClearTextField(String title) {
        super(title);
        this.title = title;
        addFocusListener(this);
        setFocusLost();
    }

    private void focusLost() {
        setForeground(Color.GRAY);
        setText(title);
    }

    @Override
    public void focusGained(FocusEvent e) {
        setForeground(Color.BLACK);
        if (getText().equals(title)) {
            setText("");
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (getText().equals("")) {
            setFocusLost();
        }
    }

    private void setFocusLost() {
        setForeground(Color.GRAY);
        setText(title);
    }
}
