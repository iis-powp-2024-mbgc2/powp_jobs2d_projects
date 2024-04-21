package edu.kis.powp.jobs2d.drivers;

import edu.kis.legacy.drawer.shape.ILine;

public interface Job2dDriverMonitor {
    void printUsage();
    double getUpdatedUsage(ILine line);
}
