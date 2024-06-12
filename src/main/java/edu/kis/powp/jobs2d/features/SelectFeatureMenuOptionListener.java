package edu.kis.powp.jobs2d.features;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.DriverManager;

public class SelectFeatureMenuOptionListener implements ActionListener {
    private final DriverManager driverManager;
    private final Job2dDriver driver;
    private final int position;

    public SelectFeatureMenuOptionListener(int position, Job2dDriver driver, DriverManager driverManager) {
        this.driverManager = driverManager;
        this.driver = driver;
        this.position = position;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        driverManager.toggleFeature(position, driver);
    }
}
