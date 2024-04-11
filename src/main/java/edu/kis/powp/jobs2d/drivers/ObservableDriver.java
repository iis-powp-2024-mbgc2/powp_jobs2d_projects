package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.events.CommandCallEvent;

import java.util.ArrayList;

public class ObservableDriver implements Job2dDriver {
    private final Job2dDriver driver;
    private final ArrayList<DriverObserver> observers;

    public ObservableDriver(Job2dDriver driver, ArrayList<DriverObserver> observers) {
        this.driver = driver;
        this.observers = observers;
    }

    private void notifyObservers(CommandCallEvent event) {
        observers.forEach((o) -> o.notifyObserver(event));
    }

    @Override
    public void setPosition(int i, int i1) {
        SetPositionCommand command = new SetPositionCommand(i, i1);
        notifyObservers(new CommandCallEvent(command));
        driver.setPosition(i, i1);
    }

    @Override
    public void operateTo(int i, int i1) {
        OperateToCommand command = new OperateToCommand(i, i1);
        notifyObservers(new CommandCallEvent(command));
        driver.operateTo(i, i1);
    }
}
