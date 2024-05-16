package edu.kis.powp.jobs2d.command;

import edu.kis.legacy.drawer.panel.DefaultDrawerFrame;

import java.util.Iterator;

public class ExceedingCanvasCommandVisitor implements CommandVisitor{

    private int width;
    private int height;

    public ExceedingCanvasCommandVisitor() {

        // TODO canvas abstraction
        height = DefaultDrawerFrame.getDefaultDrawerFrame().getDrawArea().getSize().height;
        width = DefaultDrawerFrame.getDefaultDrawerFrame().getDrawArea().getSize().width;
    }

    @Override
    public void visit(OperateToCommand operateToCommand) {
        int x = operateToCommand.getX();
        int y = operateToCommand.getY();
        if (x > width || x < 0 || y > height || y < 0)
            System.out.println("exceeding");
    }

    @Override
    public void visit(SetPositionCommand setPositionCommand) {
        int x = setPositionCommand.getX();
        int y = setPositionCommand.getY();
        if (x > width || x < 0 || y > height || y < 0)
            System.out.println("exceeding");
    }

    @Override
    public void visit(ICompoundCommand compoundCommand) {
        Iterator<DriverCommand> it = compoundCommand.iterator();
        while (it.hasNext())
        {
            it.next().accept(this);
        }
    }

}
