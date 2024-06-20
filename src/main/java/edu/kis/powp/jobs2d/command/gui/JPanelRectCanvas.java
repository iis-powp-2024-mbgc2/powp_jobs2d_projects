package edu.kis.powp.jobs2d.command.gui;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Point;

public class JPanelRectCanvas extends JPanel implements Canvas {

    public JPanelRectCanvas() {
        super();
    }

    @Override
    public boolean isPointWithinBounds(int x, int y) {
        Dimension size = getSize();
        Point point = commandCoordinatesToCanvasCoordinates(x, y);
        return point.x >= 0 && point.x < size.width && point.y >= 0 && point.y < size.height;
    }

    @Override
    public Point commandCoordinatesToCanvasCoordinates(int x, int y) {
        Dimension size = getSize();
        int width = size.width;
        int height = size.height;
        x += width / 2;
        y = height / 2 - y;
        return new Point(x, y);
    }
}
