package edu.kis.powp.jobs2d.drivers.transformators;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.jobs2d.transformations.Transformation;

import java.awt.*;


public class TransformingJob2dDriver implements Job2dDriver {
    private final Transformation transformation;

    public TransformingJob2dDriver(Transformation transformation) {
        this.transformation = transformation;
    }

    @Override
    public void setPosition(int x, int y) {
        Point point = transformation.transform(new Point(x, y));
        DriverFeature.getDriverManager().getCurrentDriver().setPosition(point.x, point.y);
    }

    @Override
    public void operateTo(int x, int y) {
        Point point = transformation.transform(new Point(x, y));
        DriverFeature.getDriverManager().getCurrentDriver().setPosition(point.x, point.y);
    }
}