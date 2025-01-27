package presentation;

import presentation.utils.DataChooserException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private PresentationController presentationController;
    private MainView mainView;
    private CardLayout graphsLayout;
    private JPanel graphs;
    public GraphView graphView;
    public GraphPath graphPath;

    protected MainFrame(PresentationController presentationController) {
        super("HeteSearch");
        this.presentationController = presentationController;
        initialize();
    }

    private void initialize() {
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1300, 800));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        graphView = new GraphView(presentationController);
        graphPath = new GraphPath();
        mainView = new MainView(presentationController, this);
        graphsLayout = new CardLayout();
        graphs = new JPanel(graphsLayout);
        graphs.add(graphPath.getPanel(), "Path");
        graphs.add(graphView, "GraphView");
        graphsLayout.show(graphs, "GraphView");
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.add(mainView, BorderLayout.WEST);
        contentPane.add(graphs, BorderLayout.CENTER);
        pack();
        createMenu();
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
                        try {
                            DataChooser dc = new DataChooser(importGraph, false);
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
                        try {
                            DataChooser dc = new DataChooser(exportGraph, true);
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

    public void resetPathGenerator() {
        mainView.resetPathGenerator();
    }
}