package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.extended_driver_options.ExtendedDriverClickListener;
import edu.kis.powp.jobs2d.extended_driver_options.DriverOptionDecorator;
import edu.kis.powp.jobs2d.extended_driver_options.DriverOptionsComposite;

public class ExtendedDriverFeature {

    private static Application app;
    private static DriverOptionsComposite driverOptionsComposite;
    private static int firstFreeMenuIndex = 0;


    public static void setupExtendedDriverPlugin(Application application) {
        app = application;
        app.addComponentMenu(ExtendedDriverFeature.class, "Driver options");

        driverOptionsComposite = DriverOptionsComposite.getInstance();
    }

    public static void addOption(String name, DriverOptionDecorator driverOption) {
        ExtendedDriverClickListener listener = new ExtendedDriverClickListener(driverOption, driverOptionsComposite, firstFreeMenuIndex);
        app.addComponentMenuElementWithCheckBox(ExtendedDriverFeature.class, name, listener, false);

        firstFreeMenuIndex++;
    }

}
