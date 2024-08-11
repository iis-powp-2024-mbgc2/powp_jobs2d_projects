package edu.kis.powp.jobs2d.command.manager;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.features.CommandsFeature;
import edu.kis.powp.observer.Subscriber;

import java.util.logging.Logger;

public class LoggerCommandChangeObserver implements Subscriber {

    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public void update() {
        DriverCommand command = CommandsFeature.getCommandManager().getCurrentCommand();
        logger.info("Current command set to: " + command.toString());
    }

    public void update(String message) {
        logger.info(message);
    }

    public String toString() {
        return "Logger Command Change Observer";
    }

}
