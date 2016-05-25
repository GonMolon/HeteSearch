package presentation;

import presentation.utilities.AutoClearTextField;

import javax.swing.*;

public class SimpleSearchPanel extends JPanel {

    private JPanel panel;
    private AutoClearTextField filterText;

    public SimpleSearchPanel() {
        add(panel);
        setEnabled(false);
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        filterText.setEnabled(enabled);
    }

    public void createUIComponents() {
        filterText = new AutoClearTextField("Type a name");
    }
}
