package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.jobs2d.features.RecordFeature;
import edu.kis.powp.jobs2d.command.OperateToCommand;

public class RecordingDriverDecorator implements Job2dDriver{
    private final Job2dDriver job2dDriver;
    public RecordingDriverDecorator(DriverManager driverManager) {
        job2dDriver = driverManager.getCurrentDriverAndFeaturesComposite(this);
    }
    @Override
    public void setPosition(int x, int y) {
        job2dDriver.setPosition(x,y);
        RecordFeature.setCommand(new SetPositionCommand(x,y));
        DriverFeature.getDriverManager().getCurrentDriver().setPosition(x, y);
    }

    @Override
    public void operateTo(int x, int y) {
        job2dDriver.operateTo(x,y);
        RecordFeature.setCommand(new OperateToCommand(x,y));
        DriverFeature.getDriverManager().getCurrentDriver().setPosition(x, y);
    }

    @Override
    public String toString() {
        return super.toString();
    }

}