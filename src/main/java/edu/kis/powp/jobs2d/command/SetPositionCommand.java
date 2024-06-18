package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;

import java.awt.*;

/**
 * Implementation of Job2dDriverCommand for setPosition command functionality.
 */
public class SetPositionCommand implements PointCommand {

    private int posX, posY;

    public SetPositionCommand(int posX, int posY) {
        super();
        this.posX = posX;
        this.posY = posY;
    }

    @Override
    public void execute(Job2dDriver driver) {
        driver.setPosition(posX, posY);
    }

    @Override
    public void accept(CommandVisitor commandVisitor) {
        commandVisitor.visit(this);
    }

    @Override
    public int getX() {
        return posX;
    }
    @Override
    public int getY() {
        return posY;
    }
    @Override
    public void setX(int posX) {
        this.posX = posX;
    }
    @Override
    public void setY(int posY) {
        this.posY = posY;
    }
    @Override
    public Point getPoint() {
        return new Point(posX, posY);
    }
}
