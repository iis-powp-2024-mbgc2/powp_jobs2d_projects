package edu.kis.powp.jobs2d.command;

import java.awt.*;

public interface PointCommand extends DriverCommand {

    public int getX();
    public int getY();
    public void setX(int x);
    public void setY(int y);
    public Point getPoint();
}
