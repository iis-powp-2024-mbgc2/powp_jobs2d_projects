package edu.kis.powp.jobs2d.drivers.UsageMonitor;

public class UsageMonitorStorage {

    private static double headDistance = 0;
    private static double opDistance = 0;

    public static void addHeadDistance(double distance) {
        headDistance += distance;
    }

    public static void addOpDistance(double distance) {
        opDistance += distance;
    }

    public static double getHeadDistance() {
        return headDistance;
    }

    public static double getOpDistance() {
        return opDistance;
    }
}
