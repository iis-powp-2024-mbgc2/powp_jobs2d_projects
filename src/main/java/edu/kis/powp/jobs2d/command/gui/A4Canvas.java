package edu.kis.powp.jobs2d.command.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class A4Canvas extends JPanel implements Canvas {
    private static final int WIDTH_MM = 210; // A4 width in mm
    private static final int HEIGHT_MM = 297; // A4 height in mm

    private final int dpi;

    public A4Canvas(int dpi) {
        this.dpi = dpi;
        setPreferredSize(getSize());

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateSize();
            }
        });
    }

    @Override
    public Dimension getSize() {
        int width = (int) (WIDTH_MM * dpi / 25.4); // Convert mm to pixels
        int height = (int) (HEIGHT_MM * dpi / 25.4); // Convert mm to pixels
        return new Dimension(width, height);
    }

    @Override
    public Dimension getPreferredSize() {
        return getSize();
    }

    private void updateSize() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        double aspectRatio = (double) WIDTH_MM / HEIGHT_MM;
        int newWidth = panelWidth;
        int newHeight = (int) (panelWidth / aspectRatio);

        if (newHeight > panelHeight) {
            newHeight = panelHeight;
            newWidth = (int) (panelHeight * aspectRatio);
        }

        setPreferredSize(new Dimension(newWidth, newHeight));
        revalidate();
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
