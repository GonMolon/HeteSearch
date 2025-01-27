package presentation;

import domain.NodeType;
import presentation.utils.NodeTextField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RelationalSearchPanel extends JPanel implements ActionListener {

    private PresentationController presentationController;
    private PathGenerator pathGenerator;
    private SearchResultsPanel searchResultsPanel;
    private JPanel panel;
    private NodeTextField fromText;
    private NodeTextField toText;

    public RelationalSearchPanel(PresentationController presentationController, PathGenerator pathGenerator, SearchResultsPanel searchResultsPanel) {
        this.presentationController = presentationController;
        this.pathGenerator = pathGenerator;
        this.searchResultsPanel = searchResultsPanel;
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
        if(pathGenerator.actualRS.size() >= 1) {
            if(fromText.id == -1 && toText.id == -1) {
                searchResultsPanel.setResults(presentationController.freeSearch(pathGenerator.from, pathGenerator.actualRS, pathGenerator.to), pathGenerator.from, pathGenerator.to);
            } else if(fromText.id != -1 && toText.id == -1) {
                searchResultsPanel.setResults(presentationController.originSearch(pathGenerator.from, fromText.id, pathGenerator.actualRS, pathGenerator.to), pathGenerator.from, pathGenerator.to);
            } else if(fromText.id != -1 && toText.id != -1) {
                searchResultsPanel.setResults(presentationController.originDestinationSearch(pathGenerator.from, fromText.id, pathGenerator.actualRS, pathGenerator.to, fromText.id), pathGenerator.from, pathGenerator.to);
            }
        }
    }

    public void setNodeTypeFrom(NodeType nodeTypeFrom) {
        fromText.setNodeType(nodeTypeFrom);
    }

    public void setNodeTypeTo(NodeType nodeTypeTo) {
        toText.setNodeType(nodeTypeTo);
    }
}
