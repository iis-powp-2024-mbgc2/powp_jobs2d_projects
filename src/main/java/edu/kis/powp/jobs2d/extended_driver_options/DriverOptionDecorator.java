package edu.kis.powp.jobs2d.extended_driver_options;

import edu.kis.powp.jobs2d.Job2dDriver;

public abstract class DriverOptionDecorator implements Job2dDriver {

    private Job2dDriver driver;

    protected void setDriver(Job2dDriver job2dDriver) {
        this.driver = job2dDriver;
    }

    @Override
    public void setPosition(int i, int i1) {
        if (driver == null)
            return;

        driver.setPosition(i, i1);
    }

    @Override
    public void operateTo(int i, int i1) {
        if (driver == null)
            return;

        driver.operateTo(i, i1);
    }
}
