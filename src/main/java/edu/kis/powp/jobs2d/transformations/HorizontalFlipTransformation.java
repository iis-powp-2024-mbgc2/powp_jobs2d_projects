package edu.kis.powp.jobs2d.transformations;

import java.awt.Point;

public class HorizontalFlipTransformation extends Transformation {

    public HorizontalFlipTransformation() {
        super("flipped_horizontally");
    }

    @Override
    public Point transform(Point point) {
        int newX = point.x * -1;
        int newY = point.y;
        return new Point(newX, newY);
    }
}