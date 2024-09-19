package edu.kis.powp.jobs2d.features;

public class MouseSettingsFeatureFactory extends FeatureFactory {
    @Override
    protected Feature createFeature() {
        return new MouseSettingsFeature();
    }
}