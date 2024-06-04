package edu.kis.powp.jobs2d.drivers.transformators;

import java.awt.*;

public class RotatingDriverDecorator extends Job2dDriverDecorator implements Transformation {
    enum RotationOption {
        ROTATE_90_DEG_CLOCKWISE,
        ROTATE_90_DEG_COUNTERCLOCKWISE,
        ROTATE_180_DEG
    }

    private final RotationOption rotationOption;

    private RotatingDriverDecorator(RotationOption rotationOption) {
        super();
        super.setStrategy(this);
        this.rotationOption = rotationOption;
    }

    @Override
    public Point transform(int x, int y) {
        int rotatedX = 0, rotatedY = 0;

        switch (rotationOption) {
            case ROTATE_90_DEG_CLOCKWISE: {
                rotatedY = x;
                rotatedX = -y;
                break;
            }
            case ROTATE_90_DEG_COUNTERCLOCKWISE: {
                rotatedY = -x;
                rotatedX = y;
                break;
            }
            case ROTATE_180_DEG: {
                rotatedY = -y;
                rotatedX = -x;
                break;
            }
        }

        return new Point(rotatedX, rotatedY);
    }

    static public RotatingDriverDecorator getRotating90DegClockwiseDecorator() {
        return new RotatingDriverDecorator(RotationOption.ROTATE_90_DEG_CLOCKWISE);
    }

    static public RotatingDriverDecorator getRotating90DegCounterclockwiseDecorator() {
        return new RotatingDriverDecorator(RotationOption.ROTATE_90_DEG_COUNTERCLOCKWISE);
    }

    static public RotatingDriverDecorator getRotating180DegDecorator() {
        return new RotatingDriverDecorator(RotationOption.ROTATE_180_DEG);
    }
}
