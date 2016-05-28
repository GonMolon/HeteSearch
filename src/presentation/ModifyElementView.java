package presentation;

import domain.NodeType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ModifyElementView extends ElementInfoView {

    static final private String title = "Modify element";

    public ModifyElementView(PresentationController presentationController, Component parentComponent, int nodeId, NodeType nodeType) {
        super(presentationController, parentComponent, title);
        selectType.setSelectedIndex(nodeType.ordinal());
        selectType.setEnabled(false);
        this.nodeId = nodeId;
        this.type = nodeType;
        fieldName.setText(presentationController.getNodeValue(type, nodeId));
        populateListModels();
    }

    private void populateListModels() {
        for (int i = 0; i < relationTypeIds.size(); ++i) {
            int relationTypeId = relationTypeIds.get(i);
            DefaultListModel<ElementInfoView.Element> model = listModels.get(i);
            NodeType toType = presentationController.getNodeTypeTo(relationTypeId, type);
            ArrayList<Integer> connectedIds = presentationController.getEdges(relationTypeId, nodeId, type);
            for (int id : connectedIds) {
                String eValue = presentationController.getNodeValue(toType, id);
                Element e = new Element(id, toType, eValue);
                model.addElement(e);
            }
        }
    }

    @Override
    protected void onOK() {
        String desiredName = fieldName.getText();
        if (!desiredName.equals("") && !desiredName.equals(fieldName.getTitle())) {
            presentationController.setNodeValue(type, nodeId, desiredName);
            super.updateNodeRelations();
            super.onOK();
        }
    }
}
