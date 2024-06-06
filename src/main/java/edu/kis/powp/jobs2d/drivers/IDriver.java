package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.drivers.visitor.DriverVisitor;

public interface IDriver extends Job2dDriver {
    void accept(DriverVisitor visitor);
}
