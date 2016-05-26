package presentation.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class RoundButton extends JButton {

    private float opacity;
    private static float MAX_ALPHA = 0.8f;
    private static float MIN_ALPHA = 0.2f;
    private Color color;

    public RoundButton(String text, Color color) {
        super(text);
        Dimension size = getPreferredSize();
        size.width = size.height = Math.max(size.width, size.height);
        setPreferredSize(size);
        setContentAreaFilled(false);
        setOpacity(MAX_ALPHA);
        setBackground(color);
        setFocusable(false);
        this.color = color;
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if(enabled) {
            setOpacity(MAX_ALPHA);
            setBackground(color);
        } else {
            setOpacity(MIN_ALPHA);
            setBackground(Color.GRAY);
        }
    }

    private void setOpacity(float opacity) {
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
        //g.drawOval(0, 0, getSize().width-1, getSize().height-1);
    }

    Shape shape;
    public boolean contains(int x, int y) {
        if(shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
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