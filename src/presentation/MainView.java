package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JPanel {

    private PresentationController presentationController;
    private MainFrame mainFrame;
    private JPanel panel;
    private PathGenerator pathGenerator;
    private JButton addNodeButton;
    private JButton searchButton;
    private RelationalSearchPanel relationalSearchPanel;
    private SimpleSearchPanel simpleSearchPanel;
    private JPanel searchPanel;
    private SearchResults searchResults;

    protected MainView(PresentationController presentationController, MainFrame mainFrame) {
        super();
        this.presentationController = presentationController;
        this.mainFrame = mainFrame;
        initialize();
    }

    private void initialize() {
        add(panel, BorderLayout.CENTER);
        addNodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModifyElementView view = new ModifyElementView(presentationController, "Testing");
                view.setVisible(true);
            }
        });
        searchButton.addActionListener(simpleSearchPanel);
        searchButton.addActionListener(relationalSearchPanel);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setGraphCard("GraphView");
            }
        });
    }

    public void update() {
        if(pathGenerator.from == null) {
            addNodeButton.setEnabled(false);
            simpleSearchPanel.setEnabled(false);
            searchButton.setEnabled(false);
            CardLayout searchPanelLayout = (CardLayout) searchPanel.getLayout();
            searchPanelLayout.show(searchPanel, "simple");
            relationalSearchPanel.reset();
        } else if(pathGenerator.actualRS.size() == 0) {
            addNodeButton.setEnabled(true);
            searchButton.setEnabled(true);
            simpleSearchPanel.setEnabled(true);
            simpleSearchPanel.setNodeType(pathGenerator.from);
            searchButton.setEnabled(true);
        } else if(pathGenerator.actualRS.size() == 1) {
            addNodeButton.setEnabled(false);
            searchButton.setEnabled(true);
            CardLayout searchPanelLayout = (CardLayout) searchPanel.getLayout();
            searchPanelLayout.show(searchPanel, "relational");
            relationalSearchPanel.setNodeTypeFrom(pathGenerator.from);
            relationalSearchPanel.setNodeTypeTo(pathGenerator.to);
        } else {
            relationalSearchPanel.setNodeTypeTo(pathGenerator.to);
        }
    }

    private void createUIComponents() {
        pathGenerator = new PathGenerator(presentationController, this);
        relationalSearchPanel = new RelationalSearchPanel(presentationController);
        simpleSearchPanel = new SimpleSearchPanel(presentationController);
        searchResults = new SearchResults();
    }
}