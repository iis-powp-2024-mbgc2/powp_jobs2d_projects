package edu.kis.powp.jobs2d.drivers.visitor;

import edu.kis.powp.jobs2d.commons.Pair;
import edu.kis.powp.jobs2d.drivers.*;
import edu.kis.powp.jobs2d.features.RecordFeature;

public class DriverDetailsVisitor implements DriverVisitor {
    private String details = "";

    public String getDetails() { return this.details; }

    public String getDetails(IDriver driver) {
        driver.accept(this);
        return details;
    }

    @Override
    public void visit(LoggerDriver loggerDriver) {
        Pair<Integer, Integer> position = loggerDriver.getPosition();
        details = "LoggerDriver details:\n" +
                "Position: (" + position.getFirst() + "," + position.getSecond() + ")";
    }

    @Override
    public void visit(DriversComposite driverComposite) {
        int registeredDrivers = driverComposite.driversCount();
        details = "DriversComposite details:\n" +
                "Registered drivers count: " + registeredDrivers;
    }

    @Override
    public void visit(RecordingDriverDecorator recordingDriverDecorator) {
        String prefix = "RecordingDriver details:\n" + "Recording: ";
        details = RecordFeature.isRecording() ? prefix + "true" : prefix + "false";
    }

    @Override
    public void visit(UsageMonitorDriverDecorator usageMonitorDriverDecorator) {
        details = "UsageMonitorDriver details:" +
                "\nCurrent headDistance: " + usageMonitorDriverDecorator.getHeadDistance() +
                "\nCurrent opDistance: " + usageMonitorDriverDecorator.getOpDistance();
    }
}
