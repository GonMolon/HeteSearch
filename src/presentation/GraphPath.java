package presentation;

import domain.NodeType;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;

public class GraphPath {

    private Graph graph;
    private Viewer viewer;
    private ViewPanel panel;
    private int lastId = 0;

    public GraphPath() {
        graph = new MultiGraph("Path");
        graph.setAutoCreate(true);
        graph.setStrict(false);
        reset();
        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        viewer.enableAutoLayout();
        panel = viewer.addDefaultView(false);
        panel.setVisible(false);
    }

    public void reset() {
        lastId = 0;
        NodeType[] types = NodeType.values();
        for(int i = 0; i < types.length; ++i) {
            Node node = graph.addNode(types[i].toString());
            node.addAttribute("ui.label", types[i].toString());
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    public void addRelation(NodeType prev, NodeType next, int id) {
        graph.addEdge(String.valueOf(lastId), prev.toString(), next.toString(), true);
        ++lastId;
    }
}
