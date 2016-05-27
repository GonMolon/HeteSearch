package presentation;


import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;

public class GraphView {

    private Graph graph;
    private Viewer viewer;
    private ViewPanel panel;

    public GraphView() {
        graph = new SingleGraph("Graph");
        graph.addNode("AB");
        graph.addNode("B");
        graph.addNode("C");
        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.enableAutoLayout();
        panel = viewer.addDefaultView(false);
    }

    public JPanel getPanel() {
        return panel;
    }

    public void finish() {
        panel.setVisible(false);
        viewer.close();
        viewer.close();
    }
}
