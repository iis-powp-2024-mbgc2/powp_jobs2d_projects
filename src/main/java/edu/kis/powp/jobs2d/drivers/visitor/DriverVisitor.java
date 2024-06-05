package edu.kis.powp.jobs2d.drivers.visitor;

import edu.kis.powp.jobs2d.drivers.DriversComposite;
import edu.kis.powp.jobs2d.drivers.LoggerDriver;
import edu.kis.powp.jobs2d.drivers.RecordingDriverDecorator;
import edu.kis.powp.jobs2d.drivers.UsageMonitorDriverDecorator;

public interface DriverVisitor {
    void visit(LoggerDriver loggerDriver);
    void visit(DriversComposite driverComposite);
    void visit(RecordingDriverDecorator recordingDriverDecorator);
    void visit(UsageMonitorDriverDecorator usageMonitorDriverDecorator);
}
