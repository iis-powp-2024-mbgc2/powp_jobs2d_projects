package edu.kis.powp.jobs2d.transformations;

import java.awt.Point;

public class ScaleTransformation extends Transformation {

    private final double scale;

    public ScaleTransformation(double scale) {
        super("scaled("+scale+"x)");
        this.scale = scale;
    }

    @Override
    public Point transform(Point point) {
        int newX = (int) (this.scale * point.x);
        int newY = (int) (this.scale * point.y);
        return new Point(newX, newY);
    }
}