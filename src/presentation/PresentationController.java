package presentation;

import domain.DomainController;
import domain.NodeType;

import java.util.ArrayList;

public class PresentationController {

    private DomainController domainController;
    private MainFrame mainFrame;

    public PresentationController() {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        domainController = new DomainController();
        mainFrame = new MainFrame(this);
        mainFrame.setVisible(true);
        domainController.addRelation(NodeType.AUTHOR, NodeType.PAPER, "TEST");
        //importDB("/home/gonmolon/GraphForTesting/");

    }

    public void newDB() {
        domainController.newDB();
        mainFrame.dispose();
        mainFrame = new MainFrame(this);
        mainFrame.setVisible(true);
        mainFrame.refreshGraphView();
    }

    public void importDB(String path) {
        domainController.importDB(path);
        mainFrame.refreshGraphView();
    }

    public void exportDB(String path) {
        domainController.exportDB(path);
    }

    public int addNode(NodeType type, String value) {
        return domainController.addNode(type, value);
    }

    public void removeNode(NodeType type, int id) {
        domainController.removeNode(type, id);
    }

    public int[] getNodes(NodeType type) {
        return domainController.getNodes(type);
    }

    public String getNodeValue(NodeType type, int id) {
        return domainController.getNodeValue(type, id);
    }

    public void setNodeValue(NodeType type, int id, String value) {
        domainController.setNodeValue(type, id, value);
    }

    public int addRelation(NodeType A, NodeType B, String name) {
        return domainController.addRelation(A, B, name);
    }

    public void removeRelation(int id) {
        domainController.removeRelation(id);
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

    public NodeType getNodeTypeTo(int relationId, NodeType from) {
        NodeType[] types = getNodeTypesFromRelation(relationId);
        if (types[0] == from) {
            return types[1];
        }
        /*else*/return types[0];
    }

    public NodeType[] getNodeTypesFromRelation(int relationID) {
        return domainController.getNodeTypesFromRelation(relationID);
    }

    public void addEdge(int relationID, NodeType typeA, int nodeA, NodeType typeB, int nodeB) {
        domainController.addEdge(relationID, typeA, nodeA, typeB, nodeB);
    }

    public void removeEdge(int relationID, NodeType typeA, int nodeA, NodeType typeB, int nodeB) {
        domainController.removeEdge(relationID, typeA, nodeA, typeB, nodeB);
    }

    public ArrayList<Integer> getEdges(int relationID, NodeType type, int nodeID) {
        return domainController.getEdges(relationID, type, nodeID);
    }

    public ArrayList<Integer> simpleSearch(NodeType type, String filter) {
        return domainController.simpleSearch(type, filter);
    }

    public ArrayList<Number[]> freeSearch(NodeType typeA, ArrayList<Integer> relationStructure, NodeType typeB) {
        return domainController.freeSearch(typeA, relationStructure, typeB);
    }

    public ArrayList<Number[]> originSearch(NodeType typeA, int nodeFrom, ArrayList<Integer> rs, NodeType typeB) {
        return domainController.originSearch(typeA, nodeFrom, rs, typeB);
    }

    public double originDestinationSearch(NodeType typeA, int nodeFrom, ArrayList<Integer> rs, NodeType typeB, int nodeTo) {
        return domainController.originDestinationSearch(typeA, nodeFrom, rs, typeB, nodeTo);
    }

    public PathInfo getPathInfo(NodeType from, NodeType to) {
        PathInfo pathInfo = new PathInfo();
        if(from != null) {
            pathInfo.availableRelations = domainController.getAvailableRelations(from, to);
        }
        pathInfo.availableNodeTypes = domainController.getAvailableNodeTypes(to);
        return pathInfo;
    }

    public class PathInfo {
        public ArrayList<Integer> availableRelations;
        public ArrayList<NodeType> availableNodeTypes;

        public PathInfo() {
            availableRelations = new ArrayList<Integer>();
            availableNodeTypes = new ArrayList<NodeType>();
        }
    }
}
