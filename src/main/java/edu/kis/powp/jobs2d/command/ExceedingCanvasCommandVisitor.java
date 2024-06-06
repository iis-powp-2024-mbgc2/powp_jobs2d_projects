package edu.kis.powp.jobs2d.command;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class ExceedingCanvasCommandVisitor implements CommandVisitor {

    private JPanel drawArea;
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private boolean isExceedingCanvas = false;
    private ArrayList<Point> commands = new ArrayList<>();

    public ExceedingCanvasCommandVisitor(JPanel drawArea) {
        this.drawArea = drawArea;
    }

    private Dimension getDrawAreaSize() {
        return drawArea.getSize();
    }

    private Point commandCoordinatesToCanvasCoordinates(int x, int y) {
        Dimension size = getDrawAreaSize();
        int width = size.width;
        int height = size.height;
        x += width / 2;
        y = height / 2 - y;
        return new Point(x, y);
    }
    private void checkForExceedingCanvas(int x, int y) {

        Dimension size = getDrawAreaSize();
        Point p = commandCoordinatesToCanvasCoordinates(x, y);
        x = p.x;
        y = p.y;
        if (x >= size.width || x < 0 || y >= size.height || y < 0) {
            isExceedingCanvas = true;
            commands.add(new Point(x, y));
        }
    }
    @Override
    public void visit(OperateToCommand operateToCommand) {
        checkForExceedingCanvas(operateToCommand.getX(), operateToCommand.getY());
        }


    @Override
    public void visit(SetPositionCommand setPositionCommand) {
        checkForExceedingCanvas(setPositionCommand.getX(), setPositionCommand.getY());
        }

    @Override
    public void visit(ICompoundCommand compoundCommand) {
        Iterator<DriverCommand> it = compoundCommand.iterator();
        while (it.hasNext()) {
            it.next().accept(this);
        }
    }

    public boolean isExceedingCanvas() {
        return isExceedingCanvas;
    }
    public List<Point> getPointsExceeding()
    {
        return commands;
    }
}
