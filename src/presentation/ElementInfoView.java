package presentation;

import domain.Element;

import javax.swing.*;
import java.awt.*;

/**
 * Created by daniconde on 24/05/2016.
 */
public class ElementInfoView extends JDialog {
    protected PresentationController presentationController;
    private final int frameWidth = 300;
    private final int frameHeight = 350;
    private final int fieldsPanelHeight = 100;

    protected ElementInfoView(PresentationController presentationController, String title) {
        super(null, title, ModalityType.APPLICATION_MODAL);
        this.presentationController = presentationController;
        initialize();
    }

    private void initialize() {
        setSize(frameWidth, frameHeight);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

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
            this.setBackground(Color.BLUE);
        }
    }

    private class RelationsPanel extends JPanel {
        RelationsPanel() {
            this.setSize(frameWidth, frameHeight - fieldsPanelHeight);
            this.setBackground(Color.RED);
        }
    }
}
