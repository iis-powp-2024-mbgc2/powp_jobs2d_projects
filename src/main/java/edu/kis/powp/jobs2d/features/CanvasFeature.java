package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.manager.CanvasManager;
import edu.kis.powp.jobs2d.canvas.Canvas;
import edu.kis.powp.jobs2d.drivers.SelectCanvasMenuOptionListener;
import edu.kis.powp.jobs2d.drivers.gui.UpdateCanvasInfoObserver;

import java.util.logging.Logger;

public class CanvasFeature {

    private static final CanvasManager canvasManager = new CanvasManager();
    private static Application app;

    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static CanvasManager getCanvasManager() {
        return canvasManager;
    }

    public static void setupCanvas(Application application) {
        app = application;
        app.addComponentMenu(CanvasFeature.class, "Canvas");
        app.addComponentMenuElement(CanvasFeature.class, "Draw Canvas",e -> {
            if (canvasManager.getCurrentCanvas() == null) {
                logger.warning("Canvas is not set");
                return;
            }
            canvasManager.getCurrentCanvas().getCanvasCommand()
                    .execute(DriverFeature.getDriverManager().getCurrentDriver());
        });

        canvasManager.getChangePublisher().addSubscriber(new UpdateCanvasInfoObserver());
    }

    public static void addCanvas(String name, Canvas canvas) {
        SelectCanvasMenuOptionListener listener = new SelectCanvasMenuOptionListener(canvasManager, canvas);
        app.addComponentMenuElement(CanvasFeature.class, name, listener);
    }

    public static void updateCanvasInfo() {
        app.updateInfo(canvasManager.getCurrentCanvas().toString());
    }
}
