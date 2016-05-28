package domain;

import persistence.PersistenceController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class DomainController {

    private Graph graph;
    private PersistenceController persistenceController;

    public DomainController() {
        newDB();
    }

    public void newDB() {
        graph = new Graph();
    }

    public void importDB(String path) {
        graph = new Graph();
        persistenceController = new PersistenceController(graph);
        persistenceController.importGraph(path);
    }

    public void exportDB(String path) {
        persistenceController = new PersistenceController(graph);
        persistenceController.exportGraph(path);
    }

    public int addNode(NodeType type, String value) {
        Node node = graph.createNode(type, value);
        graph.addNode(node);
        return node.getId();
    }

    public void removeNode(NodeType type, int id) {
        try {
            graph.removeNode(type, id);
        } catch (GraphException e) {
            e.printStackTrace();
        }
    }

    public String getNodeValue(NodeType type, int id) {
        try {
            return graph.getNode(type, id).getValue();
        } catch (GraphException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setNodeValue(NodeType type, int id, String value) {
        try {
            graph.getNode(type, id).setValue(value);
        } catch (GraphException e) {
            e.printStackTrace();
        }
    }

    public int addRelation(NodeType A, NodeType B, String name) {
        Relation relation = new Relation(A, B, name);
        graph.addRelation(relation);
        return relation.getId();
    }

    public void removeRelation(int id) {
        try {
            graph.removeRelation(id);
        } catch (GraphException e) {
            e.printStackTrace();
        }
    }

    public String getRelationName(int id) {
        try {
            return graph.getRelation(id).getName();
        } catch (GraphException e) {
            e.printStackTrace();
            return null;
        }
    }

    public NodeType getNodeTypeTo(int relationId, NodeType from) {
        try {
            Relation relation = graph.getRelation(relationId);
            if (relation.getNodeTypeA() == from) {
                return relation.getNodeTypeB();
            }
            /*else*/ return relation.getNodeTypeA();
        } catch (GraphException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addEdge(int relationID, NodeType typeA, int nodeA, NodeType typeB, int nodeB) {
        try {
            graph.addEdge(relationID, typeA, nodeA, typeB, nodeB);
        } catch (GraphException e) {
            e.printStackTrace();
        }
    }

    public void removeEdge(int relationID, NodeType typeA, int nodeA, NodeType typeB, int nodeB) {
        try {
            graph.removeEdge(relationID, typeA, nodeA, typeB, nodeB);
        } catch (GraphException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> getEdges(int relationID, int nodeId, NodeType nodeType) {
        try {
            ArrayList<Node> connectedNodes = graph.getEdges(relationID, graph.getNode(nodeType, nodeId));
            ArrayList<Integer> connectedIds = new ArrayList<>();
            for (Node connectedNode : connectedNodes) {
                connectedIds.add(connectedNode.id);
            }
            return connectedIds;
        } catch (GraphException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Integer> simpleSearch(NodeType type, String filter) {
        SimpleSearch simpleSearch = new SimpleSearch(graph, type, filter);
        simpleSearch.search();
        ArrayList<Integer> results = new ArrayList<Integer>();
        for(GraphSearch.Result result : simpleSearch.getResults()) {
            results.add(result.from.getId());
        }
        return results;
    }

    public ArrayList<Number[]> freeSearch(NodeType typeA, ArrayList<Integer> relationStructure, NodeType typeB) {
        try {
            FreeSearch freeSearch = new FreeSearch(graph, generateRelationStructure(typeA, relationStructure, typeB));
            freeSearch.search();
            ArrayList<Number[]> results = new ArrayList<Number[]>();
            for(GraphSearch.Result result : freeSearch.getResults()) {
                results.add(new Number[]{result.from.getId(), result.to.getId(), result.hetesim});
            }
            return results;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Number[]> originSearch(NodeType typeA, int nodeFrom, ArrayList<Integer> rs, NodeType typeB) {
        try {
            RelationStructure relationStructure = generateRelationStructure(typeA, rs, typeB);
            OriginSearch originSearch = new OriginSearch(graph, relationStructure, graph.getNode(typeA, nodeFrom));
            originSearch.search();
            ArrayList<Number[]> results = new ArrayList<Number[]>();
            for(GraphSearch.Result result : originSearch.getResults()) {
                results.add(new Number[]{result.to.getId(), result.hetesim});
            }
            return results;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public double originDestinationSearch(NodeType typeA, int nodeFrom, ArrayList<Integer> rs, NodeType typeB, int nodeTo) {
        try {
            RelationStructure relationStructure = generateRelationStructure(typeA, rs, typeB);
            OriginDestinationSearch originDestinationSearch = new OriginDestinationSearch(graph, relationStructure, graph.getNode(typeA, nodeFrom), graph.getNode(typeB, nodeTo));
            originDestinationSearch.search();
            return originDestinationSearch.getResults().get(0).hetesim;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private RelationStructure generateRelationStructure(NodeType typeA, ArrayList<Integer> relationPath, NodeType typeB) throws RelationStructureException, GraphException {
        ArrayList<Relation> rs = new ArrayList<Relation>();
        for(int i = 0; i < relationPath.size(); ++i) {
            rs.add(graph.getRelation(relationPath.get(i)));
        }
        return new RelationStructure(typeA, rs, typeB);
    }

    public ArrayList<Integer> getAvailableRelations(NodeType from, NodeType to) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Container<Relation>.ContainerIterator iterator = graph.getRelationIterator();
        while(iterator.hasNext()) {
            Relation relation = iterator.next();
            if((relation.getNodeTypeA() == from && relation.getNodeTypeB() == to) || (relation.getNodeTypeA() == to && relation.getNodeTypeB() == from)) {
                result.add(relation.getId());
            }
        }
        return result;
    }

    public ArrayList<NodeType> getAvailableNodeTypes(NodeType from) {
        HashSet<NodeType> availableNodeTypes = new HashSet<NodeType>();
        Container<Relation>.ContainerIterator iterator = graph.getRelationIterator();
        int types = 0;
        while(iterator.hasNext() && types < 5) {
            Relation relation = iterator.next();
            if(from == relation.getNodeTypeA()) {
                availableNodeTypes.add(relation.getNodeTypeB());
                ++types;
            } else if(from == relation.getNodeTypeB()) {
                availableNodeTypes.add(relation.getNodeTypeA());
                ++types;
            }
        }
        return new ArrayList<NodeType>(availableNodeTypes);
    }

    public ArrayList<Integer> getRelations(NodeType type) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        Container<Relation>.ContainerIterator iterator = graph.getRelationIterator();
        while(iterator.hasNext()) {
            Relation relation = iterator.next();
            if(relation.getNodeTypeA() == type || relation.getNodeTypeB() == type) {
                result.add(relation.getId());
            }
        }
        return result;
    }
}
