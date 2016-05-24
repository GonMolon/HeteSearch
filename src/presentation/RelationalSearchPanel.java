package presentation;

import javax.swing.*;

public class RelationalSearchPanel extends JPanel {

    private PresentationController presentationController;
    private JPanel panel;

    public RelationalSearchPanel(PresentationController presentationController) {
        this.presentationController = presentationController;
        add(panel);
    }
}
