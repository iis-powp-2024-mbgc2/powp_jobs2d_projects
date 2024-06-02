package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapterRecordingMouseClicksDecorator;
import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.jobs2d.events.MouseClickListener;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseClickConverter extends MouseAdapter implements MouseClickListener {
    private final int MOUSE_BUTTON_LEFT = 1;
    private final int MOUSE_BUTTON_RIGHT = 3;

    private final JPanel panel;
    private static class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public MouseClickConverter(JPanel panel) {
        this.panel = panel;
        panel.addMouseListener(this);
    }

    public void removeMouseListener() {
        panel.removeMouseListener(this);
    }

    public void mouseClicked(MouseEvent event) {
        Point position = getClickPosition(event);

        int buttonPressed = event.getButton();

        handleDriver(position, buttonPressed);
    }

    private Point getClickPosition(MouseEvent event) {
        int x = event.getX();
        int y = event.getY();

        int offsetX = event.getComponent().getWidth()/2;
        int offsetY = event.getComponent().getHeight()/2;

        return new Point(x - offsetX, y - offsetY);
    }

    private void handleDriver(Point position, int buttonPressed) {
        Job2dDriver driver;
        Job2dDriver currentDriver = DriverFeature.getDriverManager().getCurrentDriver();
        if (currentDriver instanceof LineDriverAdapter) {
            driver = new LineDriverAdapterRecordingMouseClicksDecorator((LineDriverAdapter) currentDriver);
        } else {
            throw new UnsupportedOperationException("Current driver is not supported");
        }

        if(buttonPressed == MOUSE_BUTTON_LEFT) {
            driver.operateTo(position.x, position.y);
        } else if (buttonPressed == MOUSE_BUTTON_RIGHT) {
            driver.setPosition(position.x, position.y);
        }
    }
}