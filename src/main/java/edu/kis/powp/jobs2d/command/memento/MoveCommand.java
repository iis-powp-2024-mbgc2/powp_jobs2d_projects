package edu.kis.powp.jobs2d.command.memento;

import edu.kis.powp.jobs2d.command.DriverCommand;

import java.awt.*;

public class MoveCommand implements EditCommand {
    private final DriverCommand command;
    private final Point position;

    public MoveCommand(DriverCommand command, Point position) {
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
