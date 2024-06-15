package edu.kis.powp.jobs2d.features.canvas;

import edu.kis.powp.jobs2d.command.DriverCommand;

import java.util.List;

public interface CanvasShape {
    List<DriverCommand> getCommands();
}
