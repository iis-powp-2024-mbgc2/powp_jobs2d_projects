package edu.kis.powp.jobs2d.extended_driver_options;

import edu.kis.powp.jobs2d.Job2dDriver;

public abstract class DriverOptionDecorator implements Job2dDriver {

    private Job2dDriver driver;

    protected void setDriver(Job2dDriver job2dDriver) {
        this.driver = job2dDriver;
    }

    @Override
    public void setPosition(int x, int y) {
        if (driver == null)
            return;

        driver.setPosition(x, y);
    }

    @Override
    public void operateTo(int x, int y) {
        if (driver == null)
            return;

        driver.operateTo(x, y);
    }
}
