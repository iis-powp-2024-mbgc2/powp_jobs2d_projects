package edu.kis.powp.jobs2d.drivers;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.ILine;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.canvas.Canvas;
import edu.kis.powp.jobs2d.features.CanvasFeature;

import java.util.logging.Logger;

public class CanvasAwareDriver implements Job2dDriver {
    private final ILine line;
    private int posX = 0, posY = 0;
    private final DrawPanelController drawController;

    public CanvasAwareDriver(DrawPanelController drawController, ILine line) {
        super();
        this.drawController = drawController;
        this.line = line;
    }

    @Override
    public void setPosition(int x, int y) {
        Canvas canvas = CanvasFeature.getCanvasManager().getCurrentCanvas();
        if (canvas != null && !canvas.isInsideCanvas(x, y)) {
            Logger.getGlobal().warning(String.format("Position (x: %d, y: %d) is outside of canvas", x, y));
        }
        this.posX = x;
        this.posY = y;
    }

    @Override
    public void operateTo(int x, int y) {
        line.setStartCoordinates(this.posX, this.posY);
        this.setPosition(x, y);
        line.setEndCoordinates(x, y);

        drawController.drawLine(line);
    }

    @Override
    public String toString() {
        return "Canvas aware driver";
    }
}
