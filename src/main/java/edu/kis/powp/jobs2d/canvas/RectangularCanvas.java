package edu.kis.powp.jobs2d.canvas;

import edu.kis.powp.jobs2d.command.ImmutableCompoundCommand;
import edu.kis.powp.jobs2d.command.builder.CompoundCommandBuilder;

public abstract class RectangularCanvas implements Canvas{
    private final int height;
    private final int width;
    private final String name;

    public RectangularCanvas(int width, int height, String name) {
        this.width = width;
        this.height = height;
        this.name = name;
    }
    @Override
    public ImmutableCompoundCommand getCanvasCommand() {

        CompoundCommandBuilder builder = new CompoundCommandBuilder();
        builder.setName(name)
                .addSetPosition(-width / 2, -height / 2)
                .addOperateTo(-width / 2, height / 2)
                .addOperateTo(width / 2, height / 2)
                .addOperateTo(width / 2, -height / 2)
                .addOperateTo(-width / 2, -height / 2);
        return builder.build();
    }

    @Override
    public boolean isInsideCanvas(int x, int y) {
        return x >= -width / 2 && x <= width / 2 && y >= -height / 2 && y <= height / 2;
    }
}
