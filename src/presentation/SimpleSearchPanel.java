package presentation;

import domain.NodeType;
import presentation.utils.AutoClearTextField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SimpleSearchPanel extends JPanel implements ActionListener {

    private PresentationController presentationController;
    private SearchResults searchResults;
    private JPanel panel;
    private AutoClearTextField filterText;
    private NodeType nodeType;

    public SimpleSearchPanel(PresentationController presentationController, PathGenerator pathGenerator, SearchResults searchResults) {
        this.presentationController = presentationController;
        this.searchResults = searchResults;
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
        searchResults.setResults(presentationController.simpleSearch(nodeType, filterText.getText()));
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }
}