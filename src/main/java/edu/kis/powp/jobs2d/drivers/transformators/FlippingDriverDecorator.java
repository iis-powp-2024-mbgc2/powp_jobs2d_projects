package edu.kis.powp.jobs2d.drivers.transformators;

import edu.kis.powp.jobs2d.Job2dDriver;

import java.awt.*;

public class FlippingDriverDecorator extends Job2dDriverDecorator implements Transformation {
    enum FlippingDirection {
        FLIP_VERTICALLY,
        FLIP_HORIZONTALLY,
        FLIP_BOTH_SIDES
    }

    private final FlippingDirection flippingDirection;

    private FlippingDriverDecorator(FlippingDirection flippingDirection) {
        super();
        super.setStrategy(this);
        this.flippingDirection = flippingDirection;
    }

    @Override
    public Point transform(int x, int y) {
        int flippedX = 0, flippedY = 0;

        switch (flippingDirection) {
            case FLIP_BOTH_SIDES: {
                flippedX = -x;
                flippedY = -y;
                break;
            }
            case FLIP_VERTICALLY: {
                flippedY = -y;
                flippedX = x;
                break;
            }
            case FLIP_HORIZONTALLY: {
                flippedX = -x;
                flippedY = y;
                break;
            }
        }

        return new Point(flippedX, flippedY);
    }

    static public FlippingDriverDecorator getFlipHorizontalDecorator() {
        return new FlippingDriverDecorator(FlippingDirection.FLIP_HORIZONTALLY);
    }

    static public FlippingDriverDecorator getFlipVerticalDecorator() {
        return new FlippingDriverDecorator(FlippingDirection.FLIP_VERTICALLY);
    }

    static public FlippingDriverDecorator getFlipBothSidesDecorator() {
        return new FlippingDriverDecorator(FlippingDirection.FLIP_BOTH_SIDES);
    }

}
