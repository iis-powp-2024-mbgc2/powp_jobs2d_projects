package edu.kis.powp.jobs2d.features;

import edu.kis.powp.jobs2d.transformations.LinesTransformationExecutor;
import edu.kis.powp.jobs2d.transformations.ShiftTransformation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawPanelMouseMoveFeature extends MouseAdapter {
    private final JPanel panel;
    private Timer timer;

    public DrawPanelMouseMoveFeature(JPanel panel) {
        this.panel = panel;
        this.panel.addMouseListener(this);
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
                if (shiftX != 0 || shiftY != 0) {
                    LinesRecorder.shift.x += shiftX;
                    LinesRecorder.shift.y += shiftY;
                    LinesTransformationExecutor linesTransformationExecutor = new LinesTransformationExecutor();
                    linesTransformationExecutor.execute(new ShiftTransformation(shiftX, shiftY));
                    previousPosition.x = currentPosition.x;
                    previousPosition.y = currentPosition.y;
                }
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
