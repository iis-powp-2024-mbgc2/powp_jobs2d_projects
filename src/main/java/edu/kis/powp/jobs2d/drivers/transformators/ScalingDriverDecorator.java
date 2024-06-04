package edu.kis.powp.jobs2d.drivers.transformators;

import java.awt.*;

public class ScalingDriverDecorator extends Job2dDriverDecorator implements Transformation {
    private final float scalingFactor;

    public ScalingDriverDecorator(float scaleValue) {
        super();
        super.setStrategy(this);
        this.scalingFactor = scaleValue;
    }

    @Override
    public Point transform(int x, int y) {
        int scaledX = Math.round(x * scalingFactor);
        int scaledY = Math.round(y * scalingFactor);

        return new Point(scaledX, scaledY);
    }
}
