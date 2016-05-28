package presentation;

import domain.NodeType;

import javax.swing.*;
import java.awt.*;

public class ModifyElementView extends ElementInfoView {

    static final private String title = "Modify element";

    public ModifyElementView(PresentationController presentationController, Component parentComponent, int nodeId, NodeType nodeType) {
        super(presentationController, parentComponent, title);
        selectType.setSelectedIndex(nodeType.ordinal());
        selectType.setEnabled(false);
        this.nodeId = nodeId;
        this.type = nodeType;
        populateListModels();
    }

    private void populateListModels() {
        for (int i = 0; i < relationTypeIds.size(); ++i) {
            DefaultListModel<ElementInfoView.Element> model = listModels.get(i);

        }
    }

    @Override
    protected void onOK() {
        String desiredName = fieldName.getText();
        if (!desiredName.equals("") && !desiredName.equals(fieldName.getTitle())) {
            super.updateNodeRelations();
            super.onOK();
        }
    }
}
