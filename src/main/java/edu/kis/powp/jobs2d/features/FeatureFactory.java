package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;


public abstract class FeatureFactory {

    public Feature create(Application app) {
        Feature feature = createFeature();
        feature.setup(app);
        return feature;
    }

    protected abstract Feature createFeature();
}

