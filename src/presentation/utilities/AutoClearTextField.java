package presentation.utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class AutoClearTextField extends JTextField {

    private String title;

    public AutoClearTextField(String title) {
        super(title);
        this.title = title;
        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                AutoClearTextField textField = (AutoClearTextField) e.getComponent();
                textField.setForeground(Color.BLACK);
                if (textField.getText().equals(title)) {
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                AutoClearTextField textField = (AutoClearTextField) e.getComponent();
                if (textField.getText().equals("")) {
                    textField.focusLost();
                }
            }
        });
    }

    private void focusLost() {
        setForeground(Color.GRAY);
        setText(title);
    }
}
