package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.command.gui.Canvas;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExceedingCanvasCommandVisitor implements CommandVisitor {

    private Canvas canvas;
    private boolean isExceedingCanvas = false;
    private List<Point> commands = new ArrayList<>();

    public ExceedingCanvasCommandVisitor(Canvas canvas) {
        this.canvas = canvas;
    }


    @Override
    public void visit(OperateToCommand operateToCommand) {
        canvas.isPointWithinBounds(operateToCommand.getX(), operateToCommand.getY());
    }

    @Override
    public void visit(SetPositionCommand setPositionCommand) {
        canvas.isPointWithinBounds(setPositionCommand.getX(), setPositionCommand.getY());
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

    public List<Point> getPointsExceeding() {
        return commands;
    }
}
