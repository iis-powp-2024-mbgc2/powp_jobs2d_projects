package edu.kis.powp.jobs2d.transformations;

import edu.kis.legacy.drawer.shape.ILine;
import edu.kis.powp.jobs2d.features.LinesRecorder;
import edu.kis.powp.jobs2d.features.DrawerFeature;

import java.awt.Point;
import java.util.List;

public class LinesTransformationExecutor {
    private final LinesRecorder linesRecorder;

    public LinesTransformationExecutor() {
        this.linesRecorder = LinesRecorder.getLinesRecorder();
    }

    public void execute(Transformation transformation) {
        DrawerFeature.getDrawerController().clearPanel();

        List<ILine> lines = linesRecorder.getLines();
        List<ILine> transformedLines = linesRecorder.getUntransformedLines();

        for (int i = 0; i < lines.size(); i++) {
            ILine line = lines.get(i);
            ILine unscaledLine = transformedLines.get(i);

            if (transformation instanceof ScaleTransformation) {
                Point startPoint = new Point(unscaledLine.getStartCoordinateX(), unscaledLine.getStartCoordinateY());
                Point endPoint = new Point(unscaledLine.getEndCoordinateX(), unscaledLine.getEndCoordinateY());

                Point transformedStartPoint = transformation.transform(startPoint);
                Point transformedEndPoint = transformation.transform(endPoint);

                transformedStartPoint = new ShiftTransformation(LinesRecorder.shift.x, LinesRecorder.shift.y).transform(transformedStartPoint);
                transformedEndPoint = new ShiftTransformation(LinesRecorder.shift.x, LinesRecorder.shift.y).transform(transformedEndPoint);

                line.setStartCoordinates(transformedStartPoint.x, transformedStartPoint.y);
                line.setEndCoordinates(transformedEndPoint.x, transformedEndPoint.y);
            } else {
                Point startPoint = new Point(line.getStartCoordinateX(), line.getStartCoordinateY());
                Point endPoint = new Point(line.getEndCoordinateX(), line.getEndCoordinateY());

                Point transformedStartPoint = transformation.transform(startPoint);
                Point transformedEndPoint = transformation.transform(endPoint);

                line.setStartCoordinates(transformedStartPoint.x, transformedStartPoint.y);
                line.setEndCoordinates(transformedEndPoint.x, transformedEndPoint.y);
            }

            DrawerFeature.getDrawerController().drawLine(line);
        }
    }
}
