package edu.kis.powp.jobs2d.canvas;


import edu.kis.powp.jobs2d.command.CompoundCommand;

public interface Canvas {
    CompoundCommand getCanvasCommand();
    boolean isInsideCanvas(int x, int y);
}
