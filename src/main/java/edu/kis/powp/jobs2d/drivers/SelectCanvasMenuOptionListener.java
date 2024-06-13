package edu.kis.powp.jobs2d.drivers;


import edu.kis.powp.jobs2d.command.manager.CanvasManager;
import edu.kis.powp.jobs2d.canvas.Canvas;
import java.awt.event.ActionListener;

public class SelectCanvasMenuOptionListener implements ActionListener {

    private final CanvasManager canvasManager;
    private final Canvas canvas;

    public SelectCanvasMenuOptionListener(CanvasManager canvasManager, Canvas canvas) {
        this.canvasManager = canvasManager;
        this.canvas = canvas;
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        canvasManager.setCurrentCanvas(canvas);
    }
}
