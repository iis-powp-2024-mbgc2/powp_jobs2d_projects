package edu.kis.powp.jobs2d.command.canvas;

import edu.kis.powp.jobs2d.command.CompoundCommand;
import edu.kis.powp.jobs2d.command.builder.CompoundCommandBuilder;

public class CanvasA3 implements Canvas {
    private static final int WIDTH = 297;
    private static final int HEIGHT = 420;

    @Override
    public CompoundCommand getCanvasCommand() {
        CompoundCommandBuilder builder = new CompoundCommandBuilder();
        builder.setName("Canvas A3")
                .addSetPosition(-WIDTH / 2, -HEIGHT / 2)
                .addOperateTo(-WIDTH / 2, HEIGHT / 2)
                .addOperateTo(WIDTH / 2, HEIGHT / 2)
                .addOperateTo(WIDTH / 2, -HEIGHT / 2)
                .addOperateTo(-WIDTH / 2, -HEIGHT / 2);
        return builder.build();
    }

    @Override
    public boolean isInsideCanvas(int x, int y) {
        return x >= -WIDTH / 2 && x <= WIDTH / 2 && y >= -HEIGHT / 2 && y <= HEIGHT / 2;
    }
}
