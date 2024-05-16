package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.events.MouseClickListener;
import edu.kis.powp.jobs2d.features.DriverFeature;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MouseClickEditor extends MouseClickConverter implements MouseClickListener {
    private ArrayList<Point> points;
    private Job2dDriver driver;

    public MouseClickEditor(JPanel drawArea, ArrayList<Point> points, Job2dDriver driver) {
        super(drawArea);
        this.points = points;
        this.driver = driver;
        drawArea.addMouseListener(this);
    }

    @Override
    public void handleDriver(Point position, int buttonPressed) {

        if(buttonPressed == MOUSE_BUTTON_LEFT) {

            double minDistance = 99999999;
            for (Point point : points) {
                double distance = point.distance(position);
                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
            System.out.println(minDistance);
        } else if (buttonPressed == MOUSE_BUTTON_RIGHT) {
            driver.setPosition(position.x, position.y);
        }
    }

}
