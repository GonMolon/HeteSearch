package presentation;

import domain.NodeType;
import presentation.utils.NodeTextField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RelationalSearchPanel extends JPanel implements ActionListener {

    private PresentationController presentationController;
    private JPanel panel;
    private NodeTextField fromText;
    private NodeTextField toText;

    public RelationalSearchPanel(PresentationController presentationController) {
        this.presentationController = presentationController;
        add(panel);
    }

    public void reset() {
        fromText.reset();
        toText.reset();
    }

    public void createUIComponents() {
        fromText = new NodeTextField(presentationController, "Type an origin    ");
        toText = new NodeTextField(presentationController, "Type a destination");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void setNodeTypeFrom(NodeType nodeTypeFrom) {
        fromText.setNodeType(nodeTypeFrom);
    }

    public void setNodeTypeTo(NodeType nodeTypeTo) {
        toText.setNodeType(nodeTypeTo);
    }
}
