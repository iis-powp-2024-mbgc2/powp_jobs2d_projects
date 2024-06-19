package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.drivers.visitor.DriverVisitor;
import edu.kis.powp.jobs2d.features.RecordFeature;

public class RecordingDriverDecorator implements IDriver{
    private final IDriver driver;
    public RecordingDriverDecorator(IDriver driver) {
        this.driver = driver;
    }
    @Override
    public void setPosition(int x, int y) {
        driver.setPosition(x,y);
        RecordFeature.setCommand(new SetPositionCommand(x,y));
    }

    @Override
    public void operateTo(int x, int y) {
        driver.operateTo(x,y);
        RecordFeature.setCommand(new OperateToCommand(x,y));
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public void accept(DriverVisitor visitor) {
        visitor.visit(this);
        if(driver != null) driver.accept(visitor);
    }
}
