package edu.kis.powp.jobs2d.command.manager;

import edu.kis.powp.jobs2d.canvas.Canvas;
import edu.kis.powp.jobs2d.canvas.CanvasA4;
import edu.kis.powp.observer.Publisher;

/**
 * Canvas manager provides means to set up the canvas.
 */
public class CanvasManager {
    private Canvas currentCanvas = new CanvasA4();

    private final Publisher changePublisher = new Publisher();

    /**
     * @param canvas Set the canvas as current.
     */
    public synchronized void setCurrentCanvas(Canvas canvas) {
        currentCanvas = canvas;
        changePublisher.notifyObservers();
    }

    /**
     * @return Current canvas.
     */
    public synchronized Canvas getCurrentCanvas() {
        return currentCanvas;
    }

    public Publisher getChangePublisher() {
        return changePublisher;
    }
}
