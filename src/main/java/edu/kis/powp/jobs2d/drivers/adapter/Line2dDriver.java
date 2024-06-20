package edu.kis.powp.jobs2d.drivers.adapter;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.visitor.IVisitableDriver;

public interface Line2dDriver extends IVisitableDriver {
    int getStartX();

    int getStartY();

    void setStartX(int startX);

    void setStartY(int startY);
}
