package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame {

    private PresentationController presentationController;
    private JPanel panel;
    private PathGenerator pathGenerator;
    private JButton addNodeButton;
    private JButton searchButton;
    private RelationalSearchPanel relationalSearchPanel;
    private SimpleSearchPanel simpleSearchPanel;
    private JPanel searchPanel;

    protected MainView(PresentationController presentationController) {
        super("HeteSearch");
        this.presentationController = presentationController;
        initialize();
    }

    private void initialize() {
        setMinimumSize(new Dimension(700, 700));
        //setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.add(panel);
        addNodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModifyElementViewForm view = new ModifyElementViewForm(presentationController, "Testing");
                view.setVisible(true);
            }
        });
        createMenu();
        pack();
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem newGraph = new JMenuItem("New Graph");
        newGraph.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        presentationController.newDB();
                    }
                }
        );
        JMenuItem importGraph = new JMenuItem("Import Graph");
        importGraph.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Importing graph");
                        DataChooser dc = null;
                        try {
                            dc = new DataChooser(importGraph, false);
                            presentationController.newDB();
                            presentationController.importDB(dc.getDirectory());
                            System.out.println("Done");
                        } catch (DataChooserException exception) {
                            exception.printStackTrace();
                        }
                    }
                }
        );
        JMenuItem exportGraph = new JMenuItem("Export Graph");
        exportGraph.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Exporting graph");
                        DataChooser dc = null;
                        try {
                            dc = new DataChooser(exportGraph, true);
                            presentationController.exportDB(dc.getDirectory());
                            System.out.println("Done");
                        } catch (DataChooserException exception) {
                            exception.printStackTrace();
                        }
                    }
                }
        );
        menu.add(newGraph);
        menu.add(importGraph);
        menu.add(exportGraph);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    public void update() {
        if(pathGenerator.from == null) {
            addNodeButton.setEnabled(false);
            CardLayout searchPanelLayout = (CardLayout) searchPanel.getLayout();
            searchPanelLayout.show(searchPanel, "simple");
        } else if(pathGenerator.actualRS.size() == 0) {
            addNodeButton.setEnabled(true);
            CardLayout searchPanelLayout = (CardLayout) searchPanel.getLayout();
            searchPanelLayout.show(searchPanel, "relational");
        } else {

        }
    }

    private void createUIComponents() {
        pathGenerator = new PathGenerator(presentationController, this);
        relationalSearchPanel = new RelationalSearchPanel(presentationController);
    }
}