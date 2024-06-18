package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.events.SelectWorkspaceTransformationListener;

public class WorkspaceTransformationFeature {
    private static boolean isTransforming = false;

    private static DrawPanelMouseMoveFeature drawPanelMouseMoveFeature;
    private static DrawPanelMouseZoomFeature drawPanelMouseZoomFeature;

    public static void setupWorkspaceTransformationFeature(Application app) {
        SelectWorkspaceTransformationListener startOption = new SelectWorkspaceTransformationListener(isTransforming);
        app.addComponentMenuElementWithCheckBox(MouseSettingsFeature.class, "Enable/Disable workspace transformation", startOption, isTransforming);

        drawPanelMouseMoveFeature = new DrawPanelMouseMoveFeature(app.getFreePanel());
        drawPanelMouseZoomFeature = new DrawPanelMouseZoomFeature(app.getFreePanel());
    }

    public static boolean isTransforming() {
        return isTransforming;
    }

    public static void enableTransformation() {
        drawPanelMouseMoveFeature.addDriver();
        drawPanelMouseZoomFeature.addDriver();
        isTransforming = true;
    }

    public static void disableTransformation() {
        drawPanelMouseMoveFeature.removeDriver();
        drawPanelMouseZoomFeature.removeDriver();
        isTransforming = false;
    }
}
