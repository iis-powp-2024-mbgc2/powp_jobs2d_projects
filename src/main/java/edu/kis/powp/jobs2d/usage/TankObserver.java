package edu.kis.powp.jobs2d.usage;

import edu.kis.powp.jobs2d.enums.DeviceManagementWarnings;

public interface TankObserver {
    void update(DeviceManagementWarnings warning);
}
