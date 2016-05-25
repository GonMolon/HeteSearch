package presentation;

import presentation.utilities.AutoClearTextField;

import javax.swing.*;

public class RelationalSearchPanel extends JPanel {

    private PresentationController presentationController;
    private JPanel panel;
    private AutoClearTextField fromText;
    private AutoClearTextField toText;

    public RelationalSearchPanel(PresentationController presentationController) {
        this.presentationController = presentationController;
        add(panel);
    }

    public void createUIComponents() {
        fromText = new AutoClearTextField("Type an origin    ");
        toText = new AutoClearTextField("Type a destination ");
    }
}
