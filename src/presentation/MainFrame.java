package presentation;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import presentation.utils.DataChooserException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private PresentationController presentationController;
    private MainView mainView;
    private GraphView graphView;

    protected MainFrame(PresentationController presentationController) {
        super("HeteSearch");
        this.presentationController = presentationController;
        initialize();
    }

    private void initialize() {
        //setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setPreferredSize(new Dimension(1000, 10000));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainView = new MainView(presentationController);
        graphView = new GraphView();
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.add(mainView, BorderLayout.WEST);
        contentPane.add(graphView.getPanel(), BorderLayout.CENTER);
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

    public void setVisible(boolean visible) {
        super.setVisible(visible);
        //setResizable(false);
    }

    public void dispose() {
        graphView.finish();
        super.dispose();
    }
}