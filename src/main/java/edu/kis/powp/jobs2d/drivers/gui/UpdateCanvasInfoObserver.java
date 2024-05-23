package edu.kis.powp.jobs2d.drivers.gui;

import edu.kis.powp.jobs2d.features.CanvasFeature;
import edu.kis.powp.observer.Subscriber;

public class UpdateCanvasInfoObserver implements Subscriber {
    @Override
    public void update() {
        CanvasFeature.updateCanvasInfo();
    }
}
