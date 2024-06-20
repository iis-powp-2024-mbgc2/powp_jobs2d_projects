package edu.kis.powp.jobs2d.drivers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import edu.kis.powp.jobs2d.drivers.adapter.Line2dDriver;


public class SelectDriverMenuOptionListener implements ActionListener {
    private DriverManager driverManager;
    private Line2dDriver driver;

    public SelectDriverMenuOptionListener(Line2dDriver driver, DriverManager driverManager) {
        this.driverManager = driverManager;
        this.driver = driver;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        driverManager.setCurrentDriver(driver);
    }
}