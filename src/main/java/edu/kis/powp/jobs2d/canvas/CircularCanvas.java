package edu.kis.powp.jobs2d.canvas;

import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.ImmutableCompoundCommand;
import edu.kis.powp.jobs2d.command.builder.CompoundCommandBuilder;

public abstract class CircularCanvas implements Canvas{

    private final String name;
    private final int radius;

    public CircularCanvas(int radius, String name)
    {
        this.radius = radius;
        this.name = name;
    }

    public int getRadius() {
        return this.radius;
    }

    @Override
    public ImmutableCompoundCommand getCanvasCommand()
    {
        CompoundCommandBuilder builder = new CompoundCommandBuilder();
        builder.addSetPosition(-radius, 0);
        for(int i=-radius; i<=radius; i++)
            builder.addOperateTo(i, (int) (Math.sqrt(radius*radius-i*i)));
        builder.addSetPosition(-radius, 0);
        for(int i=-radius; i<=radius; i++)
            builder.addOperateTo(i, (int) (-Math.sqrt(radius*radius-i*i)));

        return builder.build();
    }
    @Override
    public boolean isInsideCanvas(int x, int y)
    {
        return !(Math.sqrt(x * x + y * y) > radius);
    }
}
