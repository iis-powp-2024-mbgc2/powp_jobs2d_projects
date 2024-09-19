package edu.kis.powp.jobs2d.features;

public class DriverFeatureFactory extends FeatureFactory {
    @Override
    protected Feature createFeature() {
        return new DriverFeature();
    }
}