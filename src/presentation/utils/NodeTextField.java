package presentation.utils;


import domain.NodeType;
import presentation.PresentationController;

import java.awt.event.FocusEvent;
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

    public NodeTextField(PresentationController presentationController, String title, NodeType nodeType) {
        this(presentationController, title);
        this.nodeType = nodeType;
    }

    public void reset() {
        id = -1;
        setText("");
        super.focusLost(null);
    }

    public void focusLost(FocusEvent e) {
        ArrayList<Integer> results = presentationController.simpleSearch(nodeType, getText());
        if(results.size() == 0 || getText().length() == 0) {
            id = -1;
            setText("");
            super.focusLost(e);
        } else if(results.size() == 1) {
            id = results.get(0);
            setText(presentationController.getNodeValue(nodeType, id));
        } else {
            ArrayList<String> names = new ArrayList<String>();
            for(int i = 0; i < results.size(); ++i) {
                names.add(presentationController.getNodeValue(nodeType, results.get(i)));
            }
            InstanceSelectionDialog instanceSelectionDialog = new InstanceSelectionDialog(results, names, "Pick an element", "You need to specify the node.\nPick the one you want:");
            id = instanceSelectionDialog.getIdChosen();
            setText(presentationController.getNodeValue(nodeType, id));
        }
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
        if(presentationController.simpleSearch(nodeType, getText()).size() != 1) {
            reset();
        }
    }
}
