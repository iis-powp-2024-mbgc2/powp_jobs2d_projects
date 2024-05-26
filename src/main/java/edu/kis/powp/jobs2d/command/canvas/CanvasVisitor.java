package edu.kis.powp.jobs2d.command.canvas;

import edu.kis.powp.jobs2d.command.*;

import java.util.Iterator;
import java.util.logging.Logger;

public class CanvasVisitor implements CommandVisitor {
    private Canvas currentCanvas;
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public CanvasVisitor(Canvas canvas) {
        currentCanvas = canvas;
    }

    @Override
    public void visit(OperateToCommand operateToCommand) {
        if(!currentCanvas.isInsideCanvas(operateToCommand.getX(), operateToCommand.getY()))
        {
            logger.info("Point (" + operateToCommand.getX() + "," + operateToCommand.getY() + ") is exceeding canvas.");
        }
    }

    @Override
    public void visit(SetPositionCommand setPositionCommand) {
        if(!currentCanvas.isInsideCanvas(setPositionCommand.getX(), setPositionCommand.getY()))
        {
            logger.info("Point (" + setPositionCommand.getX() + "," + setPositionCommand.getY() + ") is exceeding canvas.");
        }
    }

    @Override
    public void visit(ICompoundCommand compoundCommand) {
        Iterator<DriverCommand> commands = compoundCommand.iterator();
        while(commands.hasNext())
        {
            DriverCommand command = commands.next();
            command.accept(this);
        }
    }
}
