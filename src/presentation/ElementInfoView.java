package presentation;

import domain.NodeType;
import presentation.utils.AutoClearTextField;
import presentation.utils.NodeTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

abstract public class ElementInfoView extends JDialog {
    static private final int minimumHeight = 500;
    static private final int minimumWidth = 650;

    protected int nodeId;
    protected NodeType type;
    protected Integer relationTypeId;
    protected ArrayList<Integer> relationTypeIds;
    protected ArrayList<RelationAction> actions;

    protected PresentationController presentationController;
    private Component parentComponent;

    protected JPanel contentPane;
    protected AutoClearTextField fieldName;
    protected JButton buttonAddRelation;
    protected JButton buttonDeleteRelation;
    protected JButton buttonOk;
    protected JButton buttonCancel;
    protected JComboBox<NodeType> selectType;
    protected JComboBox<String> selectRelationType;
    protected NodeTextField fieldOtherNodeName;
    protected JList<ElementInfoView.Element> listRelations;
    protected DefaultListModel<ElementInfoView.Element> currentModel;
    protected ArrayList<DefaultListModel<ElementInfoView.Element>> listModels;

    protected ElementInfoView(PresentationController presentationController, Component parentComponent, String title) {
        super(null, title, ModalityType.APPLICATION_MODAL);
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

        listRelations.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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
            public void actionPerformed(ActionEvent e) {
                onAdd();
            }
        });

        buttonDeleteRelation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onDelete();
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

    private void onSelectType() {
        type = (NodeType) selectType.getSelectedItem();
        actions = new ArrayList<>();
        relationTypeIds = presentationController.getRelations(type);

        listModels.clear();
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
        fieldOtherNodeName.setNodeType(presentationController.getNodeTypeTo(relationTypeId, type));
        currentModel = listModels.get(selectRelationType.getSelectedIndex());
        listRelations.setModel(currentModel);
    }

    private void onAdd() {
        int elementId = fieldOtherNodeName.id;
        if (elementId != -1) {
            NodeType elementType = presentationController.getNodeTypeTo(relationTypeId, type);
            String elementValue = presentationController.getNodeValue(elementType, elementId);
            Element e = new Element(elementId, elementType, elementValue);
            if (!inCurrentModel(e)) {
                currentModel.addElement(e);
                actions.add(new RelationAction(RelationAction.ADD, elementId, elementType, relationTypeId));
            }
        }
    }


    private boolean inCurrentModel(Element e) {
        boolean in = false;
        for (int i = 0; i < currentModel.size() && !in; ++i) {
            in = (e.id == currentModel.get(i).id);
        }
        return in;
    }
    private void onDelete() {
        int selectedIndex = listRelations.getSelectedIndex();
        while (selectedIndex != -1) {
                Element e = currentModel.getElementAt(selectedIndex);
                actions.add(new RelationAction(RelationAction.DELETE, e.id, e.type, relationTypeId));
                currentModel.removeElementAt(selectedIndex);
                selectedIndex = listRelations.getSelectedIndex();
        }
    }

    protected void onOK() {
        dispose();
    }


    protected void onCancel() {
        dispose();
    }

    private void createUIComponents() {
        fieldName = new AutoClearTextField("Insert element name");
        fieldOtherNodeName = new NodeTextField(presentationController, "Insert related element name", NodeType.AUTHOR);
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

    protected class Element {
        public int id;
        public NodeType type;
        public String value;

        Element(int elementId, NodeType elementType, String value) {
            this.id = elementId;
            this.type = elementType;
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
