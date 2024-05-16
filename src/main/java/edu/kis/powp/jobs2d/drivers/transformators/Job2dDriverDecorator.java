package edu.kis.powp.jobs2d.drivers.transformators;

import edu.kis.powp.jobs2d.Job2dDriver;

import java.awt.*;


public class Job2dDriverDecorator implements Job2dDriver{
    private final Job2dDriver job2dDriver;
    private Transformation transformation;


    public Job2dDriverDecorator(Job2dDriver job2dDriver) {
        this.job2dDriver = job2dDriver;

    }

    public void setStrategy(Transformation transformation) {
        this.transformation = transformation;
    }

    @Override
    public void setPosition(int x, int y) {
        Point point = transformation.transform(x, y);
        job2dDriver.setPosition(point.x, point.y);
    }

    @Override
    public void operateTo(int x, int y) {
        Point point = transformation.transform(x, y);
        job2dDriver.operateTo(point.x, point.y);
    }
}
