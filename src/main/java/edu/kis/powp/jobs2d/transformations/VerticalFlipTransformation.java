package edu.kis.powp.jobs2d.transformations;

import java.awt.Point;

public class VerticalFlipTransformation extends Transformation {

    public VerticalFlipTransformation() {
        super("flipped_vertically");
    }

    @Override
    public Point transform(Point point) {
        int newX = point.x;
        int newY = point.y * -1;
        return new Point(newX, newY);
    }
}
