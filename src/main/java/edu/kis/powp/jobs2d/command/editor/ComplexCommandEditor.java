package edu.kis.powp.jobs2d.command.editor;

import edu.kis.powp.jobs2d.command.*;
import java.util.logging.Logger;

public class ComplexCommandEditor {
    private final ICompoundCommand compoundCommand;

    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public ComplexCommandEditor(ICompoundCommand compoundCommand) {
        this.compoundCommand = compoundCommand;
    }


    public ICompoundCommand getEditedCompoundCommand() {
        return this.compoundCommand;
    }

    public void moveCommand(int commandIndex) {
        try {
            compoundCommand.interchangeCommands(commandIndex, commandIndex - 1);
        } catch (IndexOutOfBoundsException e) {
            logger.info(e.toString());
        }
    }

    public void modifyCoordinates(int commandIndex, int x, int y) {
        try {
            compoundCommand.modifyCoordinates(commandIndex, x, y);
        } catch (IndexOutOfBoundsException e) {
            logger.info(e.toString());
        }
    }
}
