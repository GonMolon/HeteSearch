package presentation;

import domain.NodeType;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import presentation.utils.MouseManagerAdapter;

import javax.swing.*;
import java.awt.*;

public class GraphPath {

    private Graph graph;
    private Viewer viewer;
    private ViewPanel panel;
    private int lastIdNode;
    private int lastIdRelation;
    private int lastX;
    private static int X_GAP = 100;
    private static int Y_GAP = 200;

    public GraphPath() {
        graph = new MultiGraph("Path");
        graph.setAutoCreate(true);
        graph.setStrict(false);
        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        panel = viewer.addDefaultView(false);
        panel.setMouseManager(new MouseManagerAdapter());
        reset();
    }

    public void reset() {
        graph.clear();
        graph.setAttribute("stylesheet",
                "node { "
                + "     shape: rounded-box; "
                + "     padding: 5px; "
                + "     fill-color: rgba(0,125,164,100); "
                + "     stroke-mode: plain; "
                + "     size-mode: fit; "
                + "     text-size: 16px; "
                + "} "
                + "edge { "
                + "     shape: freeplane; "
                + "     text-size: 14px; "
                + "} "
                + "graph { "
                + "     padding: 40px; "
                + "}"
        );
        lastIdRelation = 0;
        lastIdNode = 0;
        lastX = 0;
    }

    public JPanel getPanel() {
        return panel;
    }

    public void addFirstRelation(NodeType from, NodeType to, String relationName) {
        addNode(from);
        addRelation(to, relationName);
    }

    public void addRelation(NodeType next, String relationName) {
        if(lastIdNode > 0) {
            addNode(next);
            Edge edge = graph.addEdge(String.valueOf(++lastIdRelation), String.valueOf(lastIdNode-1), String.valueOf(lastIdNode), true);
            edge.addAttribute("ui.label", relationName);
            edge.addAttribute("ui.style", "size: 1px;");
        }
    }

    private void addNode(NodeType next) {
        Node node = graph.addNode(String.valueOf(++lastIdNode));
        node.addAttribute("ui.label", next.toString());
        node.setAttribute("xy", lastX*X_GAP, lastX%2 == 1 ? -Y_GAP : 0);
        ++lastX;
    }

    public void show() {
        ((CardLayout)panel.getParent().getLayout()).show(panel.getParent(), "Path");
    }
}
