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
        mainView.show();
        searchView = new SearchView(this);


        addRelation(NodeType.AUTHOR, NodeType.PAPER, "TEST");
    }

    protected void newDB() {
        domainController.newDB();
    }

    protected void importDB(String path) {
        domainController.importDB(path);
    }

    protected void exportDB(String path) {
        domainController.exportDB(path);
    }

    protected void addNode(NodeType type, String value) {
        domainController.addNode(type, value);
    }

    protected void removeNode(NodeType type, int id) {
        domainController.removeNode(type, id);
    }

    protected int addRelation(NodeType A, NodeType B, String name) {
        return domainController.addRelation(A, B, name);
    }

    protected void removeRelation(int id) {
        domainController.removeRelation(id);
    }

    protected String getRelationName(int id) {
        return domainController.getRelationName(id);
    }

    protected void addEdge(int relationID, NodeType typeA, int nodeA, NodeType typeB, int nodeB) {
        domainController.addEdge(relationID, typeA, nodeA, typeB, nodeB);
    }

    protected void removeEdge(int relationID, NodeType typeA, int nodeA, NodeType typeB, int nodeB) {
        domainController.removeEdge(relationID, typeA, nodeA, typeB, nodeB);
    }

    protected ArrayList<Node> getEdges(int relationID, Node node) {
        return domainController.getEdges(relationID, node);
    }

    protected ArrayList<GraphSearch.Result> simpleSearch(NodeType type, String filter) {
        return domainController.simpleSearch(type, filter);
    }

    protected ArrayList<GraphSearch.Result> freeSearch(NodeType typeA, ArrayList<Integer> relationStructure, NodeType typeB) {
        return domainController.freeSearch(typeA, relationStructure, typeB);
    }

    protected ArrayList<GraphSearch.Result> originSearch(NodeType typeA, int nodeFrom, ArrayList<Integer> rs, NodeType typeB) {
        return domainController.originSearch(typeA, nodeFrom, rs, typeB);
    }

    protected ArrayList<GraphSearch.Result> originDestinationSearch(NodeType typeA, int nodeFrom, ArrayList<Integer> rs, NodeType typeB, int nodeTo) {
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

    protected class PathInfo {
        public ArrayList<Integer> availableRelations;
        public ArrayList<NodeType> availableNodeTypes;

        public PathInfo() {
            availableRelations = new ArrayList<Integer>();
            availableNodeTypes = new ArrayList<NodeType>();
        }
    }
}
