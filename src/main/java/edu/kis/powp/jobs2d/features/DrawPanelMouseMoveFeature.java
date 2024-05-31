package edu.kis.powp.jobs2d.features;

import edu.kis.powp.jobs2d.transformations.LinesTransformationExecutor;
import edu.kis.powp.jobs2d.transformations.ShiftTransformation;
import edu.kis.powp.jobs2d.transformations.Transformation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawPanelMouseMoveFeature extends MouseAdapter {
    private final JPanel panel;
    private final LinesTransformationExecutor linesTransformationExecutor;
    private Timer timer;
    private final Point shift;

    public DrawPanelMouseMoveFeature(JPanel panel) {
        this.panel = panel;
        this.panel.addMouseListener(this);
        this.linesTransformationExecutor = new LinesTransformationExecutor();
        this.shift = new Point(0, 0);
    }

    public Point getShift() {
        return shift;
    }

    public void removeDriver() {
        panel.removeMouseListener(this);
    }

    @Override
     public void mousePressed(MouseEvent event) {
        int buttonPressed = event.getButton();
        if (buttonPressed == MouseEvent.BUTTON1) {
            Point previousPosition =  MouseInfo.getPointerInfo().getLocation();
            timer = new Timer(10, e -> {
                Point currentPosition = MouseInfo.getPointerInfo().getLocation();
                int shiftX = currentPosition.x - previousPosition.x;
                int shiftY = previousPosition.y - currentPosition.y;
                this.shift.x += shiftX;
                this.shift.y += shiftY;
                Transformation shiftTransformation = new ShiftTransformation(shiftX, shiftY);
                linesTransformationExecutor.execute(shiftTransformation);
                previousPosition.x = currentPosition.x;
                previousPosition.y = currentPosition.y;
            });
            timer.start();
        }
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON1 && timer != null) {
            timer.stop();
        }
    }
}
