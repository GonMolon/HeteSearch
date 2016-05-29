package presentation;

import javax.swing.*;
import java.awt.*;

public class SearchResults extends JPanel {

    private JList list;

    private SearchResults() {
        super();
        list = new JList();
        add(list, BorderLayout.CENTER);
    }
}
