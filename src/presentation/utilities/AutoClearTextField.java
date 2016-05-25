package presentation.utilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * Created by daniconde on 24/05/2016.
 */
public class AutoClearTextField extends JTextField {

    public AutoClearTextField(String title) {
        super(title);
        this.setForeground(Color.GRAY);
        AutoClearTextField textField = this;
        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.setForeground(Color.BLACK);
                if (textField.getText().equals(title))
                    textField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().equals("")) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(title);
                }
            }
        });
    }
}
