package edu.kis.powp.jobs2d.features;

import edu.kis.powp.jobs2d.transformations.LinesTransformationExecutor;
import edu.kis.powp.jobs2d.transformations.ScaleTransformation;
import edu.kis.powp.jobs2d.transformations.Transformation;

import javax.swing.*;
import java.awt.event.*;

public class DrawPanelMouseZoomFeature implements MouseWheelListener {
    private final JPanel panel;
    private final LinesTransformationExecutor linesTransformationExecutor;
    private double zoom;
    public static final double SCALE_FACTOR = 0.1;

    public DrawPanelMouseZoomFeature(JPanel panel) {
        this.panel = panel;
        this.panel.addMouseWheelListener(this);
        this.linesTransformationExecutor = new LinesTransformationExecutor();
        this.zoom = 1;
    }

    public double getZoom() {
        return zoom;
    }

    public void removeDriver() {
        panel.removeMouseWheelListener(this);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int rotation = e.getWheelRotation();
        this.zoom += rotation * SCALE_FACTOR * this.zoom;
        Transformation scaleTransformation = new ScaleTransformation(this.zoom);
        linesTransformationExecutor.execute(scaleTransformation);
    }
}
