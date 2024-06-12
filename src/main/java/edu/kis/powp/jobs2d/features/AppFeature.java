package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.Job2dDriver;

public class AppFeature {
    private static Application app;
    private static int position = 1;

    public static void setupFeaturesMenu(Application application) {
        app = application;
        app.addComponentMenu(AppFeature.class, "Features");
    }

    public static void addFeature(String name, Job2dDriver driver) {
        SelectFeatureMenuOptionListener listener = new SelectFeatureMenuOptionListener(position ++, driver, DriverFeature.getDriverManager());
        app.addComponentMenuElementWithCheckBox(AppFeature.class, name, listener, false);
        DriverFeature.updateDriverInfo();
    }
}
