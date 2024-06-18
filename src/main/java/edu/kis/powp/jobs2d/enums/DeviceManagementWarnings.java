package edu.kis.powp.jobs2d.enums;

import java.awt.Color;

public enum DeviceManagementWarnings {
    MAX_LEVEL,
    MEDIUM_LEVEL,
    LOW_LEVEL,
    NEEDS_REFILL,
    EMPTY_OUT_OF_WORK;

    public Color getColor() {
        switch (this) {
            case MAX_LEVEL:
                return Color.GREEN;
            case MEDIUM_LEVEL:
                return Color.YELLOW;
            case LOW_LEVEL:
                return Color.ORANGE;
            case NEEDS_REFILL:
                return Color.RED;
            case EMPTY_OUT_OF_WORK:
                return Color.BLACK;
            default:
                return Color.BLACK;
        }
    }
}
