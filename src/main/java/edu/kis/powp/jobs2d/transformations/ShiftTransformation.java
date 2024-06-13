package edu.kis.powp.jobs2d.transformations;

import java.awt.*;

public class ShiftTransformation extends Transformation {
    private final int shiftInXDirection, shiftInYDirection;

    public ShiftTransformation(int shiftInXDirection, int shiftInYDirection) {
        super("shifted(" + shiftInXDirection + "," + shiftInYDirection + ")");
        this.shiftInXDirection = shiftInXDirection;
        this.shiftInYDirection = shiftInYDirection;
    }

    @Override
    public Point transform(Point point) {
        int shiftedX = point.x + shiftInXDirection;
        int shiftedY = point.y - shiftInYDirection;
        return new Point(shiftedX, shiftedY);
    }
}
