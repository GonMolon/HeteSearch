package presentation;

import presentation.utilities.NodeTextField;

import javax.swing.*;

public class RelationalSearchPanel extends JPanel {

    private PresentationController presentationController;
    private JPanel panel;
    private NodeTextField fromText;
    private NodeTextField toText;

    public RelationalSearchPanel(PresentationController presentationController) {
        this.presentationController = presentationController;
        add(panel);
    }

    public void createUIComponents() {
        fromText = new NodeTextField("Type an origin    ");
        toText = new NodeTextField("Type a destination ");
    }
}
