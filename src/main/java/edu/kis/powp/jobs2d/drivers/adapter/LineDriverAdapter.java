package edu.kis.powp.jobs2d.drivers.adapter;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.ILine;
import edu.kis.powp.jobs2d.drivers.IDriver;
import edu.kis.powp.jobs2d.drivers.visitor.DriverVisitor;

/**
 * Line adapter - IDriver with DrawPanelController object.
 */
public class LineDriverAdapter implements IDriver {
    private ILine line;
    private int startX = 0, startY = 0;
    private String name;

    private DrawPanelController drawController;

    public LineDriverAdapter(DrawPanelController drawController, ILine line, String name) {
        super();
        this.drawController = drawController;
        this.line = line;
        this.name = name;
    }

    @Override
    public void setPosition(int x, int y) {
        this.startX = x;
        this.startY = y;
    }

    @Override
    public void operateTo(int x, int y) {
        line.setStartCoordinates(this.startX, this.startY);
        this.setPosition(x, y);
        line.setEndCoordinates(x, y);

        drawController.drawLine(line);
    }

    @Override
    public void accept(DriverVisitor visitor) { }

    @Override
    public String toString() {
        return "2d device simulator - " + name;
    }
}
