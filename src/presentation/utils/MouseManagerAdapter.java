package presentation.utils;

import org.graphstream.ui.graphicGraph.GraphicGraph;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.util.MouseManager;

import java.awt.event.MouseEvent;

public class MouseManagerAdapter implements MouseManager{

    @Override
    public void init(GraphicGraph graph, View view) {}

    @Override
    public void release() {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {}
}
