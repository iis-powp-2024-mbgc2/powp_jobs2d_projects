package edu.kis.powp.jobs2d.command;

public interface CommandHistoryHandler {
    void handleHistoryUpdate(DriverCommand command,String logEntry);
}
