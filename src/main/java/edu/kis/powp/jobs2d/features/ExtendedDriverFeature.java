package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.extended_driver_options.ExtendedDriverClickListener;
import edu.kis.powp.jobs2d.extended_driver_options.DriverOption;
import edu.kis.powp.jobs2d.extended_driver_options.OptionsManagerSingleton;

public class ExtendedDriverFeature {

    private static Application app;
    private static OptionsManagerSingleton optionsManagerSingleton;


    public static void setupExtendedDriverPlugin(Application application) {
        app = application;
        app.addComponentMenu(ExtendedDriverFeature.class, "Driver options");

        optionsManagerSingleton = OptionsManagerSingleton.getInstance();
    }

    public static void addOption(String name, DriverOption driverOption) {
        ExtendedDriverClickListener listener = new ExtendedDriverClickListener(driverOption, optionsManagerSingleton);
        app.addComponentMenuElementWithCheckBox(ExtendedDriverFeature.class, name, listener, false);
    }

}
