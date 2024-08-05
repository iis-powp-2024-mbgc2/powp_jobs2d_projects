package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.features.RecordFeature;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.transformations.changedriver.RecordCommandsForTransformation;

public class RecordingDriverDecorator implements Job2dDriver{
    private Job2dDriver job2dDriver;

    public RecordingDriverDecorator(Job2dDriver driver) {
        job2dDriver = driver;
    }

    public void setDriver(Job2dDriver job2dDriver) {
        this.job2dDriver = job2dDriver;
    }

    @Override
    public void setPosition(int x, int y) {
        job2dDriver.setPosition(x,y);
        RecordFeature.setCommand(new SetPositionCommand(x,y));
        RecordCommandsForTransformation.setCommand(new SetPositionCommand(x,y));
    }

    @Override
    public void operateTo(int x, int y) {
        job2dDriver.operateTo(x,y);
        RecordFeature.setCommand(new OperateToCommand(x,y));
        RecordCommandsForTransformation.setCommand(new OperateToCommand(x,y));
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
