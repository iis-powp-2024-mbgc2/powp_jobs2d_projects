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
        List<ILine> unscaledLines = linesRecorder.getUnscaledLines();

        for (int i = 0; i < lines.size(); i++) {
            ILine line = lines.get(i);
            ILine referenceLine;

            if (transformation instanceof ScaleTransformation) {
                referenceLine = unscaledLines.get(i);
            } else {
                referenceLine = lines.get(i);
            }
            Point transformedStartPoint = transformation.transform(new Point(referenceLine.getStartCoordinateX(), referenceLine.getStartCoordinateY()));
            Point transformedEndPoint = transformation.transform(new Point(referenceLine.getEndCoordinateX(), referenceLine.getEndCoordinateY()));

            line.setStartCoordinates(transformedStartPoint.x, transformedStartPoint.y);
            line.setEndCoordinates(transformedEndPoint.x, transformedEndPoint.y);

            DrawerFeature.getDrawerController().drawLine(line);
        }

        if (!(transformation instanceof ScaleTransformation)) {
            unscaledLines.forEach(line -> {
                Point transformedStartPoint = transformation.transform(new Point(line.getStartCoordinateX(), line.getStartCoordinateY()));
                Point transformedEndPoint = transformation.transform(new Point(line.getEndCoordinateX(), line.getEndCoordinateY()));

                line.setStartCoordinates(transformedStartPoint.x, transformedStartPoint.y);
                line.setEndCoordinates(transformedEndPoint.x, transformedEndPoint.y);
            });
        }
    }
}
