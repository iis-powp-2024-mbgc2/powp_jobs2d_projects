package edu.kis.powp.jobs2d.command.visitor;

import edu.kis.powp.jobs2d.command.DriverCommand;

public class VisitorStatisticsGenerator {
    public String generateStatistics(DriverCommand command) {
        if (command != null) {
            CommandStatisticVisitor statisticVisitor = new CommandStatisticVisitor();
            command.accept(statisticVisitor);
            CommandCounterVisitor commandCounterVisitor = new CommandCounterVisitor();
            command.accept(commandCounterVisitor);

            int totalSubCommandsCount = commandCounterVisitor.getOperateToCount() + commandCounterVisitor.getSetPositionCount();
            int operateToCommandsCount = commandCounterVisitor.getOperateToCount();
            double totalCommandLength = statisticVisitor.getTotalLength();
            double operateToCommandLength = statisticVisitor.getOperateToLength();

            return "Number of sub-commands used: " + totalSubCommandsCount + "\nNumber of operateTo commands used: " +
                    operateToCommandsCount + "\nTotal command length: " + totalCommandLength +
                    "\nOperateTo command length: " + operateToCommandLength;
        } else {
            return "No statistics to display";
        }
    }
}
