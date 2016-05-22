package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PathGenerator extends JPanel implements ActionListener{

    private PresentationController presentationController;

    private JButton[] buttons = new JButton[5];
    private JButton reset;

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

    private void reset() {
        for(int id = 0; id < buttons.length; ++id) {
            buttons[id].setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {

    }
}
