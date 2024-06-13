package edu.kis.powp.jobs2d.command;

import edu.kis.powp.appbase.Application;
import java.util.logging.Logger;

public class HistoryFeature {
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final CommandHistory history = new CommandHistory();

    public static void setupHistory(Application application) {
        application.addComponentMenu(HistoryFeature.class, "History");
        application.addComponentMenuElement(HistoryFeature.class, "Show history", e -> {
            if (history.isEmpty())
                logger.info("History is empty.");
            else
                for(String log: history.getActionsHistory())
                    logger.info(log);
        });
        application.addComponentMenuElement(HistoryFeature.class, "Clear history", e -> {
            history.clear();
        });

    }
}
