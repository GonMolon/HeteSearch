package presentation;

import domain.DomainController;
import domain.NodeType;

import java.util.ArrayList;

public class PresentationController {

    private DomainController domainController;
    public MainFrame mainFrame;

    public PresentationController() {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        domainController = new DomainController();
        mainFrame = new MainFrame(this);
        mainFrame.setVisible(true);
        //domainController.addRelation(NodeType.AUTHOR, NodeType.PAPER, "TEST");
    }

    public void newDB() {
        domainController.newDB();
        mainFrame.dispose();
        mainFrame = new MainFrame(this);
        mainFrame.setVisible(true);
        mainFrame.graphView.refresh();
    }

    public void importDB(String path) {
        domainController.newDB();
        mainFrame.dispose();
        mainFrame = new MainFrame(this);
        mainFrame.setVisible(true);
        domainController.importDB(path);
        mainFrame.graphView.refresh();
    }

    public void exportDB(String path) {
        domainController.exportDB(path);
    }

    public int addNode(NodeType type, String value) {
        int id = domainController.addNode(type, value);
        mainFrame.graphView.addNode(type, id);
        return id;
    }

    public void removeNode(NodeType type, int id) {
        domainController.removeNode(type, id);
        mainFrame.graphView.removeNode(type, id);
    }

    public int[] getNodes(NodeType type) {
        return domainController.getNodes(type);
    }

    public String getNodeValue(NodeType type, int id) {
        return domainController.getNodeValue(type, id);
    }

    public void setNodeValue(NodeType type, int id, String value) {
        domainController.setNodeValue(type, id, value);
        mainFrame.graphView.setNodeValue(type, id, value);
    }

    public int addRelation(NodeType A, NodeType B, String name) {
        int id = domainController.addRelation(A, B, name);
        mainFrame.graphView.refresh();
        return id;
    }

    public void removeRelation(int id) {
        domainController.removeRelation(id);
        mainFrame.graphView.refresh();
    }

    public ArrayList<Integer> getRelations() {
        return domainController.getRelations();
    }

    public ArrayList<Integer> getRelations(NodeType type) {
        return domainController.getRelations(type);
    }

    public String getRelationName(int id) {
        return domainController.getRelationName(id);
    }

    public boolean isCustomRelation(int id) {
        return true;
    }

    public NodeType getNodeTypeTo(int relationId, NodeType from) {
        NodeType[] types = domainController.getNodeTypesFromRelation(relationId);
        if (types[0] == from) {
            return types[1];
        } else {
            return types[0];
        }
    }

    public void addEdge(int relationID, NodeType typeA, int nodeA, NodeType typeB, int nodeB) {
        domainController.addEdge(relationID, typeA, nodeA, typeB, nodeB);
        mainFrame.graphView.addEdge(relationID, typeA, nodeA, typeB, nodeB);
    }

    public void removeEdge(int relationID, NodeType typeA, int nodeA, NodeType typeB, int nodeB) {
        domainController.removeEdge(relationID, typeA, nodeA, typeB, nodeB);
        mainFrame.graphView.removeEdge(relationID, typeA, nodeA, typeB, nodeB);
    }

    public ArrayList<Integer> getEdges(int relationID, NodeType type, int nodeID) {
        return domainController.getEdges(relationID, type, nodeID);
    }

    public Integer[] simpleSearch(NodeType type, String filter) {
        return domainController.simpleSearch(type, filter);
    }

    public ArrayList<Number[]> freeSearch(NodeType typeA, ArrayList<Integer> relationStructure, NodeType typeB) {
        return domainController.freeSearch(typeA, relationStructure, typeB);
    }

    public ArrayList<Number[]> originSearch(NodeType typeA, int nodeFrom, ArrayList<Integer> rs, NodeType typeB) {
        return domainController.originSearch(typeA, nodeFrom, rs, typeB);
    }

    public Number[] originDestinationSearch(NodeType typeA, int nodeFrom, ArrayList<Integer> rs, NodeType typeB, int nodeTo) {
        return domainController.originDestinationSearch(typeA, nodeFrom, rs, typeB, nodeTo);
    }

    public ArrayList[] getPathInfo(NodeType from, NodeType to) {
        ArrayList[] pathInfo = new ArrayList[2];
        if(from != null) {
            pathInfo[0] = domainController.getAvailableRelations(from, to);
        } else {
            pathInfo[0] = new ArrayList();
        }
        pathInfo[1] = domainController.getAvailableNodeTypes(to);
        return pathInfo;
    }

    public int getSize() {
        int size = 0;
        for(NodeType type : NodeType.values()) {
            size += domainController.getSize(type);
        }
        return size;
    }
}
