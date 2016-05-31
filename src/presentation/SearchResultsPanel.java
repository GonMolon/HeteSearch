package presentation;

import domain.NodeType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class SearchResultsPanel extends JPanel{
    private static final Color bgMasterColor    = Color.LIGHT_GRAY;
    private static final Color fgMasterColor    = Color.BLACK;
    private static final Color bgSelMasterColor = Color.BLUE;
    private static final Color fgSelMasterColor = Color.WHITE;
    private static final Color bgSlaveColor     = Color.WHITE;
    private static final Color fgSlaveColor     = Color.BLACK;
    private static final Color bgSelSlaveColor  = Color.BLUE;
    private static final Color fgSelSlaveColor  = Color.WHITE;

    private PresentationController presentationController;
    private int selectedId;
    private NodeType selectedType;
    private NodeType from;
    private NodeType to;
    private JList<Result> list;
    private DefaultListModel<Result> listModel;
    private ArrayList<ActionListener> listeners;
    private DecimalFormat df;

    public SearchResultsPanel(PresentationController presentationController) {
        super();
        this.presentationController = presentationController;
        initialize();
    }

    public void setResults(Integer[] results, NodeType type) {
        listModel = new DefaultListModel<>();
        this.from = type;
        for (Integer id : results) {
            String name = presentationController.getNodeValue(type, id);
            Result result = new Result(id, name, Result.MASTER);
            listModel.addElement(result);
        }
        list.setModel(listModel);
    }

    public void setResults(ArrayList<Number[]> results, NodeType from, NodeType to) {
        listModel = new DefaultListModel<>();
        this.from = from;
        this.to = to;
        int lastMaster = -1;
        for (Number[] nodeResults : results) {
            int masterId = nodeResults[0].intValue();
            if (masterId != lastMaster) {
                String masterName = presentationController.getNodeValue(from, masterId);
                listModel.addElement(new Result(masterId, masterName, Result.MASTER));
                lastMaster = masterId;
            }
            int slaveId = nodeResults[1].intValue();
            String slaveName = presentationController.getNodeValue(to, slaveId);
            double hetesim = nodeResults[2].doubleValue();
            listModel.addElement(new Result(slaveId, slaveName, Result.SLAVE, hetesim));
        }
        list.setModel(listModel);
    }

    public void setResults(Number[] results, NodeType from, NodeType to) {
        ArrayList<Number[]> arrayResults = new ArrayList<>(1);
        arrayResults.add(0, results);
        setResults(arrayResults, from, to);
    }

    public void addActionListener(ActionListener actionListener) {
        listeners.add(actionListener);
    }

    public int getSelectedId() {
        return selectedId;
    }

    public NodeType getSelectedType() {
        return selectedType;
    }

    private void initialize() {
        listeners = new ArrayList<>();
        list = new JList<>();
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new ResultRenderer());

        JScrollPane scrollPane = new JScrollPane(list);
        setLayout(new GridLayout(1, 1));
        add(scrollPane);
        setVisible(true);

        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                /*if (e.getClickCount() == 2)*/
                onClick(e.getClickCount());
            }
        });
        list.setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.HALF_UP);

    }

    private void onClick(Integer nClicks) {
        int selectedIndex = list.getSelectedIndex();
        if (selectedIndex != -1) {
            Result r = listModel.getElementAt(selectedIndex);
            selectedId = r.id;
            selectedType = r.isMaster() ? from : to;
            ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, nClicks.toString());
            for (ActionListener l : listeners) {
                l.actionPerformed(e);
            }
        }
    }

    private class Result {
        static private final int MAX_DISPLAYED_CHARS = 50;
        static public final int MASTER = 0;
        static public final int SLAVE  = 1;
        public int resultSocialStatusAmongOtherResults;
        public int id;
        public String name;
        public double hetesim;

        public Result(int id, String name, int socialStatus) {
            this.id = id;
            this.name = name;
            this.resultSocialStatusAmongOtherResults = socialStatus;
        }

        public Result(int id, String name, int socialStatus, double hetesim) {
            this(id, name, socialStatus);
            this.hetesim = hetesim;
        }

        public boolean isMaster() {
            return (resultSocialStatusAmongOtherResults == MASTER);
        }

        @Override
        public String toString() {
            String displayedName = name;
            if (displayedName.length() > MAX_DISPLAYED_CHARS) {
                displayedName = displayedName.substring(0, MAX_DISPLAYED_CHARS) + "...";
            }
            if (isMaster()) {
                return displayedName;
            }
            /*else*/ return (displayedName + " " + df.format(hetesim));
        }
    }

    private class ResultRenderer extends JLabel implements ListCellRenderer<Result> {

        public ResultRenderer() {
            super();
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Result> list, Result result, int index, boolean isSelected, boolean hasFocus) {
            setText(result.toString());
            boolean isMaster = result.isMaster();
            int alignment = isMaster ? LEFT : RIGHT;
            Color bgColor;
            Color fgColor;
            if (isMaster) {
                if (isSelected) {
                    bgColor = bgSelMasterColor;
                    fgColor = fgSelMasterColor;
                    setOpaque(true);
                    setBackground(bgColor);
                }
                else {
                    setOpaque(false);
                    fgColor = fgMasterColor;
                }
            }
            else {
                if (isSelected) {
                    bgColor = bgSelSlaveColor;
                    fgColor = fgSelSlaveColor;
                    setBackground(bgColor);
                    setOpaque(true);
                }
                else {
                    setOpaque(false);
                    fgColor = fgSlaveColor;
                }
            }
            setHorizontalAlignment(alignment);
            setForeground(fgColor);
            return this;
        }
    }
}
