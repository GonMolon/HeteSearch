package presentation;

import javax.swing.*;

public class SearchResults extends JPanel {

    private PresentationController presentationController;
    private JFrame frame;
    private JList list;
    private JPanel panel;

    protected SearchResults(PresentationController presentationController) {
        this.presentationController = presentationController;
        frame = new JFrame("HeteSearch");
    }
}
