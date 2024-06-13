package edu.kis.powp.jobs2d.drivers.visitor;

import edu.kis.powp.jobs2d.drivers.*;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.drivers.transformators.TransformingJob2dDriverDecorator;

public interface IDriverVisitor {

    void visit(DriversComposite driversComposite);

    void visit(LoggerDriver loggerDriver);

    void visit(LineDriverAdapter lineDriverAdapter);

    void visit(RealTimeDecoratorDriver realTimeDecoratorDriver);

    void visit(UsageMonitorDriverDecorator usageMonitorDriverDecorator);

    void visit(RecordingDriverDecorator recordingDriverDecorator);

    void visit(TransformingJob2dDriverDecorator transformingJob2dDriverDecorator);

}
