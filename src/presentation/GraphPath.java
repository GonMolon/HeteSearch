package presentation;

import domain.NodeType;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;

public class GraphPath {

    private Graph graph;
    private Viewer viewer;
    private ViewPanel panel;

    public GraphPath() {
        graph = new SingleGraph("Path");
        graph.setAutoCreate(true);
        graph.setStrict(false);
        reset();
        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.enableAutoLayout();
        panel = viewer.addDefaultView(false);
        panel.setVisible(false);
    }

    public void reset() {
        NodeType[] types = NodeType.values();
        for(int i = 0; i < types.length; ++i) {
            graph.addNode(types[i].toString());
        }
    }

    public JPanel getPanel() {
        return panel;
    }
}
