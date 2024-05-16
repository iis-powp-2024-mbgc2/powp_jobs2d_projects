package edu.kis.powp.jobs2d.command.manager;

import java.util.Iterator;
import java.util.List;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.CommandVisitor;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.observer.Publisher;

public interface ICommandManager {

    void setCurrentCommand(DriverCommand commandList);

    void setCurrentCommand(List<DriverCommand> commandList, String name);

    void runCommand(Job2dDriver driver);

    DriverCommand getCurrentCommand();

    void clearCurrentCommand();

    String getCurrentCommandString();

    Publisher getChangePublisher();
}
