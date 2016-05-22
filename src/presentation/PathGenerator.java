package presentation;

import domain.NodeType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PathGenerator extends JPanel implements ActionListener{

    private PresentationController presentationController;

    private JButton[] buttons = new JButton[5];
    private JButton reset;
    protected ArrayList<Integer> actualRS = new ArrayList<Integer>();

    private NodeType from = null;

    protected PathGenerator(PresentationController presentationController) {
        this.presentationController = presentationController;

        this.setLayout(new BorderLayout());
        addButton(0, "Label", BorderLayout.NORTH);
        addButton(1, "Author", BorderLayout.WEST);
        addButton(2, "Paper", BorderLayout.CENTER);
        addButton(3, "Conference", BorderLayout.EAST);
        addButton(4, "Term", BorderLayout.SOUTH);

        reset = new JButton("Reset");
        //this.add(reset);
        reset.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        reset();
                    }
                }
        );
    }

    private void addButton(int id, String name, String pos) {
        JButton button = new JButton(name);
        button.addActionListener(this);
        button.setActionCommand(name);
        this.add(button, pos);
        buttons[id] = button;
    }

    protected void reset() {
        actualRS = new ArrayList<Integer>();
        for(int id = 0; id < buttons.length; ++id) {
            buttons[id].setEnabled(true);
        }
        from = null;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        NodeType to = getNodeType(event.getActionCommand());
        PresentationController.PathInfo info = presentationController.getPathInfo(from, to);
        if(info.availableRelations.size() == 1) {
            actualRS.add(info.availableRelations.get(0));
        } else {

        }
        from = to;
    }

    private NodeType getNodeType(String type) {
        if(type.equals("Label")) {
            return NodeType.LABEL;
        } else if(type.equals("Author")) {
            return NodeType.AUTHOR;
        } else if(type.equals("Paper")) {
            return NodeType.PAPER;
        } else if(type.equals("Conference")) {
            return NodeType.CONF;
        } else {
            return NodeType.TERM;
        }
    }
}
