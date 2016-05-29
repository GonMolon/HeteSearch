package presentation;

import domain.NodeType;
import presentation.utils.NodeTextField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RelationalSearchPanel extends JPanel implements ActionListener {

    private PresentationController presentationController;
    private PathGenerator pathGenerator;
    private SearchResults searchResults;
    private JPanel panel;
    private NodeTextField fromText;
    private NodeTextField toText;

    public RelationalSearchPanel(PresentationController presentationController, PathGenerator pathGenerator, SearchResults searchResults) {
        this.presentationController = presentationController;
        this.pathGenerator = pathGenerator;
        this.searchResults = searchResults;
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
        if(fromText.id == -1 && toText.id == -1) {
            searchResults.setResults(presentationController.freeSearch(pathGenerator.from, pathGenerator.actualRS, pathGenerator.to));
        } else if(fromText.id != -1 && toText.id == -1) {
            ArrayList<Number[]> results = presentationController.originSearch(pathGenerator.from, fromText.id, pathGenerator.actualRS, pathGenerator.to);
            searchResults.setResults(results);
        } else if(fromText.id != -1 && toText.id != -1) {
            searchResults.setResults(fromText.id, toText.id, presentationController.originDestinationSearch(pathGenerator.from, fromText.id, pathGenerator.actualRS, pathGenerator.to, fromText.id));
        }
    }

    public void setNodeTypeFrom(NodeType nodeTypeFrom) {
        fromText.setNodeType(nodeTypeFrom);
    }

    public void setNodeTypeTo(NodeType nodeTypeTo) {
        toText.setNodeType(nodeTypeTo);
    }
}
