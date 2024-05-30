package edu.kis.powp.jobs2d.features;

import edu.kis.legacy.drawer.shape.ILine;

import java.util.ArrayList;
import java.util.List;

public class LinesRecorder {
    private final static LinesRecorder instance = new LinesRecorder();
    private final List<ILine> lines;

    private LinesRecorder() {
        lines = new ArrayList<>();
    }

    public static LinesRecorder getLinesRecorder() {
        return instance;
    }

    public List<ILine> getLines() {
        return lines;
    }

    public void addLine(ILine line) {
        lines.add(line);
    }

    public void clearLines() {
        lines.clear();
    }
}
