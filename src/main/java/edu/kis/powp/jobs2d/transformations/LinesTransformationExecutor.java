package edu.kis.powp.jobs2d.transformations;

import edu.kis.powp.jobs2d.features.LinesRecorder;
import edu.kis.powp.jobs2d.features.DrawerFeature;

import java.awt.*;

public class LinesTransformationExecutor {
    private final LinesRecorder linesRecorder;

    public LinesTransformationExecutor() {
        this.linesRecorder = LinesRecorder.getLinesRecorder();
    }

    public void execute(Transformation transformation) {
        DrawerFeature.getDrawerController().clearPanel();
        linesRecorder.getLines().forEach(line -> {
            Point transformedStartPoint = transformation.transform(new Point(line.getStartCoordinateX(), line.getStartCoordinateY()));
            Point transformedEndPoint = transformation.transform(new Point(line.getEndCoordinateX(), line.getEndCoordinateY()));
            line.setStartCoordinates(transformedStartPoint.x, transformedStartPoint.y);
            line.setEndCoordinates(transformedEndPoint.x, transformedEndPoint.y);
            DrawerFeature.getDrawerController().drawLine(line);
        });
    }
}
