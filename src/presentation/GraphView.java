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
import java.util.HashMap;

public class GraphView {

    private Graph graph;
    private Viewer viewer;
    private ViewPanel panel;
    private PresentationController presentationController;
    private int lastEdgeID;
    private HashMap<Integer, Color> relationColors;
    private static int MAX_NODES = 300;

    public GraphView(PresentationController presentationController) {
        this.presentationController = presentationController;
        graph = new MultiGraph("GraphView");
        graph.setAutoCreate(true);
        graph.setStrict(false);
        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        panel = viewer.addDefaultView(false);
        generateRelationColors();
        //panel.getCamera().setViewCenter(0, 0, 0);
        //panel.getCamera().setViewPercent(0.01);
        refresh(false);
    }


    public void refresh(boolean fullGraph) {
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
            for(int i = 0; i < ids.length && (fullGraph || i <= MAX_NODES); ++i) {
                Node node = graph.addNode(type.toString() + "_" + String.valueOf(ids[i]));
                node.addAttribute("nodetype", type);
                node.addAttribute("originalID", ids[i]);
                node.addAttribute("ui.style", "fill-color: " + getNodeColor(type) + ";");
                node.addAttribute("ui.label", presentationController.getNodeValue(type, ids[i]));
            }
        }
        for(Node from : graph.getNodeSet()) {
            NodeType typeFrom = from.getAttribute("nodetype");
            int nodeID = from.getAttribute("originalID");
            for(int relationID : presentationController.getRelations(typeFrom)) {
                for(int nodeTo : presentationController.getEdges(relationID, typeFrom, nodeID)) {
                    Node to = graph.getNode(String.valueOf(presentationController.getNodeTypeTo(relationID, typeFrom).toString() + "_" + nodeTo));
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
        edge.addAttribute("ui.label", presentationController.getRelationName(relationID));
        Color color = relationColors.get(relationID);
        edge.addAttribute("ui.style", "fill-color: rgb(" + String.valueOf(color.getRed()) + ", " + String.valueOf(color.getGreen()) + ", " + String.valueOf(color.getBlue()) + ");");
        return true;
    }

    private String getNodeColor(NodeType type) {
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

    private void generateRelationColors() {
        relationColors = new HashMap<Integer, Color>();
        for(int relationID : presentationController.getRelations()) {
            relationColors.put(relationID, new Color((int)(255*Math.random()), (int)(255*Math.random()), (int)(255*Math.random())));
        }
    }
}
