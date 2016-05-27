package presentation;


import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.*;

public class GraphView extends JPanel {

    private Graph graph;

    public GraphView() {
        setBackground(Color.BLACK);
        setMaximumSize(new Dimension(100, 100));
        //add(new JButton("HELLOUUUUUUUUUUUUUUUUUU"), BorderLayout.WEST);
    }

    public void test() {
        graph = new SingleGraph("Test");
        graph.addNode("A" );
        graph.addNode("B" );
        graph.addNode("C" );
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CA", "C", "A");
        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        ViewPanel view = viewer.addDefaultView(false);
        view.setVisible(true);
        add(view);
    }
}
