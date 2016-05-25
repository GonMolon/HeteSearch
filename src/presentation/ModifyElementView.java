package presentation;

import domain.Element;
import presentation.utilities.AutoClearTextField;
import presentation.utilities.SpringUtilities;

import javax.swing.*;
import java.awt.*;

/**
 * Created by daniconde on 24/05/2016.
 */
public class ModifyElementView extends JDialog {
    protected PresentationController presentationController;
    private final int frameWidth = 300;
    private final int frameHeight = 350;
    private final int fieldsPanelHeight = 100;
    private final int fieldWidht = 250;
    private final int fieldHeight = 35;

    protected ModifyElementView(PresentationController presentationController, String title) {
        super(null, title, ModalityType.APPLICATION_MODAL);
        this.presentationController = presentationController;
        this.initialize();
    }

    private void initialize() {
        this.setSize(frameWidth, frameHeight);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JSplitPane splitPane = new JSplitPane();
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(fieldsPanelHeight);
        splitPane.setDividerSize(0);
        splitPane.setLeftComponent(new FieldsPanel());
        splitPane.setRightComponent(new RelationsPanel());

        this.add(splitPane);
    }

    private class FieldsPanel extends JPanel {
        FieldsPanel() {
            this.setSize(frameWidth, fieldsPanelHeight);

            AutoClearTextField nameField = new AutoClearTextField("Name");
            nameField.setSize(fieldWidht, fieldHeight);
            String[] relations = {"a", "b", "c", "d"};
            JComboBox<String> typeSelect = new JComboBox<>(relations);
            typeSelect.setSize(fieldWidht, fieldHeight);

            this.setLayout(new SpringLayout());
            this.add(nameField);
            this.add(typeSelect);
            SpringUtilities.makeGrid(this, 2, 1, 5, 5, 8, 8);
        }
    }

    private class RelationsPanel extends JPanel {
        RelationsPanel() {
            this.setSize(frameWidth, frameHeight - fieldsPanelHeight);

            JList<JTextField> relationList = new JList<>();
            relationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            String[] relations = {"a", "b", "c", "d"};
            for (String r : relations) {
                JTextField field = new JTextField(r);
                field.setEditable(false);
                relationList.add(field);
            }
            JScrollPane scrollList = new JScrollPane(relationList);
            JButton buttonAdd = new JButton("add");
            JButton buttonDelete = new JButton("delete");

            this.setLayout(new FlowLayout());
            this.add(scrollList);
            this.add(buttonAdd);
            this.add(buttonDelete);

        }
    }
}
