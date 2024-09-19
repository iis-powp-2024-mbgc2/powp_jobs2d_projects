package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import java.util.HashMap;
import java.util.Map;


public abstract class FeatureFactory {

    private static Map<String, Feature> featuresRegistry = new HashMap<>();

    public static void addFeature(Feature feature) {
        featuresRegistry.put(feature.getClass().getSimpleName(), feature);
    }

    public static void addFeature(Class<? extends Feature> featureClass) {
        try {
            Feature feature = featureClass.getDeclaredConstructor().newInstance();
            featuresRegistry.put(featureClass.getSimpleName(), feature);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to instantiate feature: " + featureClass.getSimpleName(), e);
        }
    }

    public static void initializeAllFeatures(Application app) {
        for (Feature feature : featuresRegistry.values()) {
            feature.setup(app);
        }
    }


    public static void setupFeature(String featureName, Application app) {
        Feature feature = featuresRegistry.get(featureName);
        if (feature != null) {
            feature.setup(app);
        } else {
            throw new IllegalArgumentException("Feature not found: " + featureName);
        }
    }

    public static void clearFeatures() {
        featuresRegistry.clear();
    }

    public static void listFeatures() {
        featuresRegistry.keySet().forEach(System.out::println);
    }
}

