package edu.kis.powp.jobs2d.drivers.transformators;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.visitor.IDriverVisitor;
import edu.kis.powp.jobs2d.drivers.visitor.IVisitableDriver;
import edu.kis.powp.jobs2d.transformations.Transformation;

import java.awt.*;


public class TransformingJob2dDriverDecorator implements IVisitableDriver {
    private final Job2dDriver job2dDriver;
    private final Transformation transformation;

    public TransformingJob2dDriverDecorator(Job2dDriver job2dDriver, Transformation transformation) {
        this.job2dDriver = job2dDriver;
        this.transformation = transformation;
    }

    @Override
    public void setPosition(int x, int y) {
        Point point = transformation.transform(new Point(x, y));
        job2dDriver.setPosition(point.x, point.y);
    }

    @Override
    public void operateTo(int x, int y) {
        Point point = transformation.transform(new Point(x, y));
        job2dDriver.operateTo(point.x, point.y);
    }

    @Override
    public void accept(IDriverVisitor visitor) {
        visitor.visit(this);
    }
}
