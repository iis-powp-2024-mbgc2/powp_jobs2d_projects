package edu.kis.powp.jobs2d.features;

public class DrawerFeatureFactory extends FeatureFactory {
    @Override
    protected Feature createFeature() {
        return new DrawerFeature();
    }
}