package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.features.DriverFeature;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseClickConverter extends MouseAdapter {
    private final int MOUSE_BUTTON_LEFT = 1;
    private final int MOUSE_BUTTON_RIGHT = 3;

    private static class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public MouseClickConverter(JPanel panel) {
        panel.addMouseListener(this);
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
        Job2dDriver driver = DriverFeature.getDriverManager().getCurrentDriver();

        if(buttonPressed == MOUSE_BUTTON_LEFT) {
            driver.operateTo(position.x, position.y);
        } else if (buttonPressed == MOUSE_BUTTON_RIGHT) {
            driver.setPosition(position.x, position.y);
        }
    }
}
