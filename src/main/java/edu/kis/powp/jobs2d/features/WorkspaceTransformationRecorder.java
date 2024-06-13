package edu.kis.powp.jobs2d.features;

import java.awt.*;
import java.util.ArrayList;

public class WorkspaceTransformationRecorder {
    private static final WorkspaceTransformationRecorder instance = new WorkspaceTransformationRecorder();
    private double zoom;
    private Point shift;

    private WorkspaceTransformationRecorder() {
        zoom = 1;
        shift = new Point(0, 0);
    }

    public static WorkspaceTransformationRecorder getInstance() {
        return instance;
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    public Point getShift() {
        return shift;
    }

    public void setShift(Point shift) {
        this.shift = shift;
    }
}
