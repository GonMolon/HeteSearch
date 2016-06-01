package presentation;

import domain.NodeType;
import presentation.utils.InstanceSelectionDialog;
import presentation.utils.RoundButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PathGenerator extends JPanel implements ActionListener{

    private PresentationController presentationController;
    private MainView mainView;
    private GraphPath graphPath;

    protected ArrayList<Integer> actualRS = new ArrayList<Integer>();
    protected NodeType prev = null;
    protected NodeType from = null;
    protected NodeType to = null;

    private JPanel panel;

    private RoundButton label;
    private RoundButton term;
    private RoundButton paper;
    private RoundButton author;
    private RoundButton conference;
    private RoundButton[] buttons;

    private JButton resetButton;

    private static String LABEL = "Lab";
    private static String AUTHOR = "Aut";
    private static String PAPER = "Pap";
    private static String CONFERENCE = "Con";
    private static String TERM = "Ter";

    protected PathGenerator(PresentationController presentationController, MainView mainView, GraphPath graphPath) {
        this.presentationController = presentationController;
        this.mainView = mainView;
        this.graphPath = graphPath;
        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        resetButton.setVisible(true);
        NodeType next = getNodeType(event.getActionCommand());
        ArrayList[] pathInfo = presentationController.getPathInfo(prev, next);
        ArrayList<Integer> availableRelations = pathInfo[0];
        ArrayList<NodeType> availableNodeTypes = pathInfo[1];
        if(availableRelations.size() == 1) {
            actualRS.add(availableRelations.get(0));
        } else if(availableRelations.size() > 1) {
            ArrayList<String> relationsName = new ArrayList<String>();
            for(int i = 0; i < availableRelations.size(); ++i) {
                relationsName.add(presentationController.getRelationName(availableRelations.get(i)));
            }
            InstanceSelectionDialog instanceSelectionDialog = new InstanceSelectionDialog(availableRelations, relationsName, "Pick a relation", "There's more than one relation type between " + prev.toString() + " and " + next.toString() + "\nChoose which you want to use:");
            actualRS.add(instanceSelectionDialog.getIdChosen());
        }
        setEnabledButtons(false);
        for(int i = 0; i < availableNodeTypes.size(); ++i) {
            int id = getButtonID(availableNodeTypes.get(i));
            buttons[id].setEnabled(true);
        }
        if(actualRS.size() > 0) {
            String relationName = presentationController.getRelationName(actualRS.get(actualRS.size()-1));
            if(actualRS.size() == 1) {
                graphPath.addFirstRelation(prev, next, relationName);
            } else {
                graphPath.addRelation(next, relationName);
            }
        } else {
            from = next;
        }
        prev = next;
        to = next;
        mainView.update();
    }

    public void reset() {
        resetButton.setVisible(false);
        prev = null;
        from = null;
        to = null;
        setEnabledButtons(true);
        actualRS = new ArrayList<Integer>();
        mainView.update();
        graphPath.reset();
    }

    private void createUIComponents() {
        Color color = new Color(0x007DA4);
        label = new RoundButton(LABEL, color);
        author = new RoundButton(AUTHOR, color);
        paper = new RoundButton(PAPER, color);
        conference = new RoundButton(CONFERENCE, color);
        term = new RoundButton(TERM,  color);
        buttons = new RoundButton[]{label, author, paper, conference, term};
        for(int id = 0; id < buttons.length; ++id) {
            buttons[id].addActionListener(this);
            buttons[id].setPreferredSize(new Dimension(100, 100));
            buttons[id].setFont(new Font("Arial", Font.PLAIN, 20));
        }

        resetButton = new JButton("Reset");
        resetButton.setVisible(false);
        resetButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        reset();
                    }
                }
        );

        JButton button = new JButton("test");
        button.setPreferredSize(new Dimension(100, 100));
        add(button, BorderLayout.EAST);
    }

    private void setEnabledButtons(boolean enable) {
        for(int id = 0; id < buttons.length; ++id) {
            buttons[id].setEnabled(enable);
        }
    }

    private int getButtonID(NodeType type) {
        if(type == NodeType.LABEL) {
            return 0;
        } else if(type == NodeType.AUTHOR) {
            return 1;
        } else if(type == NodeType.PAPER) {
            return 2;
        } else if(type == NodeType.CONF) {
            return 3;
        } else {
            return 4;
        }
    }

    private NodeType getNodeType(String type) {
        if(type.equals(LABEL)) {
            return NodeType.LABEL;
        } else if(type.equals(AUTHOR)) {
            return NodeType.AUTHOR;
        } else if(type.equals(PAPER)) {
            return NodeType.PAPER;
        } else if(type.equals(CONFERENCE)) {
            return NodeType.CONF;
        } else {
            return NodeType.TERM;
        }
    }
}
