package presentation;

import domain.*;

import java.util.ArrayList;

public class PresentationController {

    private DomainController domainController;
    private MainView mainView;
    private SearchView searchView;

    public PresentationController() {
        domainController = new DomainController();
        mainView = new MainView(this);
        mainView.setVisible(true);
        searchView = new SearchView(this);

        domainController.addRelation(NodeType.AUTHOR, NodeType.PAPER, "TEST");
    }

    public void newDB() {
        domainController.newDB();
    }

    public void importDB(String path) {
        domainController.importDB(path);
    }

    public void exportDB(String path) {
        domainController.exportDB(path);
    }

    public void addNode(NodeType type, String value) {
        domainController.addNode(type, value);
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

    public ArrayList<GraphSearch.Result> simpleSearch(NodeType type, String filter) {
        return domainController.simpleSearch(type, filter);
    }

    public ArrayList<GraphSearch.Result> freeSearch(NodeType typeA, ArrayList<Integer> relationStructure, NodeType typeB) {
        return domainController.freeSearch(typeA, relationStructure, typeB);
    }

    public ArrayList<GraphSearch.Result> originSearch(NodeType typeA, int nodeFrom, ArrayList<Integer> rs, NodeType typeB) {
        return domainController.originSearch(typeA, nodeFrom, rs, typeB);
    }

    public ArrayList<GraphSearch.Result> originDestinationSearch(NodeType typeA, int nodeFrom, ArrayList<Integer> rs, NodeType typeB, int nodeTo) {
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
