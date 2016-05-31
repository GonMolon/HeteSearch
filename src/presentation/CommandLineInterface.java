package presentation;

import domain.DomainController;
import domain.NodeType;

import java.util.ArrayList;
import java.util.Scanner;

public class CommandLineInterface implements Runnable {

    private Scanner s;
    private boolean bgraph;
    private domain.DomainController dc;

    public CommandLineInterface() {
        s = new Scanner(System.in);
        bgraph = false;
        dc = new DomainController();
    }

    public static NodeType parseType(String type) {
        type = type.toLowerCase();
        if (type.equals("author")) return NodeType.AUTHOR;
        else if (type.equals("conference")) return NodeType.CONF;
        else if (type.equals("term")) return NodeType.TERM;
        else if (type.equals("paper")) return NodeType.PAPER;
        else return NodeType.LABEL;
    }

    public void run() {
        String command;
        command = s.next();

        while (!command.equals("exit")) {
            if (command.equals("new")) {
                dc.newDB();
                bgraph = true;
                System.out.println("New graph created.");
            } else if (command.equals("import")) {
                dc.newDB();
                dc.importDB(s.nextLine());
                bgraph = true;
                System.out.println("Graph successfully imported.");
            } else if (bgraph && command.equals("export")) {
                dc.exportDB(s.nextLine());
                System.out.println("Graph successfully exported.");
            } else if (bgraph && command.equals("addnode")) {
                int newid = dc.addNode(parseType(s.next()), s.nextLine());
                System.out.println("Node added with id "+newid+".");
            } else if (bgraph && command.equals("addrelation")) {
                int newid = dc.addRelation(parseType(s.next()), parseType(s.next()), s.nextLine());
                System.out.println("Relation added with id "+newid+".");
            } else if (bgraph && command.equals("removerelation")) {
                dc.removeRelation(s.nextInt());
                System.out.println("Relation removed.");
            } else if (bgraph && command.equals("listrelations")) {
                ArrayList<Integer> rels = dc.getRelations();
                for(int i = 0; i < rels.size(); ++i)
                    System.out.println(rels.get(i) + " - " + dc.getRelationName(rels.get(i)));
            } else if (bgraph && command.equals("hetesearch")) {
                CommandLineSearch cls = new CommandLineSearch(s.nextLine(), dc);
                cls.run();
            } else if (bgraph && command.equals("search")) {
                NodeType nt = parseType(s.next());
                Integer[] results = dc.simpleSearch(nt, s.nextLine());
                System.out.println("Search results:");
                for(int i = 0; i < results.length; ++i)
                    System.out.println(results[i] + " - " + dc.getNodeValue(nt, results[i].intValue()));
            } else if (bgraph && command.equals("removenode")) {
                NodeType nt = parseType(s.next());
                int id = s.nextInt();
                if (dc.nodeExists(nt, id)) {
                    dc.removeNode(parseType(s.next()), s.nextInt());
                    System.out.println("Node removed.");
                } else {
                    System.out.println("Node with id "+id+" doesn't exist.");
                }
            } else if (bgraph && command.equals("setnode")) {
                NodeType nt = parseType(s.next());
                int id = s.nextInt();
                if (dc.nodeExists(nt, id)) {
                    dc.setNodeValue(parseType(s.next()), s.nextInt(), s.nextLine());
                    System.out.println("Node value modified.");
                } else {
                    System.out.println("Node with id "+id+" doesn't exist.");
                }
            } else if (bgraph && command.equals("addedge")) {
                dc.addEdge(s.nextInt(), parseType(s.next()), s.nextInt(), parseType(s.next()), s.nextInt());
                System.out.println("Edge between both nodes added.");
            } else if (bgraph && command.equals("removeedge")) {
                dc.removeEdge(s.nextInt(), parseType(s.next()), s.nextInt(), parseType(s.next()), s.nextInt());
                System.out.println("Edge between both nodes removed.");
            } else if (bgraph && command.equals("listedges")) {
                ArrayList<Integer> edges = dc.getEdges(s.nextInt(), parseType(s.next()), s.nextInt());
                for(int i = 0; i < edges.size(); ++i)
                    System.out.println(edges.get(i));
            } else {
                System.out.println ("Unknown command. Please read the documentation included for more information.");
                s.nextLine();
            }
            command = s.next();
        }
    }
}
