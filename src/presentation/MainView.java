package presentation;

import persistence.PersistenceController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView {

    private PresentationController presentationController;
    private JFrame frame;

    protected MainView(PresentationController presentationController) {
        this.presentationController = presentationController;
        createFrame();
    }

    protected void show() {
        frame.pack();
        frame.setVisible(true);
    }

    private void createFrame() {
        frame = new JFrame("HeteSearch");
        frame.setMinimumSize(new Dimension(700,700));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contentPane = (JPanel) frame.getContentPane();
        contentPane.add(new PathGenerator(presentationController));
        createMenu();
    }

    private void createMenu() {
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("File");
        MenuItem newGraph = new MenuItem("New Graph");
        newGraph.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //presentationController.newGraph();
                    }
                }
        );
        MenuItem importGraph = new MenuItem("Import Graph");
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
        MenuItem exportGraph = new MenuItem("Export Graph");
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
        frame.setMenuBar(menuBar);
    }
}