package presentation;

import domain.Node;
import domain.NodeType;
import presentation.utils.AutoClearTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ElementInfoView extends JDialog {
    private final int minimumHeight = 400;
    private final int minimumWidth = 300;

    protected PresentationController presentationController;
    protected Node node;
    protected NodeType type;
    protected JPanel contentPane;
    protected JButton buttonOk;
    protected JButton buttonCancel;
    protected JComboBox selectType;
    protected JButton buttonAddRelation;
    protected JButton buttonRemoveRelation;
    protected AutoClearTextField fieldName;

    protected ElementInfoView(PresentationController presentationController, Component parentComponent) {
        super(null, "Modify element", ModalityType.APPLICATION_MODAL);
        this.presentationController = presentationController;
        setLocationRelativeTo(parentComponent);
        setLocation(getLocation().x - minimumWidth/2, getLocation().y - minimumHeight/2);
        initialize();
    }

    private void initialize() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOk);
        setMinimumSize(new Dimension(minimumWidth, minimumHeight));

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
        int type = selectType.getSelectedIndex();
        String name = fieldName.getText();
        presentationController.addNode(NodeType.values()[type], name);
    }

    private void createUIComponents() {
        fieldName = new AutoClearTextField("Insert element name");
    }

    private void onOK() {
        dispose();
    }

    private void onCancel() {
        dispose();
    }
}
