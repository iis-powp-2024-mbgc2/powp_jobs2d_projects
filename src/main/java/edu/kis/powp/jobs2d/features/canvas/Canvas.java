package edu.kis.powp.jobs2d.features.canvas;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;

import java.util.Arrays;
import java.util.List;

public abstract class Canvas {
    private final String name;
    private final CanvasShape shape;

    public Canvas(String name, CanvasShape shape) {
        this.name = name;
        this.shape = shape;
    }

    public String getName() {
        return name;
    }

    public CanvasShape getShape() {
        return shape;
    }

    public void draw(Job2dDriver driver) {
        int x = shape.getX();
        int y = shape.getY();
        int width = shape.getWidth();
        int height = shape.getHeight();

        driver.setPosition(x, y);
        driver.operateTo(x + width, y);
        driver.operateTo(x + width, y + height);
        driver.operateTo(x, y + height);
        driver.operateTo(x, y);
    }

    public List<DriverCommand> getCommands() {
        int x = shape.getX();
        int y = shape.getY();
        int width = shape.getWidth();
        int height = shape.getHeight();

        return Arrays.asList(
                new SetPositionCommand(x, y),
                new OperateToCommand(x + width, y),
                new OperateToCommand(x + width, y + height),
                new OperateToCommand(x, y + height),
                new OperateToCommand(x, y)
        );
    }
}
