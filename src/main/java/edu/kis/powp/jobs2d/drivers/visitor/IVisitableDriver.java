package edu.kis.powp.jobs2d.drivers.visitor;

import edu.kis.powp.jobs2d.Job2dDriver;

public interface IVisitableDriver extends Job2dDriver {

    void accept(IDriverVisitor visitor);

}
