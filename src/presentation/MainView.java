package presentation;

import domain.*;
import persistence.EdgeSerializer;
import persistence.LabelSerializer;
import persistence.NodeSerializer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import static domain.NodeType.*;

/**
 * Created by Magi on 10/05/2016.
 */
public class MainView {

    public int Clikeds;

    private JFrame viewFrame = new JFrame("MainFrame");
    private JPanel mainPanel = new JPanel();
    private JButton labelButton = new JButton("Label");
    private JButton termButton = new JButton("Term");
    private JButton conferenceButton = new JButton("Conference");
    private JButton authorButton = new JButton("Author");
    private JButton paperButton = new JButton("Paper");

    private JButton addEleButton = new JButton("Add Element");
    //^^Afergir button al panel de buttons, pero no es sempre visible
    private JPanel buttonsPanel = new JPanel();

    private JTextField busqueda = new JTextField("Type to search");
    private JButton searchButton = new JButton("Search");
    //^^ Fer visible nomes quan les busquedes siguin possibles.

    private JTextField relationalSearchOrigin = new JTextField("From");
    private JTextField relationalSearchDestination = new JTextField("To");

    private JMenuBar menubar = new JMenuBar();
    private JMenu menuFile = new JMenu("File");
    private JMenuItem New = new JMenuItem("New Graph");
    private JMenuItem Export = new JMenuItem("Export database");
    private JMenuItem Import = new JMenuItem("Import database");

    public MainView(){
        initialize_components();
    }

    private void initialize_components() {
        initialize_mainPanel();
        initialize_viewFrame();
        initialize_Buttons();
        initialize_menuView();
        listeners_plz();

    }

    private void one_clicked(boolean active){
        if(active){
            addEleButton.setVisible(true);
            searchButton.setVisible(true);
            busqueda.setVisible(true);
        }
        else{
            addEleButton.setVisible(false);
            searchButton.setVisible(false);
            busqueda.setVisible(false);
        }
    }



    private void listeners_plz() {

        authorButton.addActionListener(new ActionListener() {
            boolean clicked = false;
            public void actionPerformed(ActionEvent e) {
                //DESCLIKEAR
                if(clicked == true) {
                    authorButton.setBackground(Color.cyan);
                    clicked = false;
                    --Clikeds;
                    if(Clikeds <= 1){
                        more_clicked(false);
                    }
                    if(Clikeds == 0) one_clicked(false);
                    if(Clikeds == 1) one_clicked(true);
                //CLIKEAR
                } else {
                    clicked = true;
                    authorButton.setBackground(Color.blue);

                    //Búsqueda relacional
                    if(Clikeds >= 1){
                        one_clicked(false);
                        more_clicked(true);
                    }
                    //Búsqueda simple
                    else {
                        one_clicked(true);
                    }
                    ++Clikeds;
                }
            }
        });

        labelButton.addActionListener(new ActionListener() {
            boolean clicked = false;
            public void actionPerformed(ActionEvent e) {
                //DESCLIKEAR
                if(clicked == true) {
                    labelButton.setBackground(Color.cyan);
                    clicked = false;
                    --Clikeds;
                    if(Clikeds <= 1){
                        more_clicked(false);
                    }
                    if(Clikeds == 0) one_clicked(false);
                    if(Clikeds == 1) one_clicked(true);
                    //CLIKEAR
                } else {
                    clicked = true;
                    labelButton.setBackground(Color.blue);

                    //Búsqueda relacional
                    if(Clikeds >= 1){
                        one_clicked(false);
                        more_clicked(true);
                    }
                    //Búsqueda simple
                    else {
                        one_clicked(true);
                    }
                    ++Clikeds;
                }
            }
        });

        paperButton.addActionListener(new ActionListener() {
            boolean clicked = false;
            public void actionPerformed(ActionEvent e) {
                //DESCLIKEAR
                if(clicked == true) {
                    paperButton.setBackground(Color.cyan);
                    clicked = false;
                    --Clikeds;
                    if(Clikeds <= 1){
                        more_clicked(false);
                    }
                    if(Clikeds == 0) one_clicked(false);
                    if(Clikeds == 1) one_clicked(true);
                    //CLIKEAR
                } else {
                    clicked = true;
                    paperButton.setBackground(Color.blue);

                    //Búsqueda relacional
                    if(Clikeds >= 1){
                        one_clicked(false);
                        more_clicked(true);
                    }
                    //Búsqueda simple
                    else {
                        one_clicked(true);
                    }
                    ++Clikeds;
                }
            }
        });

        conferenceButton.addActionListener(new ActionListener() {
            boolean clicked = false;
            public void actionPerformed(ActionEvent e) {
                //DESCLIKEAR
                if(clicked == true) {
                    conferenceButton.setBackground(Color.cyan);
                    clicked = false;
                    --Clikeds;
                    if(Clikeds <= 1){
                        more_clicked(false);
                    }
                    if(Clikeds == 0) one_clicked(false);
                    if(Clikeds == 1) one_clicked(true);
                    //CLIKEAR
                } else {
                    clicked = true;
                    conferenceButton.setBackground(Color.blue);

                    //Búsqueda relacional
                    if(Clikeds >= 1){
                        one_clicked(false);
                        more_clicked(true);
                    }
                    //Búsqueda simple
                    else {
                        one_clicked(true);
                    }
                    ++Clikeds;
                }
            }
        });

        termButton.addActionListener(new ActionListener() {
            boolean clicked = false;
            public void actionPerformed(ActionEvent e) {
                //DESCLIKEAR
                if(clicked == true) {
                    termButton.setBackground(Color.cyan);
                    clicked = false;
                    --Clikeds;
                    if(Clikeds <= 1){
                        more_clicked(false);
                    }
                    if(Clikeds == 0) one_clicked(false);
                    if(Clikeds == 1) one_clicked(true);
                    //CLIKEAR
                } else {
                    clicked = true;
                    termButton.setBackground(Color.blue);

                    //Búsqueda relacional
                    if(Clikeds >= 1){
                        one_clicked(false);
                        more_clicked(true);
                    }
                    //Búsqueda simple
                    else {
                        one_clicked(true);
                    }
                    ++Clikeds;
                }
            }
        });



    }

