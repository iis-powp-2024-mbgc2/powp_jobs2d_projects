package edu.kis.powp.jobs2d.extended_driver_options;

import edu.kis.powp.jobs2d.Job2dDriver;

public abstract class DriverOptionDecorator implements Job2dDriver {

    protected Job2dDriver driver;

    protected void setDriver(Job2dDriver job2dDriver) {
        this.driver = job2dDriver;
    }

}
