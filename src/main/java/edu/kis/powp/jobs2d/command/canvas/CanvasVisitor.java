package edu.kis.powp.jobs2d.command.canvas;

import edu.kis.powp.jobs2d.command.*;

import java.util.Iterator;
import java.util.logging.Logger;

public class CanvasVisitor implements CommandVisitor {
    private final Canvas currentCanvas;
    private boolean isOutOfBounds = false;
    public CanvasVisitor(Canvas canvas) {
        currentCanvas = canvas;
    }

    @Override
    public void visit(OperateToCommand operateToCommand) {
        isOutOfBounds = !currentCanvas.isInsideCanvas(operateToCommand.getX(), operateToCommand.getY());
    }

    @Override
    public void visit(SetPositionCommand setPositionCommand) {
        isOutOfBounds = !currentCanvas.isInsideCanvas(setPositionCommand.getX(), setPositionCommand.getY());
    }

    @Override
    public void visit(ICompoundCommand compoundCommand) {
        Iterator<DriverCommand> commands = compoundCommand.iterator();
        boolean isOutOfBoundsFlag = false;
        while(commands.hasNext())
        {
            DriverCommand command = commands.next();
            command.accept(this);
            if(isOutOfBounds)
                isOutOfBoundsFlag = true;
        }
        if(isOutOfBoundsFlag)
            isOutOfBounds = true;
    }

    public boolean getIsOutOfBounds() {
        return this.isOutOfBounds;
    }
}
