package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.drivers.visitor.DriverVisitor;
import edu.kis.powp.jobs2d.features.RecordFeature;

public class RecordingDriverDecorator implements IDriver{
    private final IDriver IDriver;
    public RecordingDriverDecorator(IDriver driver) {
        IDriver = driver;
    }
    @Override
    public void setPosition(int x, int y) {
        IDriver.setPosition(x,y);
        RecordFeature.setCommand(new SetPositionCommand(x,y));
    }

    @Override
    public void operateTo(int x, int y) {
        IDriver.operateTo(x,y);
        RecordFeature.setCommand(new OperateToCommand(x,y));
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void accept(DriverVisitor visitor) {
        visitor.visit(this);
    }
}
