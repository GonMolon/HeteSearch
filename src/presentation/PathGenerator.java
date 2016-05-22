package presentation;

import domain.NodeType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class PathGenerator extends JPanel implements ActionListener{

    private PresentationController presentationController;
    protected ArrayList<Integer> actualRS = new ArrayList<Integer>();
    private NodeType from = null;
    private RoundButton[] buttons = new RoundButton[5];
    private JButton reset;

    private static float MAX_ALPHA = 0.8f;
    private static float MIN_ALPHA = 0.2f;
    private static String LABEL = "L";
    private static String AUTHOR = "A";
    private static String PAPER = "P";
    private static String CONFERENCE = "C";
    private static String TERM = "T";

    protected PathGenerator(PresentationController presentationController) {
        this.presentationController = presentationController;

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, 0));
        buttonsPanel.add(createButton(0, LABEL));
        JPanel middleButtons = new JPanel();
        middleButtons.setLayout(new BoxLayout(middleButtons, 1));
        middleButtons.add(createButton(1, AUTHOR));
        middleButtons.add(createButton(2, PAPER));
        middleButtons.add(createButton(3, CONFERENCE));
        buttonsPanel.add(middleButtons);
        buttonsPanel.add(createButton(4, TERM));

        add(buttonsPanel);
        reset = new JButton("Reset");
        add(reset);
        reset.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        from = null;
                        setEnabledButtons(true);
                        actualRS = new ArrayList<Integer>();
                    }
                }
        );
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        NodeType to = getNodeType(event.getActionCommand());
        PresentationController.PathInfo info = presentationController.getPathInfo(from, to);
        if(info.availableRelations.size() == 1) {
            actualRS.add(info.availableRelations.get(0));
            System.out.println(actualRS.get(actualRS.size()-1));
        } else if(info.availableRelations.size() > 1) {
            this.setEnabled(false);
            ArrayList<String> relationsName = new ArrayList<String>();
            for(int i = 0; i < info.availableRelations.size(); ++i) {
                relationsName.add(presentationController.getRelationName(info.availableRelations.get(i)));
            }
            RelationSelection relationSelection = new RelationSelection(info.availableRelations, relationsName);
            actualRS.add(relationSelection.getIdChosen());
            System.out.println(actualRS.get(actualRS.size()-1));
        }
        setEnabledButtons(false);
        for(int i = 0; i < info.availableNodeTypes.size(); ++i) {
            int id = getButtonID(info.availableNodeTypes.get(i));
            buttons[id].setOpacity(MAX_ALPHA);
            //buttons[id].setBackground(getButtonColor(id));
            buttons[id].setEnabled(true);
        }
        from = to;
    }

    private RoundButton createButton(int id, String name) {
        RoundButton button = new RoundButton(name);
        button.addActionListener(this);
        button.setActionCommand(name);
        button.setPreferredSize(new Dimension(100, 100));
        button.setBackground(getButtonColor(id));
        button.setOpacity(MAX_ALPHA);
        button.setFont(new Font("Arial", Font.PLAIN, 40));
        buttons[id] = button;
        return button;
    }

    protected void setEnabledButtons(boolean enable) {
        actualRS = new ArrayList<Integer>();
        for(int id = 0; id < buttons.length; ++id) {
            if(!enable) {
                buttons[id].setOpacity(MIN_ALPHA);
                //buttons[id].setBackground(Color.GRAY);
            } else {
                buttons[id].setOpacity(MAX_ALPHA);
                //buttons[id].setBackground(getButtonColor(id));
            }
            buttons[id].setEnabled(enable);
        }
        from = null;
    }


    private Color getButtonColor(int id) {
        if(id == 0) {
            return Color.YELLOW;
        } else if(id == 1) {
            return Color.CYAN;
        } else if(id == 2) {
            return Color.RED;
        } else if(id == 3) {
            return Color.GREEN;
        } else {
            return Color.ORANGE;
        }
    }

    private int getButtonID(NodeType type) {
        if(type == NodeType.LABEL) {
            return 0;
        } else if(type == NodeType.AUTHOR) {
            return 1;
        } else if(type == NodeType.PAPER) {
            return 2;
        } else if(type == NodeType.CONF) {
            return 3;
        } else {
            return 4;
        }
    }

    private NodeType getNodeType(String type) {
        if(type.equals(LABEL)) {
            return NodeType.LABEL;
        } else if(type.equals(AUTHOR)) {
            return NodeType.AUTHOR;
        } else if(type.equals(PAPER)) {
            return NodeType.PAPER;
        } else if(type.equals(CONFERENCE)) {
            return NodeType.CONF;
        } else {
            return NodeType.TERM;
        }
    }

    private class RoundButton extends JButton {

        private float opacity;

        public RoundButton(String label) {
            super(label);
            Dimension size = getPreferredSize();
            size.width = size.height = Math.max(size.width, size.height);
            setPreferredSize(size);
            setContentAreaFilled(false);
            opacity = 1f;
        }

        public void setOpacity(float opacity) {
            this.opacity = opacity;
            repaint();
        }

        protected void paintComponent(Graphics g) {
            if(getModel().isArmed()) {
                g.setColor(Color.lightGray);
            } else {
                g.setColor(getBackground());
            }
            g.fillOval(0, 0, getSize().width-1, getSize().height-1);
            super.paintComponent(g);
        }

        protected void paintBorder(Graphics g) {
            g.setColor(getForeground());
            g.drawOval(0, 0, getSize().width-1, getSize().height-1);
        }

        Shape shape;
        public boolean contains(int x, int y) {
            if(shape == null || !shape.getBounds().equals(getBounds())) {
                shape = new Ellipse2D.Float(0, 0,
                        getWidth(), getHeight());
            }
            return shape.contains(x, y);
        }

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            super.paint(g2);
            g2.dispose();
        }
    }
}
