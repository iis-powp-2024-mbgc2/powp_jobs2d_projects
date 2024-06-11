package edu.kis.powp.jobs2d.canvas;


import edu.kis.powp.jobs2d.command.ImmutableCompoundCommand;

public interface Canvas {
    ImmutableCompoundCommand getCanvasCommand();
    boolean isInsideCanvas(int x, int y);
}
