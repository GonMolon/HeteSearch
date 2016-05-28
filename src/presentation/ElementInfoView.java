package presentation;

import domain.Element;
import domain.NodeType;
import presentation.utils.AutoClearTextField;
import presentation.utils.NodeTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ElementInfoView extends JDialog {
    private final int minimumHeight = 400;
    private final int minimumWidth = 300;

    protected int nodeId;
    protected NodeType type;
    protected Integer relationTypeId;
    protected ArrayList<Integer> relationTypeIds;
    protected ArrayList<RelationAction> actions;

    protected PresentationController presentationController;
    Component parentComponent;

    protected JPanel contentPane;
    protected AutoClearTextField fieldName;
    protected JButton buttonAddRelation;
    protected JButton buttonDeleteRelation;
    protected JButton buttonOk;
    protected JButton buttonCancel;
    protected JComboBox<NodeType> selectType;
    protected JComboBox<String> selectRelationType;
    private NodeTextField fieldOtherNodeName;
    protected JList<ElementInfoView.Element> listRelations;
    protected DefaultListModel<ElementInfoView.Element> currentModel;
    protected ArrayList<DefaultListModel<ElementInfoView.Element>> listModels;

    protected ElementInfoView(PresentationController presentationController, Component parentComponent) {
        super(null, "Modify element", ModalityType.APPLICATION_MODAL);
        this.presentationController = presentationController;
        this.parentComponent = parentComponent;
        initialize();
    }

    private void initialize() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOk);
        setMinimumSize(new Dimension(minimumWidth, minimumHeight));
        setLocationRelativeTo(parentComponent);
        setLocation(getLocation().x - minimumWidth/2, getLocation().y - minimumHeight/2);

        listModels = new ArrayList<>();
        selectType.setModel(new DefaultComboBoxModel<>(NodeType.values()));
        onSelectType();

        selectType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSelectType();
            }
        });

        selectRelationType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSelectRelationType();
            }
        });

        buttonAddRelation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onAdd();
            }
        });

        buttonOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

    }

    protected void createNode() {
        int typeIndex = selectType.getSelectedIndex();
        type = NodeType.values()[typeIndex];
        String name = fieldName.getText();
        nodeId = presentationController.addNode(type, name);
    }

    protected void updateNodeRelations() {
        for (RelationAction a : actions) {
            if (a.action == RelationAction.ADD) {
                presentationController.addEdge(a.relationType, type, nodeId, a.typeB, a.nodeB);
            }
            else /*if (a.action == RelationAction.DELETE)*/{
                presentationController.removeEdge(a.relationType, type, nodeId, a.typeB, a.nodeB);
            }
        }
    }

    private void createUIComponents() {
        fieldName = new AutoClearTextField("Insert element name");
        fieldOtherNodeName = new NodeTextField(presentationController, "Insert other node name", NodeType.AUTHOR);
    }

    private void onSelectType() {
        type = (NodeType) selectType.getSelectedItem();

        listModels.clear();
        relationTypeIds = presentationController.getRelations(type);
        String[] relationNames = new String[relationTypeIds.size()];
        for (int i = 0; i < relationTypeIds.size(); ++i) {
            listModels.add(new DefaultListModel<>());
            String relationName = presentationController.getRelationName(relationTypeIds.get(i));
            relationNames[i] = relationName;
        }
        selectRelationType.setModel(new DefaultComboBoxModel<>(relationNames));
        onSelectRelationType();
    }

    private void onSelectRelationType() {
        relationTypeId = relationTypeIds.get(selectRelationType.getSelectedIndex());
        currentModel = listModels.get(selectRelationType.getSelectedIndex());
        listRelations.setModel(currentModel);
    }


    private void onAdd() {
        currentModel.addElement(new Element(422, NodeType.AUTHOR, "Carlos Ruiz"));
    }

    private void onDelete() {

    }

    private void onOK() {
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    private class RelationAction {
        static public final int ADD = 0;
        static public final int DELETE = 1;

        public int action;
        public int nodeB;
        public NodeType typeB;
        public int relationType;

        RelationAction(int action, int nodeB, NodeType typeB, int relationType) {
            this.action = action;
            this.nodeB = nodeB;
            this.typeB = typeB;
            this.relationType = relationType;
        }

    }

    private class Element {
        public int id;
        public NodeType type;
        public String value;

        Element(int elementId, NodeType elementType, String value) {
            this.id = elementId;
            this.type = type;
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
