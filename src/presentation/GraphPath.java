package presentation;

import domain.NodeType;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;

public class GraphPath {

    private Graph graph;
    private Viewer viewer;
    private JPanel panel;
    private int lastIdNode;
    private int lastIdRelation;
    private int lastX;
    private float lastY;
    private static int GAP = 100;
    private static int COLUMNS = 3;

    public GraphPath() {
        graph = new MultiGraph("Path");
        graph.setAutoCreate(true);
        graph.setStrict(false);
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        graph.setAttribute("stylesheet",
                "node { "
                + "     shape: rounded-box; "
                + "     padding: 10px; "
                + "     fill-color: rgba(0,125,164,100); "
                + "     stroke-mode: plain; "
                + "     size-mode: fit; "
                + "} "
                + "edge { "
                + "     shape: freeplane; "
                + "}");
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
        panel = viewer.addDefaultView(false);
        lastIdRelation = 0;
        lastIdNode = 0;
        lastX = 0;
        lastY = 0;
    }

    public void reset() {
        lastIdRelation = 0;
        lastIdNode = 0;
        lastX = 0;
        lastY = 0;
        graph.clear();
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
        int y = 0;
        if(lastX%2 == 1) {
            y = -100;
        }
        node.setAttribute("xy", lastX*GAP, y);
        ++lastX;
    }
}
