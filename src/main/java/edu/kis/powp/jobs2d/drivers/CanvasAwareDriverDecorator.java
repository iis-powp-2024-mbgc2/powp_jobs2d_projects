package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.canvas.Canvas;
import edu.kis.powp.jobs2d.features.CanvasFeature;

import java.util.logging.Logger;

public class CanvasAwareDriverDecorator implements Job2dDriver {
    private final Job2dDriver driver;

    public CanvasAwareDriverDecorator(Job2dDriver driver) {
        super();
        this.driver = driver;
    }

    @Override
    public void setPosition(int x, int y) {
        Canvas canvas = CanvasFeature.getCanvasManager().getCurrentCanvas();
        if (canvas != null && !canvas.isInsideCanvas(x, y)) {
            Logger.getGlobal().warning(
                    String.format("Position (x: %d, y: %d) is outside of canvas", x, y));
        }
        driver.setPosition(x, y);
    }

    @Override
    public void operateTo(int x, int y) {
        Canvas canvas = CanvasFeature.getCanvasManager().getCurrentCanvas();
        if (canvas != null && !canvas.isInsideCanvas(x, y)) {
            Logger.getGlobal().warning(
                    String.format("Operation to (x: %d, y: %d) is outside of canvas", x, y));
        }
        driver.operateTo(x, y);
    }

    @Override
    public String toString() {
        return driver.toString() + " (Canvas Aware)";
    }
}
