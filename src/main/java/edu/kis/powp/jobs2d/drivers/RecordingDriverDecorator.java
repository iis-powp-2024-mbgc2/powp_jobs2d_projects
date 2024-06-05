package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.drivers.visitor.DriverVisitor;
import edu.kis.powp.jobs2d.features.RecordFeature;
import edu.kis.powp.jobs2d.command.OperateToCommand;

public class RecordingDriverDecorator implements Job2dDriver{
    private final Job2dDriver job2dDriver;
    public RecordingDriverDecorator(Job2dDriver driver) {
        job2dDriver = driver;
    }
    @Override
    public void setPosition(int x, int y) {
        job2dDriver.setPosition(x,y);
        RecordFeature.setCommand(new SetPositionCommand(x,y));
    }

    @Override
    public void operateTo(int x, int y) {
        job2dDriver.operateTo(x,y);
        RecordFeature.setCommand(new OperateToCommand(x,y));
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void accept(DriverVisitor visitor) {
        visitor.visit(this);
    }
}
