package edu.kis.powp.jobs2d.features;

public class RecordFeatureFactory extends FeatureFactory {
    @Override
    protected Feature createFeature() {
        return new RecordFeature();
    }
}