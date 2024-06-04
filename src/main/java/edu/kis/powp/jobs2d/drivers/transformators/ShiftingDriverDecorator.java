package edu.kis.powp.jobs2d.drivers.transformators;

import edu.kis.powp.jobs2d.Job2dDriver;

import java.awt.*;

public class ShiftingDriverDecorator extends Job2dDriverDecorator implements Transformation {
    private final int shiftInXDirection, shiftInYDirection;

    public ShiftingDriverDecorator(int shiftInXDirection, int shiftInYDirection) {
        super();
        super.setStrategy(this);
        this.shiftInXDirection = shiftInXDirection;
        this.shiftInYDirection = shiftInYDirection;
    }

    @Override
    public Point transform(int x, int y) {
        int shiftedX = x + shiftInXDirection;
        int shiftedY = y - shiftInYDirection;

        return new Point(shiftedX, shiftedY);
    }
}
