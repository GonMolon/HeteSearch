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
    private PresentationController presentationController;

    public GraphView(PresentationController presentationController) {
        this.presentationController = presentationController;
        graph = new SingleGraph("GraphView");
        graph.setAutoCreate(true);
        graph.setStrict(false);
        graph.addEdge("AB", "A", "B");
        graph.addEdge("CD", "C", "D");
        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.enableAutoLayout();
        panel = viewer.addDefaultView(false);
        panel.setVisible(false);
    }

    public JPanel getPanel() {
        return panel;
    }

    public void refresh() {

    }
}
