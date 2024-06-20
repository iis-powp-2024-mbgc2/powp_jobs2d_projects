package edu.kis.powp.jobs2d.drivers.adapter;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.ILine;
import edu.kis.powp.jobs2d.drivers.visitor.IDriverVisitor;
import edu.kis.powp.jobs2d.drivers.visitor.IVisitableDriver;

import java.util.Objects;

/**
 * Line adapter - Line2dDriver with DrawPanelController object.
 */
public class LineDriverAdapter implements Line2dDriver {
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
    public String toString() {
        return "2d device simulator - " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineDriverAdapter that = (LineDriverAdapter) o;
        return line.getClass() == that.line.getClass() && Objects.equals(name, that.name);
    }

    @Override
    public int getStartX() {
        return startX;
    }

    @Override
    public int getStartY() {
        return startY;
    }

    @Override
    public void setStartX(int startX) {
        this.startX = startX;
    }

    @Override
    public void setStartY(int startY) {
        this.startY = startY;
    }

    @Override
    public void accept(IDriverVisitor visitor) {
        visitor.visit(this);
    }
}
