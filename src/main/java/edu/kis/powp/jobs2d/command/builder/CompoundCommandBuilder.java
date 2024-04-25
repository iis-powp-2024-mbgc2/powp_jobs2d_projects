package edu.kis.powp.jobs2d.command.builder;

import edu.kis.powp.jobs2d.command.CompoundCommand;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.commons.Builder;

import java.util.ArrayList;
import java.util.List;

public class CompoundCommandBuilder implements Builder<CompoundCommand> {

    private final List<DriverCommand> commands = new ArrayList<>();

    private String name = "";

    public CompoundCommandBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CompoundCommandBuilder addOperateTo(int x, int y) {
        commands.add(new OperateToCommand(x, y));
        return this;
    }

    public CompoundCommandBuilder addSetPosition(int x, int y) {
        commands.add(new SetPositionCommand(x, y));
        return this;
    }

    public CompoundCommandBuilder addCommand(DriverCommand command) {
        commands.add(command);
        return this;
    }

    @Override
    public CompoundCommand build() {
        return new CompoundCommand(commands, name);
    }
}
