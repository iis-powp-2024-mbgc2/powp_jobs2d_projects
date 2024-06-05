package edu.kis.powp.jobs2d;

import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.visitor.DriverDetailsVisitor;
import edu.kis.powp.jobs2d.features.DriverFeature;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class DriverDetailsVisitorTest implements ActionListener {
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Override
    public void actionPerformed(ActionEvent e) {
        DriverDetailsVisitor ddv = new DriverDetailsVisitor();
        DriverManager driverManager = DriverFeature.getDriverManager();
        String details = ddv.getDetails(driverManager.getCurrentDriver());
        logger.info(details);
    }

}
