package edu.kis.powp.jobs2d.drivers;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.canvas.Canvas;
import edu.kis.powp.jobs2d.command.canvas.CanvasA4;
import edu.kis.powp.jobs2d.command.canvas.CanvasVisitor;
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
     * @param command commands
     * @return value false or true if command can fit into current Canvas
     */
    public void checkCanvas(DriverCommand command)
    {
        CanvasVisitor canvasVisitor = new CanvasVisitor(this.currentCanvas);
        command.accept(canvasVisitor);

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
