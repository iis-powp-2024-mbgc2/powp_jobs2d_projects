package edu.kis.powp.jobs2d.command;

public interface CommandVisitor {
    void visit(OperateToCommand operateToCommand);
    void visit(SetPositionCommand setPositionCommand);
    void visit(ICompoundCommand compoundCommand);
}
