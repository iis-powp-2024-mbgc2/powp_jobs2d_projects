package edu.kis.powp.jobs2d.features;

import edu.kis.powp.jobs2d.transformations.LinesTransformationExecutor;
import edu.kis.powp.jobs2d.transformations.ScaleTransformation;
import edu.kis.powp.jobs2d.transformations.Transformation;

import javax.swing.*;
import java.awt.event.*;

public class DrawPanelMouseZoomFeature implements MouseWheelListener {
    private final JPanel panel;
    private final LinesTransformationExecutor linesTransformationExecutor;

    public DrawPanelMouseZoomFeature(JPanel panel) {
        this.panel = panel;
        this.panel.addMouseWheelListener(this);
        this.linesTransformationExecutor = new LinesTransformationExecutor();
    }

    public void removeDriver() {
        panel.removeMouseWheelListener(this);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int rotation = e.getWheelRotation();
        Transformation scaleTransformation = new ScaleTransformation(1 + (rotation * 0.1));
        linesTransformationExecutor.execute(scaleTransformation);
    }
}
