package edu.kis.powp.jobs2d.drivers.visitor;

import com.sun.tools.javac.util.Pair;
import edu.kis.powp.jobs2d.drivers.DriversComposite;
import edu.kis.powp.jobs2d.drivers.LoggerDriver;
import edu.kis.powp.jobs2d.drivers.RecordingDriverDecorator;
import edu.kis.powp.jobs2d.drivers.UsageMonitorDriverDecorator;
import edu.kis.powp.jobs2d.features.RecordFeature;

public class DriverDetailsVisitor implements DriverVisitor {
    private String details = "";

    public String getDetails() { return this.details; }

    @Override
    public void visit(LoggerDriver loggerDriver) {
        Pair<Integer, Integer> position = loggerDriver.getPosition();
        details = "LoggerDriver details:\n" +
                "Position: (" + position.fst + "," + position.snd + ")";
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
