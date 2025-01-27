package presentation;


import domain.NodeType;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Camera;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;
import presentation.utils.CustomMouseManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class GraphView extends JPanel implements ViewerListener {

    private Graph graph;
    private Viewer viewer;
    private ViewPanel panel;
    private ViewerPipe pipe;
    private Camera camera;
    private PresentationController presentationController;
    private int lastEdgeID;
    private HashMap<Integer, Color> relationColors;
    private boolean fullGraph;
    private boolean graphVisible;
    private static int MAX_NODES = 250;

    public GraphView(PresentationController presentationController) {
        super(new CardLayout());
        this.presentationController = presentationController;
        graph = new MultiGraph("GraphView");
        graph.setAutoCreate(true);
        graph.setStrict(false);
        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD );
        viewer.enableAutoLayout();
        panel = viewer.addDefaultView(false);
        panel.setMouseManager(new CustomMouseManager());
        panel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                double newZoom = camera.getViewPercent()+e.getWheelRotation()*0.1;
                if(newZoom >= 0.1 && newZoom <= 5) {
                    camera.setViewPercent(newZoom);
                }
            }
        });
        camera = panel.getCamera();
        pipe = viewer.newViewerPipe();
        pipe.addViewerListener(this);
        pipe.addSink(graph);
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while(true) {
                            pipe.pump();
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        ).start();
        setBackground(Color.WHITE);
        add(panel, "graph");
        add(new JLabel(
                "<html><h3 style=\"text-align: center;\">The graph is too big</h3>\n<p style=\"text-align: center;\">It won't be shown entirely unless it decreases.<br>However, If you do a search and select a result, the result's related nodes will be shown</p></html>",
                SwingConstants.CENTER),
                "big");
        add(new JLabel(
                "<html><h3 style=\"text-align: center;\">The graph is empty</h3>\n<p style=\"text-align: center;\">Add some elements to see data or import an existing graph</p></html>",
                SwingConstants.CENTER),
                "empty");
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
        generateRelationColors();
        camera.resetView();
        graphVisible = true;
        lastNode = null;
        fullGraph = generateGraph();
        if(fullGraph) {
            if(presentationController.getSize() > 0) {
                ((CardLayout)getLayout()).show(this, "graph");
                graphVisible = true;
            } else {
                ((CardLayout)getLayout()).show(this, "empty");
                graphVisible = false;
            }
        } else {
            ((CardLayout)getLayout()).show(this, "big");
            graphVisible = false;
        }
    }

    private boolean generateGraph() {
        if(presentationController.getSize() > MAX_NODES) {
            return false;
        }
        for(NodeType type : NodeType.values()) {
            int[] ids = presentationController.getNodes(type);
            for(int i = 0; i < ids.length; ++i) {
                i_addNode(type, ids[i]);
            }
        }
        for(Node from : graph.getNodeSet()) {
            addEdges(from, false);
        }
        return true;
    }

    private Node generateGraph(Node node) {
        if(!fullGraph && nodeFocused != node) {
            NodeType type = node.getAttribute("nodetype");
            int id = node.getAttribute("originalID");
            refresh();
            node = i_addNode(type, id);
            graphVisible = true;
            ((CardLayout)getLayout()).show(this, "graph");
            Queue<Node> queue = new LinkedList<Node>();
            queue.add(node);
            int level = 1;
            int elementsThisLevel = 1;
            int elementsNextLevel = 0;
            while(!queue.isEmpty() && level <= (presentationController.getSize() <= MAX_NODES*2 ? 2 : 1)) {
                addEdges(queue.element(), true);
                for(Edge edge : queue.element().getEachEdge()) {
                    queue.add(edge.getOpposite(queue.element()));
                    ++elementsNextLevel;
                }
                queue.poll();
                if(--elementsThisLevel == 0) {
                    elementsThisLevel = elementsNextLevel;
                    elementsNextLevel = 0;
                    ++level;
                }
            }
            lastNode = null;
            camera.resetView();
        } else {
            double[] pos = org.graphstream.algorithm.Toolkit.nodePosition(node);
            camera.setViewCenter(pos[0], pos[1], pos[2]);
        }
        return node;
    }

    public void generateGraph(NodeType type, int id) {
        Node node = i_addNode(type, id);
        node = generateGraph(node);
        setEdgeLabel(node, true, null);
        lastNode = node;
        nodeFocused = node;
    }

    private boolean setEdge(Node from, NodeType toType, int toID, int relationID, boolean addition, boolean force) {
        Node to = graph.getNode(toType.toString() + "_" + String.valueOf(toID));
        if(to != null) {
            for(Edge edge : from.getEachEdge()) {
                if(edge.getOpposite(from) == to) {
                    if((int) edge.getAttribute("relationID") == relationID) {
                        if(addition) {
                            return false;
                        } else {
                            graph.removeEdge(edge);
                            return true;
                        }
                    }
                }
            }
            if(!addition) {
                return false;
            } else {
                Edge edge = graph.addEdge(String.valueOf(++lastEdgeID), from.getId(), to.getId());
                edge.addAttribute("relationID", relationID);
                Color color = relationColors.get(relationID);
                edge.addAttribute("ui.style", "fill-color: rgb(" + String.valueOf(color.getRed()) + ", " + String.valueOf(color.getGreen()) + ", " + String.valueOf(color.getBlue()) + ");");
                return true;
            }
        } else {
            if(force) {
                i_addNode(toType, toID);
                return setEdge(from, toType, toID, relationID, addition, false);
            } else {
                return false;
            }
        }
    }

    private void setEdge(int relationID, NodeType typeA, int nodeA, NodeType typeB, int nodeB, boolean addition) {
        if(graphVisible) {
            Node from = graph.getNode(typeA.toString() + "_" + String.valueOf(nodeA));
            if(from == null) {
                from = graph.getNode(typeB.toString() + "_" + String.valueOf(nodeB));
                typeB = typeA;
                nodeB = nodeA;
            }
            if(from != null) {
                setEdge(from, typeB, nodeB, relationID, addition, true);
            }
        }
    }

    public void addEdge(int relationID, NodeType typeA, int nodeA, NodeType typeB, int nodeB) {
         setEdge(relationID, typeA, nodeA, typeB, nodeB, true);
    }

    public void removeEdge(int relationID, NodeType typeA, int nodeA, NodeType typeB, int nodeB) {
        setEdge(relationID, typeA, nodeA, typeB, nodeB, false);
    }

    private void addEdges(Node from, boolean force) {
        NodeType typeFrom = from.getAttribute("nodetype");
        int nodeID = from.getAttribute("originalID");
        for(int relationID : presentationController.getRelations(typeFrom)) {
            for(int nodeTo : presentationController.getEdges(relationID, typeFrom, nodeID)) {
                setEdge(from, presentationController.getNodeTypeTo(relationID, typeFrom), nodeTo, relationID, true, force);
            }
        }
    }

    private void addAttributes(Node node, NodeType type, int id) {
        node.addAttribute("nodetype", type);
        node.addAttribute("originalID", id);
        node.addAttribute("ui.style", "fill-color: " + getNodeColor(type) + ";");
        setNodeLabel(node, presentationController.getNodeValue(type, id));
    }

    private Node i_addNode(NodeType type, int id) {
        Node node = graph.getNode(type.toString() + "_" + String.valueOf(id));
        if(node == null) {
            node = graph.addNode(type.toString() + "_" + String.valueOf(id));
            addAttributes(node, type, id);
        }
        return node;
    }

    public void addNode(NodeType type, int id) {
        if(presentationController.getSize() == 1) {
            fullGraph = true;
            graphVisible = true;
            ((CardLayout)getLayout()).show(this, "graph");
        }
        if(graphVisible) {
            nodeFocused = i_addNode(type, id);
        }
        camera.resetView();
    }

    public void removeNode(NodeType type, int id) {
        if(graphVisible) {
            Node node = graph.getNode(type.toString() + "_" + String.valueOf(id));
            if(node != null) {
                graph.removeNode(node);
            }
            camera.resetView();
            if(presentationController.getSize() == 0) {
                fullGraph = false;
                graphVisible = false;
                ((CardLayout)getLayout()).show(this, "empty");
            }
        }
    }

    public void setNodeValue(NodeType type, int id, String value) {
        if(graphVisible) {
            Node node = graph.getNode(type.toString() + "_" + String.valueOf(id));
            if(node != null) {
                setNodeLabel(node, value);
            }
        }
    }

    public void showGraph() {
        ((CardLayout)getParent().getLayout()).show(getParent(), "GraphView");
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

    private void setEdgeLabel(Node node, boolean enabled, Node actNode) {
        for(Edge edge : node.getEachEdge()) {
            if(enabled) {
                edge.addAttribute("ui.label", presentationController.getRelationName(edge.getAttribute("relationID")));
            } else {
                if(actNode == null || edge.getOpposite(node) != actNode) {
                    edge.removeAttribute("ui.label");
                }
            }
        }
        if(enabled && lastNode != null && lastNode != node) {
            setEdgeLabel(lastNode, false, node);
        }
    }

    private void setNodeLabel(Node node, String label) {
        if(label.length() > 15) {
            label = label.substring(0, 15);
            label = label.concat("...");
        }
        node.addAttribute("ui.label", label);
    }

    private long lastClick = 0;
    private Node lastNode = null;
    private Node nodeFocused = null;

    @Override
    public void buttonPushed(String id) {
        Node node = graph.getNode(id);
        setEdgeLabel(node, true, null);
        long actClick = System.currentTimeMillis();
        if(actClick-lastClick < 400) {
            boolean detailedInfo = nodeFocused == node;
            node = generateGraph(node);
            nodeFocused = node;
            if(detailedInfo) {
                ModifyElementView modifyElementView = new ModifyElementView(presentationController, presentationController.mainFrame, node.getAttribute("originalID"), node.getAttribute("nodetype"));
                modifyElementView.setVisible(true);
            }
        }
        lastClick = actClick;
        lastNode = node;
    }

    @Override
    public void viewClosed(String viewName) {}

    @Override
    public void buttonReleased(String id) {}
}
