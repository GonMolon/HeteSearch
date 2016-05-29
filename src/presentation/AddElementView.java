package presentation;

import domain.NodeType;

import java.awt.*;

public class AddElementView extends ElementInfoView {

    static private final String title = "Add element";

    public AddElementView(PresentationController presentationController, Component parentComponent) {
        super(presentationController, parentComponent, title);
    }

    public AddElementView(PresentationController presentationController, Component parentComponent, NodeType nodeType) {
        super(presentationController, parentComponent, title);
        selectType.setSelectedIndex(nodeType.ordinal());
        selectType.setEnabled(false);
    }

    @Override
    protected void onOK() {
        String desiredName = fieldName.getText();
        if (!desiredName.equals("") && !desiredName.equals(fieldName.getTitle())) {
            super.createNode();
            super.updateNodeRelations();
            super.onOK();
            presentationController.mainFrame.resetPathGenerator();
        }
    }

    @Override
    protected void onCancel() {
        super.onCancel();
        presentationController.mainFrame.resetPathGenerator();
    }
}
