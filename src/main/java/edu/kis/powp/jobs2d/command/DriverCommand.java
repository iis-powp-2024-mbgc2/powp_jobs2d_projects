package edu.kis.powp.jobs2d.command;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.canvas.Canvas;
import edu.kis.powp.jobs2d.features.CanvasFeature;

import java.util.logging.Logger;

/**
 * DriverCommand interface.
 */
public abstract class DriverCommand {

    protected int posX = 0, posY = 0;
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Execute command on driver.
     *
     * @param driver driver.
     */
    public void execute(Job2dDriver driver) {
        Canvas currentCanvas = CanvasFeature.getCanvasManager().getCurrentCanvas();
        if (currentCanvas!= null && !currentCanvas.isInsideCanvas(posX, posY)) {
            logger.warning(String.format("Position out of canvas bounds: x: %d, y: %d", posX, posY));
        }
    }

    public void accept(CommandVisitor commandVisitor) {

    }
}
