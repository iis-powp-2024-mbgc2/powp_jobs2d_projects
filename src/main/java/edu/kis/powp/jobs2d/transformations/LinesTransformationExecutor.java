package edu.kis.powp.jobs2d.transformations;

import edu.kis.legacy.drawer.shape.ILine;
import edu.kis.powp.jobs2d.features.LinesRecorder;
import edu.kis.powp.jobs2d.features.DrawerFeature;
import edu.kis.powp.jobs2d.features.WorkspaceTransformationRecorder;

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
        List<ILine> untransformedLines = linesRecorder.getUntransformedLines();

        for (int i = 0; i < lines.size(); i++) {
            ILine line = lines.get(i);
            ILine untransformedLine = untransformedLines.get(i);

            if (transformation instanceof ScaleTransformation) {
                Point startPoint = new Point(untransformedLine.getStartCoordinateX(), untransformedLine.getStartCoordinateY());
                Point endPoint = new Point(untransformedLine.getEndCoordinateX(), untransformedLine.getEndCoordinateY());

                Point transformedStartPoint = transformation.transform(startPoint);
                Point transformedEndPoint = transformation.transform(endPoint);

                Point currentShift = WorkspaceTransformationRecorder.getInstance().getShift();

                transformedStartPoint = new ShiftTransformation(currentShift.x, currentShift.y).transform(transformedStartPoint);
                transformedEndPoint = new ShiftTransformation(currentShift.x, currentShift.y).transform(transformedEndPoint);

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
