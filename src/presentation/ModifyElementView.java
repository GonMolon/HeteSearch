package presentation;

import presentation.utils.AutoClearTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ModifyElementView extends JDialog {
    private final int minimumHeight = 400;
    private final int minimumWidth = 300;

    private PresentationController presentationController;
    private JPanel contentPane;
    private JButton buttonOk;
    private JButton buttonCancel;
    private JComboBox selectType;
    private JButton buttonAddRelation;
    private JButton buttonRemoveRelation;
    private AutoClearTextField fieldName;

    public ModifyElementView(PresentationController presentationController, String title) {
        super(null, title, ModalityType.APPLICATION_MODAL);
        presentationController = presentationController;
        initialize();
    }

    void initialize() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOk);
        setMinimumSize(new Dimension(minimumWidth, minimumHeight));

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

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
// add your code here
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    private void createUIComponents() {
        fieldName = new AutoClearTextField("Test");
    }
}
