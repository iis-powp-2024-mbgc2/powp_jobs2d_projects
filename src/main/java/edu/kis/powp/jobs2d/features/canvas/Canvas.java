package edu.kis.powp.jobs2d.features.canvas;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;

import java.util.List;

public class Canvas {
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
        getCommands().forEach(command -> command.execute(driver));
    }

    public List<DriverCommand> getCommands() {
        return shape.getCommands();
    }
}