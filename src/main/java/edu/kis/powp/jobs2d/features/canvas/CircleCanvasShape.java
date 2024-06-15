package edu.kis.powp.jobs2d.features.canvas;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;

import java.util.ArrayList;
import java.util.List;

public class CircleCanvasShape implements CanvasShape {
    private final int x, y, radius;

    public CircleCanvasShape(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    @Override
    public List<DriverCommand> getCommands() {
        List<DriverCommand> commands = new ArrayList<>();
        double increment = Math.PI / 180; // Increment angle in radians for drawing the circle
        double angle = 0.0;

        int startX = x + radius;
        int startY = y;

        commands.add(new SetPositionCommand(startX, startY));

        while (angle < 2 * Math.PI) {
            angle += increment;
            int newX = (int) (x + radius * Math.cos(angle));
            int newY = (int) (y + radius * Math.sin(angle));
            commands.add(new OperateToCommand(newX, newY));
        }

        commands.add(new OperateToCommand(startX, startY)); // Close the circle

        return commands;
    }
}
