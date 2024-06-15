package edu.kis.powp.jobs2d.extended_driver_options;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExtendedDriverClickListener implements ActionListener {

    private final DriverOptionDecorator driverOption;
    private final DriverOptionsComposite driverOptionsComposite;
    private boolean checked;
    private final int menuIndex;

    public ExtendedDriverClickListener(DriverOptionDecorator driverOption, DriverOptionsComposite driverOptionsComposite, int menuIndex) {
        this.driverOption = driverOption;
        this.driverOptionsComposite = driverOptionsComposite;
        this.checked = false;
        this.menuIndex = menuIndex;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        checked = !checked;

        if (checked)
            driverOptionsComposite.addOption(new Option(driverOption, menuIndex));
        else
            driverOptionsComposite.removeOption(menuIndex);
    }

}
