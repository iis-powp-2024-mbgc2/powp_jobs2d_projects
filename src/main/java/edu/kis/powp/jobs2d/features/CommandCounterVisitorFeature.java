package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.visitor.CommandCounterVisitor;

import java.awt.event.ActionEvent;
import java.util.logging.Logger;

public class CommandCounterVisitorFeature {

    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private final static CommandCounterVisitor commandCounterVisitor = new CommandCounterVisitor();

    public static void setupCommandCounterVisitor(Application application) {
        application.addTest("Command Counter Visitor Test", (ActionEvent e) -> {
            logger.info("ICompoundCommandCounter: " + commandCounterVisitor.getICompoundCommandCounter());
            logger.info("IDriverCommandCounter: " + commandCounterVisitor.getIDriverCommandCounter());
            logger.info("OperateToCommandCounter: " + commandCounterVisitor.getOperateToCommandCounter());
            logger.info("SetPositionCommandCounter: " + commandCounterVisitor.getSetPositionCommandCounter());
        });
    }

    public static CommandCounterVisitor getCommandCounterVisitor() {
        return commandCounterVisitor;
    }
}
