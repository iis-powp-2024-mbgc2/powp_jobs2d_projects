package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;

import java.util.logging.Logger;

public class LoggerDriver implements Job2dDriver {
    private final Logger logger = Logger.getLogger("global");
    private int posX = 0, posY = 0;
    private final boolean isDetailedLogger;

    public LoggerDriver(boolean isDetailedLogger) {
        this.isDetailedLogger = isDetailedLogger;
    }

    @Override
    public void setPosition(int x, int y) {
        String message = this.getMessage(x, y, "Set position");
        this.logger.info(message);
        this.posX = x;
        this.posY = y;
    }

    @Override
    public void operateTo(int x, int y) {
        String message = this.getMessage(x, y, "Operate");
        this.logger.info(message);
        this.posX = x;
        this.posY = y;
    }

    private String getMessage(int x, int y, String messagePrefix) {
        return this.isDetailedLogger ? this.getDetailedMessage(x, y, messagePrefix) : this.getSimpleMessage(x, y, messagePrefix);
    }

    private String getDetailedMessage(int newX, int newY, String messagePrefix) {
        String movedFromMessage = messagePrefix + " from x: " + this.posX + ", y: " + this.posY + "\n   to x: " + newX + ", y: " + newY;
        String changeMessage = "\n   Change x: " + (newX - this.posX) + ", y: " + (newY - this.posY);
        return movedFromMessage + changeMessage;
    }

    private String getSimpleMessage(int newX, int newY, String messagePrefix) {
        return messagePrefix + " to x: " + newX + ", y: " + newY;
    }

    @Override
    public String toString() {
        return "Logger driver";
    }
}
