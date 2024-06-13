package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import java.awt.event.ActionListener;

public class TransformationsFeature {

    private static Application app;

    public static void setupPresetCommands(Application application) {
        app = application;
        app.addComponentMenu(TransformationsFeature.class, "Transform");
    }

    public static void addCommand(String name, ActionListener listener) {
        app.addComponentMenuElement(TransformationsFeature.class, name, listener);
    }
}
