package edu.kis.powp.jobs2d.features;

import edu.kis.legacy.drawer.shape.ILine;

import java.util.ArrayList;
import java.util.List;

public class LinesRecorder {
    private final static LinesRecorder instance = new LinesRecorder();
    private final List<ILine> lines;
    private final List<ILine> untransformedLines;

    private LinesRecorder() {
        lines = new ArrayList<>();
        untransformedLines = new ArrayList<>();
    }

    public static LinesRecorder getLinesRecorder() {
        return instance;
    }

    public List<ILine> getLines() {
        return lines;
    }

    public List<ILine> getUntransformedLines() {
        return untransformedLines;
    }

    public void addLine(ILine line) {
        try {
            lines.add(line);
            untransformedLines.add((ILine) line.clone());
        } catch (CloneNotSupportedException ignored) {
            throw new UnsupportedOperationException("Could not clone line");
        }
    }

    public void addLine(ILine line, ILine untransformedLine) {
        lines.add(line);
        untransformedLines.add(untransformedLine);
    }

    public void clearLines() {
        lines.clear();
        untransformedLines.clear();
    }
}
