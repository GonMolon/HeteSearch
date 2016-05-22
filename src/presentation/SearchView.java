package presentation;

import javax.swing.*;

public class SearchView {

    private PresentationController presentationController;
    private JFrame frame;

    protected SearchView(PresentationController presentationController) {
        this.presentationController = presentationController;
        frame = new JFrame("HeteSearch");
    }
}
