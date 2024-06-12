package edu.kis.powp.jobs2d.drivers.gui;

import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.jobs2d.features.RecordFeature;
import edu.kis.powp.observer.Subscriber;

public class WrapDriverWithRecordingDecoratorObserver implements Subscriber {

    @Override
    public void update() {
        RecordFeature.wrapDriverWithRecordingDecorator();
    }
}
