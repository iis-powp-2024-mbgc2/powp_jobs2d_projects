package edu.kis.powp.jobs2d.features.canvas;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;

import java.util.Arrays;
import java.util.List;

public class RectangleCanvasShape implements CanvasShape {
    private final int x, y, width, height;

    public RectangleCanvasShape(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public List<DriverCommand> getCommands() {
        return Arrays.asList(
                new SetPositionCommand(x, y),
                new OperateToCommand(x + width, y),
                new OperateToCommand(x + width, y + height),
                new OperateToCommand(x, y + height),
                new OperateToCommand(x, y)
        );
    }
}
