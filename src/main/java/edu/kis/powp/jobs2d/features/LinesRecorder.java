package edu.kis.powp.jobs2d.features;

import edu.kis.legacy.drawer.shape.ILine;

import java.util.ArrayList;
import java.util.List;

public class LinesRecorder {
    private final static LinesRecorder instance = new LinesRecorder();
    private final List<ILine> lines;
    private final List<ILine> unscaledLines;

    private LinesRecorder() {
        lines = new ArrayList<>();
        unscaledLines = new ArrayList<>();
    }

    public static LinesRecorder getLinesRecorder() {
        return instance;
    }

    public List<ILine> getLines() {
        return lines;
    }

    public List<ILine> getUnscaledLines() {
        return unscaledLines;
    }

    public void addLine(ILine line) {
        lines.add(line);
        try {
            unscaledLines.add((ILine) line.clone());
        } catch (CloneNotSupportedException e) {}
    }

    public void clearRecordedLines() {
        lines.clear();
        unscaledLines.clear();
    }
}
