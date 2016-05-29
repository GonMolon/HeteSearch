package presentation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SearchResults extends ScrollPane {

    private PresentationController presentationController;
    private PathGenerator pathGenerator;
    private JPanel panel;
    private JList list;

    public SearchResults(PresentationController presentationController, PathGenerator pathGenerator) {
        this.presentationController = presentationController;
        this.pathGenerator = pathGenerator;
        list = new JList();
        setBackground(Color.black);
        add(list, BorderLayout.CENTER);
    }

    public void setResults(Integer[] results) {

    }

    public void setResults(ArrayList<Number[]> results) {
    }

    public void setResults(int fromID, int toID, double hetesim) {

    }

    private class Item extends JTable {

    }
}
