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
    private final int PRINT_WAIT_TIME_MS = 300;
    private final ILine line;
    private final String name;
    private final DrawPanelController drawController;
    private boolean isPrintAvailable = true;
    private int startX = 0, startY = 0;
    private double headDistance = 0, opDistance = 0;

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
        headDistance += getUpdatedUsage(line);
    }

    @Override
    public void operateTo(int x, int y) {
        line.setStartCoordinates(this.startX, this.startY);
        this.setPosition(x, y);
        line.setEndCoordinates(x, y);

        drawController.drawLine(line);

        headDistance += getUpdatedUsage(line);
        opDistance += getUpdatedUsage(line);
        printUsage();
    }

    @Override
    public String toString() {
        return "2d device simulator - " + name;
    }

    @Override
    public void printUsage() {
        if (!isPrintAvailable) { return; }

        isPrintAvailable = false;
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(PRINT_WAIT_TIME_MS);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
            logger.info("Usage: \n" + "   Op distance: " + opDistance + "\n   Head distance: " + headDistance);
            isPrintAvailable = true;
        });
        thread.start();
    }

    @Override
    public double getUpdatedUsage(ILine line) {
        int startX = line.getStartCoordinateX();
        int startY = line.getStartCoordinateY();
        int endX = line.getEndCoordinateX();
        int endY = line.getEndCoordinateY();

        return Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
    }
}
