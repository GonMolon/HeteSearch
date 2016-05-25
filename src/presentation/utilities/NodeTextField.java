package presentation.utilities;


import domain.GraphSearch;
import domain.Node;
import domain.NodeType;
import presentation.PresentationController;

import java.awt.event.FocusEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class NodeTextField extends AutoClearTextField {

    private PresentationController presentationController;
    private NodeType nodeType;
    public int id;

    public NodeTextField(PresentationController presentationController, String title) {
        super(title);
        this.presentationController = presentationController;
        id = -1;
    }

    public void reset() {
        id = -1;
        setText("");
        super.focusLost(null);
    }

    public void focusLost(FocusEvent e) {
        ArrayList<GraphSearch.Result> results = presentationController.simpleSearch(nodeType, getText());
        if(results.size() == 0 || getText().length() == 0) {
            id = -1;
            setText("");
            super.focusLost(e);
        } else if(results.size() == 1) {
            id = results.get(0).from.getId();
            setText(results.get(0).from.getValue());
        } else {
            ArrayList<Integer> ids = new ArrayList<Integer>();
            ArrayList<String> names = new ArrayList<String>();
            for(int i = 0; i < results.size(); ++i) {
                Node node = results.get(i).from;
                ids.add(node.getId());
                names.add(node.getValue());
            }
            InstanceSelectionDialog instanceSelectionDialog = new InstanceSelectionDialog(ids, names, "Pick an element", "You need to specify the node.\nPick the one you want:");
            id = instanceSelectionDialog.getIdChosen();
            setText(presentationController.getNodeValue(nodeType, id));
        }
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
        ArrayList<GraphSearch.Result> result = presentationController.simpleSearch(nodeType, getText());
        if(result.size() != 1) {
            reset();
        }
    }
}
