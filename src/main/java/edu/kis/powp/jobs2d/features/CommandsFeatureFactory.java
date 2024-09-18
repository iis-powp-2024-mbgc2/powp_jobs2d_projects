package edu.kis.powp.jobs2d.features;

public class CommandsFeatureFactory extends FeatureFactory {
    @Override
    protected Feature createFeature() {
        return new CommandsFeature();
    }
}