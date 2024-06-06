package edu.kis.powp.jobs2d.drivers.transformators;

import java.awt.*;

public class RotatingDriverDecoratorTransformation extends TransformationJob2dDriverDecorator implements Transformation {
    enum RotationOption {
        ROTATE_90_DEG_CLOCKWISE,
        ROTATE_90_DEG_COUNTERCLOCKWISE,
        ROTATE_180_DEG
    }

    private final RotationOption rotationOption;

    private RotatingDriverDecoratorTransformation(RotationOption rotationOption) {
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

    static public RotatingDriverDecoratorTransformation getRotating90DegClockwiseDecorator() {
        return new RotatingDriverDecoratorTransformation(RotationOption.ROTATE_90_DEG_CLOCKWISE);
    }

    static public RotatingDriverDecoratorTransformation getRotating90DegCounterclockwiseDecorator() {
        return new RotatingDriverDecoratorTransformation(RotationOption.ROTATE_90_DEG_COUNTERCLOCKWISE);
    }

    static public RotatingDriverDecoratorTransformation getRotating180DegDecorator() {
        return new RotatingDriverDecoratorTransformation(RotationOption.ROTATE_180_DEG);
    }
}
