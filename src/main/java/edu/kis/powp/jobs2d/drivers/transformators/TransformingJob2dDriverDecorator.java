package edu.kis.powp.jobs2d.drivers.transformators;

import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.jobs2d.drivers.visitor.IDriverVisitor;
import edu.kis.powp.jobs2d.drivers.visitor.IVisitableDriver;
import edu.kis.powp.jobs2d.transformations.Transformation;

import java.awt.*;


public class TransformingJob2dDriverDecorator implements IVisitableDriver {
    private final IVisitableDriver job2dDriver;
    private final Transformation transformation;

    public TransformingJob2dDriverDecorator(Transformation transformation) {
        this.job2dDriver = DriverFeature.getDriverManager().getCurrentDriverAndFeaturesComposite(this);
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

    public IVisitableDriver getDriver() {
        return job2dDriver;
    }

    @Override
    public void accept(IDriverVisitor visitor) {
        visitor.visit(this);
    }
}
