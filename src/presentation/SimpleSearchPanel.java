package presentation;

import presentation.utils.AutoClearTextField;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleSearchPanel extends JPanel implements ActionListener {

    private PresentationController presentationController;
    private PathGenerator pathGenerator;
    private SearchResultsPanel searchResultsPanel;
    private JPanel panel;
    private AutoClearTextField filterText;

    public SimpleSearchPanel(PresentationController presentationController, PathGenerator pathGenerator, SearchResultsPanel searchResults) {
        this.presentationController = presentationController;
        this.pathGenerator = pathGenerator;
        this.searchResultsPanel = searchResults;
        add(panel);
        setEnabled(false);
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        filterText.setEnabled(enabled);
    }

    public void createUIComponents() {
        filterText = new AutoClearTextField("Type a name");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(pathGenerator.actualRS.size() == 0) {
            searchResultsPanel.setResults(presentationController.simpleSearch(pathGenerator.from, filterText.getText()), pathGenerator.from);
        }
    }
}