package presentation;


import domain.NodeType;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.*;

public class GraphView {

    private Graph graph;
    private Viewer viewer;
    private ViewPanel panel;
    private PresentationController presentationController;
    private int lastEdgeID;
    private static int MAX_NODES = 300;

    public GraphView(PresentationController presentationController) {
        this.presentationController = presentationController;
        graph = new MultiGraph("GraphView");
        graph.setAutoCreate(true);
        graph.setStrict(false);
        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        panel = viewer.addDefaultView(false);
        panel.setEnabled(false);
        refresh();
    }


    public void refresh() {
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
                        + "     text-size: 14px; "
                        + "} "
                        + "graph { "
                        + "     padding: 40px; "
                        + "}"
        );
        lastEdgeID = 0;
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
        for(NodeType type : NodeType.values()) {
            int[] ids = presentationController.getNodes(type);
            for(int i = 0; i < Math.min(ids.length, MAX_NODES); ++i) {
                Node node = graph.addNode(type.toString() + "_" + String.valueOf(ids[i]));
                node.addAttribute("nodetype", type);
                node.addAttribute("originalID", ids[i]);
                node.addAttribute("ui.style", "fill-color: " + getColor(type) + ";");
                node.addAttribute("ui.label", presentationController.getNodeValue(type, ids[i]));
            }
        }
        for(Node from : graph.getNodeSet()) {
            NodeType type = from.getAttribute("nodetype");
            int nodeID = from.getAttribute("originalID");
            for(int relationID : presentationController.getRelations(type)) {
                for(int nodeTo : presentationController.getEdges(relationID, type, nodeID)) {
                    NodeType[] types = presentationController.getNodeTypesFromRelation(relationID);
                    NodeType typeTo = types[0];
                    if(type == typeTo) {
                        typeTo = types[1];
                    }
                    Node to = graph.getNode(String.valueOf(typeTo.toString() + "_" + nodeTo));
                    if(to != null) {
                        addEdge(from, to, relationID);
                    }
                }
            }
        }
    }

    public JPanel getPanel() {
        return panel;
    }

    public void show() {
        ((CardLayout)panel.getParent().getLayout()).show(panel.getParent(), "GraphView");
    }

    private boolean addEdge(Node from, Node to, int relationID) {
        for(Edge edge : from.getEachEdge()) {
            if(edge.getOpposite(from) == to) {
                if((int) edge.getAttribute("relationID") == relationID) {
                    return false;
                }
            }
        }
        Edge edge = graph.addEdge(String.valueOf(++lastEdgeID), from.getId(), to.getId());
        edge.addAttribute("relationID", relationID);
        return true;
    }

    private String getColor(NodeType type) {
        if(type == NodeType.AUTHOR) {
            return "red";
        } else if(type == NodeType.PAPER) {
            return "blue";
        } else if(type == NodeType.CONF) {
            return "yellow";
        } else if(type == NodeType.TERM) {
            return "green";
        } else {
            return "cyan";
        }
    }
}
