package presentation;

import domain.NodeType;
import presentation.utils.AutoClearTextField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SimpleSearchPanel extends JPanel implements ActionListener {

    private JPanel panel;
    private AutoClearTextField filterText;
    private NodeType nodeType;
    private PresentationController presentationController;

    public SimpleSearchPanel(PresentationController presentationController) {
        this.presentationController = presentationController;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<Integer> results = presentationController.simpleSearch(nodeType, filterText.getText());
        for(int i = 0; i < results.size(); ++i) {
            System.out.println(results.get(i));
        }
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }
}