package presentation;

import persistence.PersistenceController;

import javax.swing.*;
import java.awt.*;

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
        MenuItem importGraph = new MenuItem("Import Graph");
        MenuItem exportGraph = new MenuItem("Export Graph");
        menu.add(newGraph);
        menu.add(importGraph);
        menu.add(exportGraph);
        menuBar.add(menu);
        frame.setMenuBar(menuBar);
    }
}