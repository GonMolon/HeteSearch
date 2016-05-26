package presentation;

import presentation.utilities.AutoClearTextField;

import javax.swing.*;
import java.awt.event.*;

public class ModifyElementViewForm extends JDialog {
    private PresentationController presentationController;
    private JPanel contentPane;
    private JButton buttonAdd;
    private JButton buttonCancel;
    private AutoClearTextField fieldName;
    private JComboBox selectType;

    public ModifyElementViewForm(PresentationController presentationController, String title) {
        super(null, title, ModalityType.APPLICATION_MODAL);
        presentationController = presentationController;
        initialize();
    }

    void initialize() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonAdd);

        buttonAdd.addActionListener(new ActionListener() {
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
        pack();
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
        fieldName = new AutoClearTextField("Type element name");
    }
}
