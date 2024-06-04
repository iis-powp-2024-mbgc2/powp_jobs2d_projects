package edu.kis.powp.jobs2d.command;

import javax.swing.*;
import java.awt.Dimension;
import java.util.Iterator;
import java.util.logging.Logger;

public class ExceedingCanvasCommandVisitor implements CommandVisitor {

    private JPanel drawArea;
    private static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public ExceedingCanvasCommandVisitor(JPanel drawArea) {
        this.drawArea = drawArea;
    }

    private Dimension getDrawAreaSize() {
        return drawArea.getSize();
    }

    @Override
    public void visit(OperateToCommand operateToCommand) {
        Dimension size = getDrawAreaSize();
        int width = size.width;
        int height = size.height;

        int x = operateToCommand.getX() + width / 2;
        int y = height / 2 - operateToCommand.getY();

        if (x >= width || x < 0 || y >= height || y < 0) {
            logger.warning("Exceeding canvas: (" + x + ", " + y + ")");
        }
    }

    @Override
    public void visit(SetPositionCommand setPositionCommand) {
        Dimension size = getDrawAreaSize();
        int width = size.width;
        int height = size.height;

        int x = setPositionCommand.getX() + width / 2;
        int y = height / 2 - setPositionCommand.getY();


        if (x >= width || x < 0 || y >= height || y < 0) {
            logger.warning("Exceeding canvas: (" + x + ", " + y + ")");
        }
    }

    @Override
    public void visit(ICompoundCommand compoundCommand) {
        Iterator<DriverCommand> it = compoundCommand.iterator();
        while (it.hasNext()) {
            it.next().accept(this);
        }
    }
}
