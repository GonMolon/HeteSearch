package presentation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RelationSelection extends JOptionPane {

    public RelationSelection(ArrayList<Integer> relationsID, ArrayList<String> relationsName) {
        super("Pick a relation between these two NodeTypes", JOptionPane.QUESTION_MESSAGE);
        JButton[] relations = new JButton[relationsID.size()];
        for(int i = 0; i < relations.length; ++i) {
            JButton button = new JButton(relationsName.get(i));
            button.setActionCommand(String.valueOf(relationsID.get(i)));
            button.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            setValue(e.getActionCommand());
                        }
                    }
            );
            relations[i] = button;
        }
        this.setOptions(relations);
        JDialog dialogOptionPane = this.createDialog(new JFrame(), "Pick a relation between these two NodeTypes");
        dialogOptionPane.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialogOptionPane.pack();
        dialogOptionPane.setVisible(true);
    }

    public int getIdChosen() {
        return Integer.valueOf((String) getValue());
    }
}
