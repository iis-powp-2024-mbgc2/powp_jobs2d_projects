package edu.kis.powp.jobs2d.command.gui;

import java.awt.Dimension;
import java.awt.Point;

public interface Canvas {
    boolean isPointWithinBounds(int x, int y);
    Point commandCoordinatesToCanvasCoordinates(int x, int y);
}
