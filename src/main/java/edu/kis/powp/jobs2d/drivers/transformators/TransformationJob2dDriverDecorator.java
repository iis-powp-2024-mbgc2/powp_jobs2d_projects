package edu.kis.powp.jobs2d.drivers.transformators;

import edu.kis.powp.jobs2d.extended_driver_options.DriverOption;

import java.awt.*;


public class TransformationJob2dDriverDecorator extends DriverOption {
    private Transformation transformation;


    public TransformationJob2dDriverDecorator() {
    }

    public void setStrategy(Transformation transformation) {
        this.transformation = transformation;
    }

    @Override
    public void setPosition(int x, int y) {
        Point point = transformation.transform(x, y);
        driver.setPosition(point.x, point.y);
    }

    @Override
    public void operateTo(int x, int y) {
        Point point = transformation.transform(x, y);
        driver.operateTo(point.x, point.y);
    }
}
