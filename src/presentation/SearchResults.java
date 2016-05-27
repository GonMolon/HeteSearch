package presentation;

import javax.swing.*;
import java.awt.*;

public class SearchResults extends JPanel {

    private JList list;

    public SearchResults() {
        list = new JList();
        add(list, BorderLayout.CENTER);
    }
}
