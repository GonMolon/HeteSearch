package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.font.NumericShaper;
import java.util.ArrayList;

public class SearchResults extends JTable {

    private PresentationController presentationController;
    private PathGenerator pathGenerator;
    private JList list;

    public SearchResults(PresentationController presentationController, PathGenerator pathGenerator) {
        this.presentationController = presentationController;
        this.pathGenerator = pathGenerator;
        list = new JList();
        //setMinimumSize(new Dimension(10, 10000)); //Hay que hacer que esta lista llegue hasta abajo justo. Yo no he sido capaz...
        add(list);
        setResults(new Integer[]{4, 5, 4}); //FOR TESTING. WHY THE FUCK THIS DOESN'T WOOOORKKKKKKK
    }

    public void setResults(Integer[] results) {
        DefaultListModel resultsModel = new DefaultListModel();
        add(new JList(results));
        resultsModel.ensureCapacity(results.length);
        list.setModel(resultsModel);
        for(Integer result : results) {
            resultsModel.addElement(Integer.toString(result));
        }
        ListCellRenderer renderer = new ItemRender();
        list.setCellRenderer(renderer);
    }

    public void setResults(ArrayList<Number[]> results) {
    }

    public void setResults(Number[] results) {

    }

    private class ItemRender extends JPanel implements ListCellRenderer {

        private JLabel label;

        public ItemRender() {
            label = new JLabel();
            add(label);
        }

        protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            label = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if(value instanceof Integer) {
                label.setText(String.valueOf((Integer)value));
            } else if(value instanceof Number[]) {

            }
            if(isSelected) {
                setForeground(Color.BLUE);
            } else {
                setForeground(Color.BLACK);
            }
            return this;
        }
    }
}
