package edu.kis.powp.jobs2d.drivers.adapter;

import edu.kis.powp.jobs2d.Job2dDriver;

public interface Line2dDriver extends Job2dDriver {
    int getStartX();

    int getStartY();

    void setStartX(int startX);

    void setStartY(int startY);
}
