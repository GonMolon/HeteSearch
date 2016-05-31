package presentation;

import domain.DomainController;
import domain.NodeType;

import java.util.ArrayList;

public class CommandLineSearch implements Runnable {

    String search;
    DomainController dc;

    public CommandLineSearch(String search, DomainController dc) {
        this.search = search;
        this.dc = dc;
    }

    private ArrayList<Integer> parseRelationStructure(String rs) {
        ArrayList<Integer> res = new ArrayList<>();
        String[] relations = rs.split("-");
        for(int i = 0; i < relations.length; ++i) {
            res.add(Integer.parseInt(relations[i]));
        }
        return res;
    }

    public void run() {
        // Parsing. Let's get the first parameter.
        String[] params = search.split(" ");
        NodeType origin, dest;
        ArrayList<Number[]> results;
        origin = CommandLineInterface.parseType(params[1]);
        dest = CommandLineInterface.parseType(params[2]);
        ArrayList<Integer> relationStructure = parseRelationStructure(params[3]);
        if (params.length == 4) {
            // FreeSearch
            results = dc.freeSearch(origin, relationStructure, dest);
        } else if (params.length == 5) {
            // OriginSearch
            results = dc.originSearch(origin, Integer.parseInt(params[4]), relationStructure, dest);
        } else {
            // OriginDestinationSearch
            results = new ArrayList<>();
            results.add(dc.originDestinationSearch(origin, Integer.parseInt(params[4]), relationStructure, dest, Integer.parseInt(params[5])));
        }

        System.out.println("Search done. Printing results:");

        for(int i = 0; i < results.size(); ++i) {
            Number[] res = results.get(i);
            System.out.println(dc.getNodeValue(origin, res[0].intValue()) + " - " + dc.getNodeValue(dest, res[1].intValue()) + ". Hetesim: " + res[2]);
        }
    }

}