    private void more_clicked(boolean b) {
        if(b){
            relationalSearchDestination.setVisible(true);
            relationalSearchOrigin.setVisible(true);
            searchButton.setVisible(true);
        } else {
            relationalSearchDestination.setVisible(false);
            relationalSearchOrigin.setVisible(false);
            searchButton.setVisible(false);
        }
    }

    private void initialize_menuView() {
        menuFile.add(New);
        menuFile.add(Export);
        menuFile.add(Import);
        menubar.add(menuFile);
        viewFrame.setJMenuBar(menubar);
    }

    private void initialize_mainPanel() {
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(buttonsPanel);
    }

    private void initialize_Buttons() {
        buttonsPanel.setLayout(null);
        buttonsPanel.add(paperButton);
        paperButton.setBounds(300,335, 100,30);
        paperButton.setBackground(Color.cyan);
        buttonsPanel.add(conferenceButton, BorderLayout.WEST);
        conferenceButton.setBounds(475, 335, 100, 30);
        conferenceButton.setBackground(Color.cyan);
        buttonsPanel.add(termButton, BorderLayout.SOUTH);
        termButton.setBounds(300, 510, 100, 30);
        termButton.setBackground(Color.cyan);
        buttonsPanel.add(authorButton, BorderLayout.EAST);
        authorButton.setBounds(125, 335, 100, 30);
        authorButton.setBackground(Color.cyan);
        buttonsPanel.add(labelButton, BorderLayout.NORTH);
        labelButton.setBounds(300, 125, 100, 30);
        labelButton.setBackground(Color.cyan);
        buttonsPanel.add(addEleButton);
        addEleButton.setBounds(525, 175, 125, 30);
        addEleButton.setVisible(false);
        buttonsPanel.add(busqueda);
        busqueda.setBounds(125, 590, 375, 30);
        busqueda.setVisible(false);
        buttonsPanel.add(searchButton);
        searchButton.setBounds(560, 590, 100, 30);
        searchButton.setVisible(false);
        buttonsPanel.add(relationalSearchOrigin);
        relationalSearchOrigin.setBounds(125, 590, 172, 30);
        relationalSearchOrigin.setVisible(false);
        buttonsPanel.add(relationalSearchDestination);
        relationalSearchDestination.setBounds(327, 590, 170, 30);
        relationalSearchDestination.setVisible(false);
    }

