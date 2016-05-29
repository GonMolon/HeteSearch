package presentation.utils;

import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.swingViewer.util.GraphMetrics;
import org.graphstream.ui.view.Camera;
import org.graphstream.ui.view.util.DefaultMouseManager;

import java.awt.event.MouseEvent;

public class CustomMouseManager extends DefaultMouseManager {
    private int lastX = -1;
    private int lastY = -1;
    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        lastX = -1;
        lastY = -1;
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        int actX = e.getX();
        int actY = e.getY();
        if(lastX != -1 && lastY != -1) {
            Camera camera = view.getCamera();
            Point3 actPos = camera.getViewCenter();
            GraphMetrics metrics = camera.getMetrics();
            double despX = (lastX-actX)/metrics.ratioPx2Gu;
            double despY = (actY-lastY)/metrics.ratioPx2Gu;
            view.getCamera().setViewCenter(actPos.x+despX, actPos.y+despY, 0);
        }
        lastX = actX;
        lastY = actY;
    }
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {}
}
