package edu.kis.powp.jobs2d.transformations;

import java.awt.Point;

public class RotateTransformation extends Transformation {

    private final double angleInRadians;

    public RotateTransformation(double degrees) {
        super("rotated(" + degrees + "°)");
        this.angleInRadians = Math.toRadians(degrees);
    }

    @Override
    public Point transform(Point point) {
        int newX = (int) (point.x * Math.cos(this.angleInRadians) - point.y * Math.sin(this.angleInRadians));
        int newY = (int) (point.x * Math.sin(this.angleInRadians) + point.y * Math.cos(this.angleInRadians));
        return new Point(newX, newY);
    }
}
