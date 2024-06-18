package edu.kis.powp.jobs2d.command.memento;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.PointCommand;

import java.awt.*;

public class MoveCommand implements EditCommand {
    private final PointCommand command;
    private final Point position;

    public MoveCommand(PointCommand command, Point position) {
        this.command = command;
        this.position = position;
    }

    @Override
    public void execute() {
        command.setX(position.x);
        command.setY(position.y);
    }

    public String toString() {
        return "Move (" + position.x + ", " + position.y + ")";
    }
}
