package presentation.utils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class InstanceSelectionDialog extends JOptionPane {

    public InstanceSelectionDialog(ArrayList<Integer> ids, ArrayList<String> names, String title, String instruction) {
        super(instruction, JOptionPane.QUESTION_MESSAGE);
        JButton[] instancesButtons = new JButton[ids.size()];
        for(int i = 0; i < instancesButtons.length; ++i) {
            JButton button = new JButton(names.get(i));
            button.setActionCommand(String.valueOf(ids.get(i)));
            button.addActionListener(
                    new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            setValue(e.getActionCommand());
                        }
                    }
            );
            instancesButtons[i] = button;
        }
        setOptions(instancesButtons);
        JDialog dialogOptionPane = this.createDialog(new JFrame(), title);
        dialogOptionPane.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialogOptionPane.pack();
        dialogOptionPane.setVisible(true);
    }

    public int getIdChosen() {
        return Integer.valueOf((String) getValue());
    }
}
