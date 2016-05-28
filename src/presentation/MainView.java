package presentation;

import domain.NodeType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JPanel {

    private PresentationController presentationController;
    private MainFrame mainFrame;
    private JPanel panel;
    private PathGenerator pathGenerator;
    private JButton searchButton;
    private RelationalSearchPanel relationalSearchPanel;
    private SimpleSearchPanel simpleSearchPanel;
    private JPanel searchPanel;
    private JButton addNodeButton;
    private JButton addRelationButton;
    private JButton removeRelationButton;
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
                ElementInfoView view = new ElementInfoView(presentationController, getRootPane());
                view.setVisible(true);
            }
        });
        addRelationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO implement this
            }
        });
        removeRelationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO implement this
            }
        });
        searchButton.addActionListener(simpleSearchPanel);
        searchButton.addActionListener(relationalSearchPanel);
    }

    public void update() {
        if(pathGenerator.from == null) {
            addNodeButton.setEnabled(false);
            addRelationButton.setEnabled(false);
            simpleSearchPanel.setEnabled(false);
            searchButton.setEnabled(false);
            CardLayout searchPanelLayout = (CardLayout) searchPanel.getLayout();
            searchPanelLayout.show(searchPanel, "simple");
            relationalSearchPanel.reset();
        } else if(pathGenerator.actualRS.size() == 0) {
            addNodeButton.setEnabled(true);
            addRelationButton.setEnabled(false);
            searchButton.setEnabled(true);
            simpleSearchPanel.setEnabled(true);
            simpleSearchPanel.setNodeType(pathGenerator.from);
            searchButton.setEnabled(true);
        } else if(pathGenerator.actualRS.size() == 1) {
            addNodeButton.setEnabled(false);
            addRelationButton.setEnabled(true);
            searchButton.setEnabled(true);
            CardLayout searchPanelLayout = (CardLayout) searchPanel.getLayout();
            searchPanelLayout.show(searchPanel, "relational");
            relationalSearchPanel.setNodeTypeFrom(pathGenerator.from);
            relationalSearchPanel.setNodeTypeTo(pathGenerator.to);
        } else {
            addRelationButton.setEnabled(false);
            relationalSearchPanel.setNodeTypeTo(pathGenerator.to);
        }
    }

    private void createUIComponents() {
        pathGenerator = new PathGenerator(presentationController, this, mainFrame.graphPath);
        relationalSearchPanel = new RelationalSearchPanel(presentationController);
        simpleSearchPanel = new SimpleSearchPanel(presentationController);
        searchResults = new SearchResults();
    }
}