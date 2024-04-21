package edu.kis.powp.jobs2d.drivers.adapter;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.ILine;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.Job2dDriverMonitor;

import java.util.logging.Logger;

/**
 * Line adapter - Job2dDriver with DrawPanelController object.
 */
public class LineDriverAdapter implements Job2dDriver, Job2dDriverMonitor {
    private ILine line;
    private int startX = 0, startY = 0;
    private String name;

    private DrawPanelController drawController;

    private final double distance = 0.0;

    public LineDriverAdapter(DrawPanelController drawController, ILine line, String name) {
        super();
        this.drawController = drawController;
        this.line = line;
        this.name = name;
    }

    @Override
    public void setPosition(int x, int y) {
        this.startX = x;
        this.startY = y;
    }

    @Override
    public void operateTo(int x, int y) {
        line.setStartCoordinates(this.startX, this.startY);
        this.setPosition(x, y);
        line.setEndCoordinates(x, y);

        drawController.drawLine(line);

        this.printUsage();
    }

    @Override
    public String toString() {
        return "2d device simulator - " + name;
    }

    @Override
    public void printUsage() {
        Logger logger =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        logger.info(String.valueOf(distance));
    }
}
