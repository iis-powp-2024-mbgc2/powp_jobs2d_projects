package edu.kis.powp.jobs2d.features;

import edu.kis.powp.jobs2d.transformations.LinesTransformationExecutor;
import edu.kis.powp.jobs2d.transformations.ScaleTransformation;

import javax.swing.*;
import java.awt.event.*;

public class DrawPanelMouseZoomFeature implements MouseWheelListener {
    private final JPanel panel;
    public static final double ZOOM_FACTOR = 0.1;

    public DrawPanelMouseZoomFeature(JPanel panel) {
        this.panel = panel;
        this.panel.addMouseWheelListener(this);
    }

    public void removeDriver() {
        panel.removeMouseWheelListener(this);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int rotation = e.getWheelRotation();
        LinesRecorder.zoom += rotation * ZOOM_FACTOR * LinesRecorder.zoom;
        LinesTransformationExecutor linesTransformationExecutor = new LinesTransformationExecutor();
        linesTransformationExecutor.execute(new ScaleTransformation(LinesRecorder.zoom));
    }
}
