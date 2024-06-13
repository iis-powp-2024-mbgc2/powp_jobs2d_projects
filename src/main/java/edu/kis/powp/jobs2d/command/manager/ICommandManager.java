package edu.kis.powp.jobs2d.command.manager;

import java.util.List;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.drivers.visitor.IVisitableDriver;
import edu.kis.powp.observer.Publisher;

public interface ICommandManager {

    void setCurrentCommand(DriverCommand commandList);

    void setCurrentCommand(List<DriverCommand> commandList, String name);

    void runCommand(IVisitableDriver driver);

    DriverCommand getCurrentCommand();

    void clearCurrentCommand();

    String getCurrentCommandString();

    Publisher getChangePublisher();
}
