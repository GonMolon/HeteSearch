package presentation.utils;


import domain.NodeType;
import javafx.event.ActionEvent;
import presentation.PresentationController;
import scala.Int;

import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class NodeTextField extends AutoClearTextField implements ActionListener {

    private PresentationController presentationController;
    private NodeType nodeType;
    public int id;

    public NodeTextField(PresentationController presentationController, String title) {
        super(title);
        this.presentationController = presentationController;
        addActionListener(this);
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
        Integer[] results = presentationController.simpleSearch(nodeType, getText());
        if(results.length == 0 || getText().length() == 0) {
            id = -1;
            setText("");
            super.focusLost(e);
        } else if(results.length == 1) {
            id = results[0];
            setText(presentationController.getNodeValue(nodeType, id));
        } else {
            ArrayList<Integer> ids = new ArrayList<Integer>();
            ArrayList<String> names = new ArrayList<String>();
            for(int i = 0; i < results.length; ++i) {
                ids.add(results[i]);
                names.add(presentationController.getNodeValue(nodeType, results[i]));
            }
            InstanceSelectionDialog instanceSelectionDialog = new InstanceSelectionDialog(ids, names, "Pick an element", "You need to specify the node.\nPick the one you want:");
            id = instanceSelectionDialog.getIdChosen();
            setText(presentationController.getNodeValue(nodeType, id));
        }
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
        if(presentationController.simpleSearch(nodeType, getText()).length != 1) {
            reset();
        }
    }

    public void actionPerformed(java.awt.event.ActionEvent e) {
        focusLost(null);
    }
}