    private void initialize_viewFrame(){
        viewFrame.setMinimumSize(new Dimension(700,700));
        viewFrame.setPreferredSize(viewFrame.getMinimumSize());
        viewFrame.setResizable(false);
        viewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contentPane = (JPanel) viewFrame.getContentPane();
        contentPane.add(mainPanel);
    }


    public void setEnable() {
        viewFrame.pack();
        viewFrame.setVisible(true);
    }

    public static class PersistenceController {

        private Graph graph;

        private java.util.List<String> readFile(String path) {
            java.util.List<String> toReturn = new ArrayList<>();
            BufferedReader br = null;
            try {
                String sCurrentLine;
                String absolutePath = new File(path).getAbsolutePath();
                FileReader fr = new FileReader(absolutePath);
                br = new BufferedReader(fr);
                while ((sCurrentLine = br.readLine()) != null) {
                    toReturn.add(sCurrentLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return toReturn;
        }

        private void writeFile(String path, java.util.List<String> strings) {
            try {
                String absolutePath = new File(path).getAbsolutePath();
                File file = new File(absolutePath);
                file.createNewFile();
                FileWriter fw = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw);
                for (String s : strings) {
                    out.println(s);
                }
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void clearDir(String path) {
            String absolutePath = new File(path).getAbsolutePath();
            File folder = new File(absolutePath);
            if (!folder.exists()) {
                folder.mkdir();
            }
            File flist[] = folder.listFiles();
            for (int i = 0; i < flist.length; i++) {
                String pes = flist[i].getName();
                if (pes.endsWith(".txt")) {
                    flist[i].delete();
                }
            }
        }

        private void exportNodes(String path) {
            for (NodeType n : NodeType.values()) {
                if (n != LABEL) {
                    java.util.List<String> strings = new ArrayList<>();
                    domain.Container<Node>.ContainerIterator it = graph.getNodeIterator(n);
                    while (it.hasNext()) {
                        Node node = it.next();
                        NodeSerializer serializer = new NodeSerializer(node);
                        strings.add(serializer.getData());
                    }
                    String filepath = path + n.toString().toLowerCase() + ".txt";
                    writeFile(filepath, strings);
                }
            }
        }

        private void exportEdges(String path) throws GraphException {
            Map<String, ArrayList<String>> strings = new HashMap<String, ArrayList<String>>();
            strings.put("author_label", new ArrayList<>());
            strings.put("conf_label", new ArrayList<>());
            strings.put("paper_author", new ArrayList<>());
            strings.put("paper_conf", new ArrayList<>());
            strings.put("paper_label", new ArrayList<>());
            strings.put("paper_term", new ArrayList<>());

            for (NodeType n : NodeType.values()) {
                if (n != LABEL && n != TERM) {
                    domain.Container<Node>.ContainerIterator it = graph.getNodeIterator(n);
                    while (it.hasNext()) {
                        EdgeSerializer serializer = null;
                        ArrayList<Node> relation;
                        Node node1 = it.next();
                        if (n == AUTHOR) {
                            relation = graph.getEdges(3, node1);
                            for (int i = 0; i < relation.size(); ++i) {
                                Node node2 = relation.get(i);
                                serializer = new LabelSerializer(node1, node2);
                                strings.get("author_label").add(serializer.getData());
                            }
                        } else if (n == CONF) {
                            relation = graph.getEdges(5, node1);
                            for (int i = 0; i < relation.size(); ++i) {
                                Node node2 = relation.get(i);
                                serializer = new LabelSerializer(node1, node2);
                                strings.get("author_label").add(serializer.getData());
                            }
                        } else if (n == PAPER) {
                            relation = graph.getEdges(0, node1);
                            for (int i = 0; i < relation.size(); ++i) {
                                Node node2 = relation.get(i);
                                serializer = new EdgeSerializer(node1, node2);
                                strings.get("paper_author").add(serializer.getData());
                            }
                            relation = graph.getEdges(1, node1);
                            for (int i = 0; i < relation.size(); ++i) {
                                Node node2 = relation.get(i);
                                serializer = new EdgeSerializer(node1, node2);
                                strings.get("paper_conf").add(serializer.getData());
                            }
                            relation = graph.getEdges(4, node1);
                            for (int i = 0; i < relation.size(); ++i) {
                                Node node2 = relation.get(i);
                                serializer = new LabelSerializer(node1, node2);
                                strings.get("paper_label").add(serializer.getData());
                            }
                            relation = graph.getEdges(2, node1);
                            for (int i = 0; i < relation.size(); ++i) {
                                Node node2 = relation.get(i);
                                serializer = new EdgeSerializer(node1, node2);
                                strings.get("paper_term").add(serializer.getData());
                            }
                        }
                    }
                }
            }
            writeFile(path + "author_label.txt", strings.get("author_label"));
            writeFile(path + "conf_label.txt", strings.get("conf_label"));
            writeFile(path + "paper_author.txt", strings.get("paper_author"));
            writeFile(path + "paper_conf.txt", strings.get("paper_conf"));
            writeFile(path + "paper_label.txt", strings.get("paper_label"));
            writeFile(path + "paper_term.txt", strings.get("paper_term"));

            /*
            Iterator it = graph.getRelationIterator();
            while(it.hasNext()){
                Relation r = (Relation) it.next();
                if(!r.isDefault()){
                    System.out.println(r.getName());
                }
            }
            */

        }

        public PersistenceController(Graph graph) {
            this.graph = graph;
            try {
                graph.addNode(graph.createNode(LABEL, "Database"), 0);
                graph.addNode(graph.createNode(LABEL, "Data Mining"), 1);
                graph.addNode(graph.createNode(LABEL, "AI"), 2);
                graph.addNode(graph.createNode(LABEL, "Information Retreival"), 3);
            } catch (GraphException e) {
                e.printStackTrace();
            }
        }

        public void importNodes(String path, NodeType type) {
            java.util.List<String> strings = readFile(path);
            for (String s : strings) {
                NodeSerializer serializer = new NodeSerializer(s);
                Node node = graph.createNode(type, serializer.getName());
                try {
                    graph.addNode(node, serializer.getId());
                } catch (GraphException e) {
                    e.printStackTrace();
                }
            }
        }

        public void importEdges(String path, NodeType type1, NodeType type2) {
            java.util.List<String> strings = readFile(path);
            for (String s : strings) {
                int relId = -1;
                EdgeSerializer serializer = null;
                if (type1.equals(AUTHOR) && type2.equals(NodeType.LABEL)) {
                    relId = 3;
                    serializer = new LabelSerializer(graph, s, type1, type2);
                } else if (type1.equals(NodeType.CONF) && type2.equals(NodeType.LABEL)) {
                    relId = 5;
                    serializer = new LabelSerializer(graph, s, type1, type2);
                } else if (type1.equals(NodeType.PAPER) && type2.equals(AUTHOR)) {
                    relId = 0;
                    serializer = new EdgeSerializer(graph, s, type1, type2);
                } else if (type1.equals(NodeType.PAPER) && type2.equals(NodeType.CONF)) {
                    relId = 1;
                    serializer = new EdgeSerializer(graph, s, type1, type2);
                } else if (type1.equals(NodeType.PAPER) && type2.equals(NodeType.LABEL)) {
                    relId = 4;
                    serializer = new LabelSerializer(graph, s, type1, type2);
                } else if (type1.equals(NodeType.PAPER) && type2.equals(NodeType.TERM)) {
                    relId = 2;
                    serializer = new EdgeSerializer(graph, s, type1, type2);
                }
                try {
                    Node node1 = serializer.getNode1();
                    Node node2 = serializer.getNode2();
                    graph.addEdge(relId, node1, node2);
                } catch (GraphException e) {
                    e.printStackTrace();
                }
            }
        }

        public void importGraph(String path){
            importNodes(path + "author.txt", NodeType.AUTHOR);
            importNodes(path + "conf.txt", NodeType.CONF);
            importNodes(path + "paper.txt", NodeType.PAPER);
            importNodes(path + "term.txt", NodeType.TERM);
            importEdges(path + "author_label.txt", NodeType.AUTHOR, NodeType.LABEL);
            importEdges(path + "conf_label.txt", NodeType.CONF, NodeType.LABEL);
            importEdges(path + "paper_author.txt", NodeType.PAPER, NodeType.AUTHOR);
            importEdges(path + "paper_conf.txt", NodeType.PAPER, NodeType.CONF);
            importEdges(path + "paper_label.txt", NodeType.PAPER, NodeType.LABEL);
            importEdges(path + "paper_term.txt", NodeType.PAPER, NodeType.TERM);
        }

        public void exportGraph(String path) {
            clearDir(path);
            try {
                exportNodes(path);
                exportEdges(path);
            } catch (GraphException e){
                e.printStackTrace();
            }

        }

    }
}
