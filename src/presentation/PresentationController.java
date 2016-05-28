package presentation;

import domain.*;
import scala.Int;

import java.awt.event.WindowEvent;
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
    }

    public void importDB(String path) {
        domainController.importDB(path);
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

    public String getNodeValue(NodeType type, int id) {
        return domainController.getNodeValue(type, id);
    }

    public int addRelation(NodeType A, NodeType B, String name) {
        return domainController.addRelation(A, B, name);
    }

    public void removeRelation(int id) {
        domainController.removeRelation(id);
    }

    public ArrayList<Integer> getRelations(NodeType type) {
        return domainController.getRelations(type);
    }

    public String getRelationName(int id) {
        return domainController.getRelationName(id);
    }

    public void addEdge(int relationID, NodeType typeA, int nodeA, NodeType typeB, int nodeB) {
        domainController.addEdge(relationID, typeA, nodeA, typeB, nodeB);
    }

    public void removeEdge(int relationID, NodeType typeA, int nodeA, NodeType typeB, int nodeB) {
        domainController.removeEdge(relationID, typeA, nodeA, typeB, nodeB);
    }

    public ArrayList<Node> getEdges(int relationID, Node node) {
        return domainController.getEdges(relationID, node);
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
