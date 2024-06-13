package edu.kis.powp.jobs2d.usage;

import edu.kis.powp.jobs2d.enums.DeviceManagementWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelWarningListener {
	private final List<WarningObserver> observers = new ArrayList<>();
	private final Map<DeviceManagementWarnings, String> errorMessages = new HashMap<>();

	public interface WarningObserver {
		void onWarning(DeviceManagementWarnings warning);
	}

	public LevelWarningListener() {
		initializeErrorMessages();
	}

	private void initializeErrorMessages() {
		errorMessages.put(DeviceManagementWarnings.MAX_LEVEL, "Max Level Reached");
		errorMessages.put(DeviceManagementWarnings.MEDIUM_LEVEL, "Medium Level");
		errorMessages.put(DeviceManagementWarnings.LOW_LEVEL, "Low Level");
		errorMessages.put(DeviceManagementWarnings.NEEDS_REFILL, "Needs Refill");
		errorMessages.put(DeviceManagementWarnings.EMPTY_OUT_OF_WORK, "Empty Out of Work");
	}

	public void addObserver(WarningObserver observer) {
		observers.add(observer);
	}

	public void removeObserver(WarningObserver observer) {
		observers.remove(observer);
	}

	public void notifyObservers(DeviceManagementWarnings warning) {
		for (WarningObserver observer : observers) {
			observer.onWarning(warning);
		}
	}

	public void temp(DeviceManagementWarnings warning) {
		notifyObservers(warning);
	}

	public String getWarningMessage(DeviceManagementWarnings warning) {
		return errorMessages.getOrDefault(warning, "Unknown");
	}
}