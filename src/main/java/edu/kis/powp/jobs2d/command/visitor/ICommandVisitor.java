package edu.kis.powp.jobs2d.command.visitor;

import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.IDriverCommand;

public interface ICommandVisitor {
    void visit(IDriverCommand command);
    void visit(ICompoundCommand command);
}
