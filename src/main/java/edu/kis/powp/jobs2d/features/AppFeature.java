package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.SelectDriverMenuOptionListener;

public class AppFeature {
    private static Application app;

    public static void setupFeaturesMenu(Application application) {
        app = application;
        app.addComponentMenu(AppFeature.class, "Features");
    }

    public static void addFeature(String name, Job2dDriver driver) {
        SelectDriverMenuOptionListener listener = new SelectDriverMenuOptionListener(driver, DriverFeature.getDriverManager());
        app.addComponentMenuElementWithCheckBox(AppFeature.class, name, listener, false);
        DriverFeature.updateDriverInfo();
    }
}
