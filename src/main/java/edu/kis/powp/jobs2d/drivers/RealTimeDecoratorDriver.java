package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.drivers.visitor.IDriverVisitor;
import edu.kis.powp.jobs2d.drivers.visitor.IVisitableDriver;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Queue;

public class RealTimeDecoratorDriver implements IVisitableDriver {
    private JPanel panel;
    private Queue<Runnable> tasks = new LinkedList<>();
    private int intervalMs = 300;

    private final IVisitableDriver driver;

    public RealTimeDecoratorDriver(DriverManager driverManager, JPanel panel) {
        this.driver = driverManager.getCurrentDriverAndFeaturesComposite(this);
        this.panel = panel;
        startTaskExecution();
    }

    @Override
    public void operateTo(int x, int y) {
        if (tasks.isEmpty()) {
            driver.operateTo(x, y);
            panel.repaint();
            driver.setPosition(x, y);
            tasks.add(() -> {
                try {
                    Thread.sleep(intervalMs);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        } else {
            tasks.add(() -> {
                driver.operateTo(x, y);
                panel.repaint();
                driver.setPosition(x, y);
                try {
                    Thread.sleep(intervalMs);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @Override
    public void setPosition(int x, int y) {
        if (tasks.isEmpty()) {
            driver.setPosition(x, y);
        } else {
            tasks.add(() -> {
                driver.setPosition(x, y);
            });
        }
    }

    private void startTaskExecution() {
        new Timer(0, e -> {
            if (!tasks.isEmpty()) {
                SwingUtilities.invokeLater(tasks.poll());
            }
        }).start();
    }

    public void setIntervalMs(int intervalMs) {
        this.intervalMs = intervalMs;
    }

    public IVisitableDriver getDriver() {
        return driver;
    }

    @Override
    public void accept(IDriverVisitor visitor) {
        visitor.visit(this);
    }
}
