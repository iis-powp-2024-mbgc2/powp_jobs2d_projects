package edu.kis.powp.jobs2d.extended_driver_options;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

public class ExtendedDriverClickListener implements ActionListener {

    private final DriverOption driverOption;
    private final DriverOptionsComposite driverOptionsComposite;
    private boolean checked;

    public ExtendedDriverClickListener(DriverOption driverOption, DriverOptionsComposite driverOptionsComposite) {
        this.driverOption = driverOption;
        this.driverOptionsComposite = driverOptionsComposite;
        this.checked = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        checked = !checked;

        if (checked)
            driverOptionsComposite.addOption(driverOption);
        else
            driverOptionsComposite.removeOption(driverOption);

        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info("Clicked: " + driverOption.toString());
    }

}
