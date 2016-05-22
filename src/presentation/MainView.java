package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame {

    private PresentationController presentationController;
    private JButton addNode;

    protected MainView(PresentationController presentationController) {
        super("HeteSearch");
        this.presentationController = presentationController;
        createComponents();
    }

    protected void init() {
        pack();
        setVisible(true);
    }

    private void createComponents() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(700,700));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.add(new PathGenerator(presentationController, this), BorderLayout.CENTER);
        addNode = new JButton("Add element");
        addNode.setVisible(false);
        addNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO implement this
            }
        });
        contentPane.add(addNode, BorderLayout.EAST);
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
                        presentationController.importDB("data/");
                        System.out.println("Done");
                    }
                }
        );
        JMenuItem exportGraph = new JMenuItem("Export Graph");
        exportGraph.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Exporting graph");
                        presentationController.exportDB("out/");
                        System.out.println("Done");
                    }
                }
        );
        menu.add(newGraph);
        menu.add(importGraph);
        menu.add(exportGraph);
        menuBar.add(menu);
        setJMenuBar(menuBar);
    }

    public void setAddNode(boolean visible) {
        addNode.setVisible(visible);
    }
}