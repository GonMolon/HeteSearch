package presentation;

import domain.NodeType;
import presentation.utils.AutoClearTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ModifyRelationTypesView extends JDialog {
    static final private String title = "Modify relation types";
    static final private int minHeight = 400;
    static final private int minWidth = 400;

    private PresentationController presentationController;
    private Component parentComponent;

    private NodeType nodeTypeA;
    private NodeType nodeTypeB;
    private ArrayList<RelationTypeAction> actionsOnCancel;
    private ArrayList<RelationTypeAction> actionsOnOk;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox<NodeType> selectNodeTypeA;
    private JComboBox<NodeType> selectNodeTypeB;
    private JButton buttonNew;
    private JButton buttonDelete;
    private JList<RelationType> listRelationTypes;
    private AutoClearTextField fieldNewName;
    private DefaultListModel<RelationType> currentModel;
    private ArrayList< ArrayList< DefaultListModel< RelationType > > > listModels;

    public ModifyRelationTypesView(PresentationController presentationController, Component parentComponent) {
        super(null, title, ModalityType.APPLICATION_MODAL);
        this.presentationController = presentationController;
        this.parentComponent = parentComponent;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        initialize();
    }

    private void initialize() {
        setMinimumSize(new Dimension(minWidth, minHeight));
        setResizable(true);
        setLocationRelativeTo(parentComponent);

        selectNodeTypeA.setModel(new DefaultComboBoxModel<>(NodeType.values()));
        selectNodeTypeB.setModel(new DefaultComboBoxModel<>(NodeType.values()));
        actionsOnCancel = new ArrayList<>();
        actionsOnOk = new ArrayList<>();
        listModels = new ArrayList<>();
        populateListModels();

        onSelectNodeType();

        selectNodeTypeA.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSelectNodeType();
            }
        });

        selectNodeTypeB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onSelectNodeType();
            }
        });

        buttonNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onAdd();
            }
        });

        buttonDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onDelete();
            }
        });

        buttonOK.addActionListener(new ActionListener() {
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

    private void populateListModels() {
        for (NodeType a : NodeType.values()) {
            listModels.add(new ArrayList<>());
            ArrayList< DefaultListModel<RelationType> > arrayList = listModels.get(a.ordinal());
            for (NodeType b : NodeType.values()) {
                arrayList.add(new DefaultListModel<>());
                DefaultListModel<RelationType> model = arrayList.get(b.ordinal());
                populateListModel(model, a, b);
            }
        }
    }

    private void populateListModel(DefaultListModel<RelationType> model, NodeType a, NodeType b) {
        if (a.ordinal() <= b.ordinal()) {
            ArrayList<Integer> relationIds = presentationController.getRelations(a);
            for (Integer id : relationIds) {
                if (presentationController.getNodeTypeTo(id, a) == b) {
                    model.addElement(new RelationType(id, a, b, presentationController.getRelationName(id)));
                }
            }
        }
    }

    private void onSelectNodeType() {
        nodeTypeA = (NodeType) selectNodeTypeA.getSelectedItem();
        nodeTypeB = (NodeType) selectNodeTypeB.getSelectedItem();
        if (nodeTypeA.ordinal() > nodeTypeB.ordinal()) {
            NodeType aux = nodeTypeA;
            nodeTypeA = nodeTypeB;
            nodeTypeB = aux;
        }
        currentModel = listModels.get(nodeTypeA.ordinal()).get(nodeTypeB.ordinal());
        listRelationTypes.setModel(currentModel);
    }

    private void onAdd() {
        String desiredName = fieldNewName.getText();
        if (!desiredName.equals("") && !desiredName.equals(fieldNewName.getTitle())) {
            int newRelationId = presentationController.addRelation(nodeTypeA, nodeTypeB, desiredName);
            RelationType rt = new RelationType(newRelationId, nodeTypeA, nodeTypeB, desiredName);
            currentModel.addElement(rt);
            actionsOnCancel.add(new RelationTypeAction(RelationTypeAction.DELETE, rt));
        }
    }

    private void onDelete() {
        int selectedIndex = listRelationTypes.getSelectedIndex();
        while (selectedIndex != -1) {
            RelationType rt = currentModel.getElementAt(selectedIndex);
            actionsOnOk.add(new RelationTypeAction(RelationTypeAction.DELETE, rt));
            currentModel.removeElementAt(selectedIndex);
            selectedIndex = listRelationTypes.getSelectedIndex();
        }
    }

    private void onOK() {
        for (RelationTypeAction a : actionsOnOk) {
            a.execute();
        }
        dispose();
        presentationController.mainFrame.resetPathGenerator();
    }

    private void onCancel() {
        for (RelationTypeAction a : actionsOnCancel) {
            a.execute();
        }
        dispose();
    }

    public void createUIComponents() {
        fieldNewName = new AutoClearTextField("Insert new relation type name");
    }

    private class RelationType {
        public NodeType a;
        public NodeType b;
        public int id;

        public String value;

        public RelationType(int id, NodeType a, NodeType b, String value) {
            this.id = id;
            this.a = a;
            this.b = b;
            this.value = value;
        }
        @Override
        public String toString() {
            return value;
        }
    }

    private class RelationTypeAction {
        static public final int ADD = 0;
        static public final int DELETE = 1;
        public int action;
        public RelationType relationType;

        RelationTypeAction(int action, RelationType relationType) {
            this.action = action;
            this.relationType = relationType;
        }

        public void execute() {
            if (action == ADD) {
                presentationController.addRelation(relationType.a, relationType.b, relationType.value);
            }
            else {
                presentationController.removeRelation(relationType.id);
            }
        }
    }
}
