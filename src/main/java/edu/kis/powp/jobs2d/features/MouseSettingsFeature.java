package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.drivers.MouseClickConverter;
import edu.kis.powp.jobs2d.drivers.MouseClickConverterWithRightAndLeft;
import edu.kis.powp.jobs2d.enums.MouseOption;
import edu.kis.powp.jobs2d.events.MouseClickListener;
import edu.kis.powp.jobs2d.events.SelectMouseOptionListener;

public class MouseSettingsFeature {
    private static Application application;
    public static MouseClickListener mouseClickListener;

    public static void setupMouseSettingsFeature(Application app) {
        application = app;

        SelectMouseOptionListener leftOnlyOption = new SelectMouseOptionListener(MouseOption.LEFT_ONLY);
        SelectMouseOptionListener leftAndRightOption = new SelectMouseOptionListener(MouseOption.LEFT_AND_RIGHT);

        application.addComponentMenu(MouseSettingsFeature.class, "Mouse Settings");
        application.addComponentMenuElement(MouseSettingsFeature.class, "Lines Only", leftOnlyOption);
        application.addComponentMenuElement(MouseSettingsFeature.class, "Continuous and Lines", leftAndRightOption);

    }

    //Drawing by selecting points with left click
    public static void setMouseClickConverterLeftOnly() {

        if( mouseClickListener != null){
            ((MouseClickConverterWithRightAndLeft) mouseClickListener).removeMouseListener();
            mouseClickListener = null;
        }
        mouseClickListener = new MouseClickConverter(application.getFreePanel());

    }

    //Drawing by holding right click
    public static void setMouseClickConverterLeftAndRight() {
        if (mouseClickListener != null) {
            ((MouseClickConverter) mouseClickListener).removeMouseListener();
            mouseClickListener = null;
        }
        mouseClickListener = new MouseClickConverterWithRightAndLeft(application.getFreePanel() , DriverFeature.getDriverManager().getCurrentDriver() );

    }

}