package presentation;

import javax.swing.*;

public class RelationalSearchPanel extends JPanel {

    private PresentationController presentationController;
    private JPanel panel;
    private JFormattedTextField formattedTextField1;
    private JFormattedTextField formattedTextField2;

    public RelationalSearchPanel(PresentationController presentationController) {
        this.presentationController = presentationController;
        add(panel);
    }
}
