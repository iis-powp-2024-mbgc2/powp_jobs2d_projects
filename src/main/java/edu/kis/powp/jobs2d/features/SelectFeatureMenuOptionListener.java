package edu.kis.powp.jobs2d.features;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.visitor.IVisitableDriver;

public class SelectFeatureMenuOptionListener implements ActionListener {
    private final DriverManager driverManager;
    private final IVisitableDriver driver;
    private final int position;

    public SelectFeatureMenuOptionListener(int position, IVisitableDriver driver, DriverManager driverManager) {
        this.driverManager = driverManager;
        this.driver = driver;
        this.position = position;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        driverManager.toggleFeature(position, driver);
    }
}
