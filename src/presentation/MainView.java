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
    private JButton searchButton;
    private RelationalSearchPanel relationalSearchPanel;
    private SimpleSearchPanel simpleSearchPanel;
    private JPanel searchPanel;
    private JButton addNodeButton;
    private JButton addRelationButton;
    private SearchResults searchResults;

    protected MainView(PresentationController presentationController, MainFrame mainFrame) {
        super();
        this.presentationController = presentationController;
        this.mainFrame = mainFrame;
        initialize();
    }

    private void initialize() {
        add(panel);
        addNodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddElementView view = new AddElementView(presentationController, getRootPane(), pathGenerator.from);
                view.setVisible(true);
            }
        });
        addRelationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModifyRelationTypesView view = new ModifyRelationTypesView(presentationController, getRootPane());
                view.setVisible(true);
            }
        });
        searchButton.addActionListener(simpleSearchPanel);
        searchButton.addActionListener(relationalSearchPanel);
    }

    public void update() {
        if(pathGenerator.from == null) {
            addNodeButton.setEnabled(false);
            simpleSearchPanel.setEnabled(false);
            searchButton.setEnabled(false);
            CardLayout searchPanelLayout = (CardLayout) searchPanel.getLayout();
            searchPanelLayout.show(searchPanel, "simple");
            relationalSearchPanel.reset();
            mainFrame.graphView.showGraph();
        } else if(pathGenerator.actualRS.size() == 0) {
            addNodeButton.setEnabled(true);
            searchButton.setEnabled(true);
            simpleSearchPanel.setEnabled(true);
            searchButton.setEnabled(true);
            mainFrame.graphView.showGraph();
        } else if(pathGenerator.actualRS.size() == 1) {
            addNodeButton.setEnabled(false);
            searchButton.setEnabled(true);
            CardLayout searchPanelLayout = (CardLayout) searchPanel.getLayout();
            searchPanelLayout.show(searchPanel, "relational");
            relationalSearchPanel.setNodeTypeFrom(pathGenerator.from);
            relationalSearchPanel.setNodeTypeTo(pathGenerator.to);
            mainFrame.graphPath.show();
        } else {
            relationalSearchPanel.setNodeTypeTo(pathGenerator.to);
            mainFrame.graphPath.show();
        }
    }

    private void createUIComponents() {
        pathGenerator = new PathGenerator(presentationController, this, mainFrame.graphPath);
        searchResults = new SearchResults(presentationController, pathGenerator);
        relationalSearchPanel = new RelationalSearchPanel(presentationController, pathGenerator, searchResults);
        simpleSearchPanel = new SimpleSearchPanel(presentationController, pathGenerator, searchResults);
    }

    public void resetPathGenerator() {
        pathGenerator.reset();
    }
}