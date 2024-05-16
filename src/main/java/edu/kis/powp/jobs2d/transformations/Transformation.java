package edu.kis.powp.jobs2d.transformations;

import java.awt.Point;

public abstract class Transformation {

    private final String name;
    public Transformation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract Point transform(Point point);
}
