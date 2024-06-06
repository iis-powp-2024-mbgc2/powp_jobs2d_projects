package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.extended_driver_options.DriverOptionDecorator;
import edu.kis.powp.jobs2d.features.RecordFeature;

public class RecordingDriverDecorator extends DriverOptionDecorator {


    public RecordingDriverDecorator() {
    }

    @Override
    public void setPosition(int x, int y) {
        super.driver.setPosition(x, y);
        RecordFeature.setCommand(new SetPositionCommand(x, y));
    }

    @Override
    public void operateTo(int x, int y) {
        super.driver.operateTo(x, y);
        RecordFeature.setCommand(new OperateToCommand(x, y));
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
