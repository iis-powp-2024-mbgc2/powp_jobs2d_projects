package edu.kis.powp.jobs2d.drivers.visitor;

import edu.kis.powp.jobs2d.commons.Pair;
import edu.kis.powp.jobs2d.drivers.*;
import edu.kis.powp.jobs2d.features.RecordFeature;

import java.util.Iterator;

public class DriverDetailsVisitor implements DriverVisitor {
    private StringBuilder stringBuilder = new StringBuilder();
    public void clear() { stringBuilder = new StringBuilder(); }
    public String getDetails(IDriver driver) {
        clear();
        driver.accept(this);
        return stringBuilder.toString();
    }

    @Override
    public void visit(LoggerDriver loggerDriver) {
        Pair<Integer, Integer> position = loggerDriver.getPosition();
        String details = "\nLoggerDriver details:" +
                "\n Position: (" + position.getFirst() + "," + position.getSecond() + ")";
        stringBuilder.append(details);
    }

    @Override
    public void visit(DriversComposite driverComposite) {
        int registeredDrivers = driverComposite.driversCount();
        String details = "\nDriversComposite details:" +
                "\n Registered drivers count: " + registeredDrivers;
        stringBuilder.append(details);

        Iterator<IDriver> driverIterator = driverComposite.getIterator();
        while(driverIterator.hasNext()) {
            IDriver nextDriver = driverIterator.next();
            nextDriver.accept(this);
        }
    }

    @Override
    public void visit(RecordingDriverDecorator recordingDriverDecorator) {
        String isRecordingLabel = RecordFeature.isRecording() ? "true" : "false";
        String details = "\nRecordingDriver details:" +
                "\n Recording: " + isRecordingLabel +
                "\n Serviced drivers:";
        stringBuilder.append(details);

        IDriver decoratedDriver = recordingDriverDecorator.getDriver();
        decoratedDriver.accept(this);
    }

    @Override
    public void visit(UsageMonitorDriverDecorator usageMonitorDriverDecorator) {
        String details = "\nUsageMonitorDriver details:" +
                "\n Current headDistance: " + usageMonitorDriverDecorator.getHeadDistance() +
                "\n Current opDistance: " + usageMonitorDriverDecorator.getOpDistance() +
                "\n Serviced driver:";
        stringBuilder.append(details);

        IDriver decoratedDriver = usageMonitorDriverDecorator.getDriver();
        decoratedDriver.accept(this);
    }
}
