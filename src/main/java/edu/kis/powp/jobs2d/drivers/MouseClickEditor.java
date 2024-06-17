package edu.kis.powp.jobs2d.drivers;

import edu.kis.legacy.drawer.panel.DefaultDrawerFrame;
import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.ILine;
import edu.kis.legacy.drawer.shape.line.AbstractLine;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.*;
import edu.kis.powp.jobs2d.events.MouseClickListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class MouseClickEditor extends MouseClickConverter implements MouseClickListener {
    private Job2dDriver driver;
    private final CompoundCommand compoundCommand;
    private DriverCommand selectedPoint = null;
    private final DrawPanelController drawPanelController;
    private final DefaultDrawerFrame commandPreviewPanel;

    public void setDriver(Job2dDriver driver) {
        this.driver = driver;
    }

    public MouseClickEditor(JPanel drawArea, CompoundCommand compoundCommand, Job2dDriver driver, DrawPanelController drawPanelController, DefaultDrawerFrame commandPreviewPanel) {
        super(drawArea);
        this.compoundCommand = compoundCommand;
        this.driver = driver;
        this.drawPanelController = drawPanelController;
        this.commandPreviewPanel = commandPreviewPanel;
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        int buttonPressed = event.getButton();
        Point position = getClickPosition(event);

        if (buttonPressed == MOUSE_BUTTON_LEFT)
            movePoint(position);

    }
    @Override
    public void mousePressed(MouseEvent event) {
        int buttonPressed = event.getButton();
        Point position = getClickPosition(event);
        switch (buttonPressed) {
            case MOUSE_BUTTON_LEFT:
                selectPoint(position);
                break;
            case MOUSE_BUTTON_MID:
                unselectPoint();
                break;
            case MOUSE_BUTTON_RIGHT:
                selectPoint(position);
                showContextMenu(event.getPoint(), position);
                break;
        }
    }

    private void unselectPoint() {
        selectedPoint = null;
        refreshScreen();
    }

    private List<Point> commandToPoints() {
        List<Point> points = new ArrayList<>();
        compoundCommand.iterator().forEachRemaining(command -> {
            Point point = new Point((command).getX(), (command).getY());
            points.add(point);
        });
        return points;
    }

    @Override
    public void handleDriver(Point position, int buttonPressed) {}

    private void showContextMenu(Point contextMenuPosition , Point position) {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem deleteItem = new JMenuItem("Delete Point");
        JMenuItem addItem = new JMenuItem("Add Point");
        JMenuItem changeToSetPositionItem = new JMenuItem("Detach Point");


        deleteItem.addActionListener(e -> deletePoint());
        addItem.addActionListener(e -> addPoint(position));
        changeToSetPositionItem.addActionListener(e -> changeToSetPosition());

        if (selectedPoint == null) {
            deleteItem.setEnabled(false);
            changeToSetPositionItem.setEnabled(false);

        }else{
            if (!(selectedPoint instanceof OperateToCommand))
                changeToSetPositionItem.setEnabled(false);
        }


        popupMenu.add(deleteItem);
        popupMenu.add(addItem);
        popupMenu.add(changeToSetPositionItem);

        popupMenu.show(commandPreviewPanel.getDrawArea(), contextMenuPosition.x, contextMenuPosition.y);
        popupMenu.setVisible(true);

        System.out.println("Right button clicked");
    }

    private void deletePoint() {
        if (selectedPoint == null)
            return;
        compoundCommand.removeCommand(selectedPoint);
        selectedPoint = null;
        refreshScreen();
    }

    private void refreshScreen() {
        drawPanelController.clearPanel();
        compoundCommand.execute(driver);
        showSelectedPoint();
        showCommands();
    }

    private void showSelectedPoint() {
        if (selectedPoint == null)
            return;
        Point position = selectedPoint.getPoint();
        ILine line = new SelectedPointLine();
        line.setStartCoordinates(position.x, position.y);
        line.setEndCoordinates(position.x, position.y);
        commandToPoints().forEach(point -> drawPanelController.drawLine(line));
    }

    private void showCommands() {
        compoundCommand.iterator().forEachRemaining(command -> {

            Point point = new Point((command).getX(), (command).getY());
            ILine line;
            if (command instanceof SetPositionCommand) line = new SetPositionLine();
            else if (command instanceof OperateToCommand) line = new OperateToLine();
            else return;
            line.setStartCoordinates(point.x, point.y);
            line.setEndCoordinates(point.x, point.y);
            drawPanelController.drawLine(line);
        });
    }

    private void selectPoint(Point position) {
        List<Point> points = commandToPoints();
        Optional<Point> closestPoint = points.stream().min(Comparator.comparingDouble(point -> point.distance(position)));
        if (closestPoint.isPresent() && closestPoint.get().distance(position) < 20) {
            this.selectedPoint = findCommand(closestPoint.get(), compoundCommand.iterator());
        } else {
            unselectPoint();
        }
        refreshScreen();
    }

    private void changeToSetPosition() {
        if (selectedPoint instanceof SetPositionCommand)
            return;
        int index = compoundCommand.getCommands().indexOf(selectedPoint);
        SetPositionCommand setPositionCommand = new SetPositionCommand(selectedPoint.getX(), selectedPoint.getY());
        compoundCommand.addCommand(setPositionCommand, index);
        compoundCommand.removeCommand(selectedPoint);
        selectedPoint = setPositionCommand;
        refreshScreen();
    }

    private void selectPoint(DriverCommand command) {
        this.selectedPoint = command;
        refreshScreen();
    }


    private void addPoint(Point position) {
        int index;
        if (selectedPoint == null) {
            index = compoundCommand.getCommands().toArray().length;
            SetPositionCommand setPositionCommand = new SetPositionCommand(position.x, position.y);
            compoundCommand.addCommand(setPositionCommand, index);
            index++;
        }
        else {
            index = compoundCommand.getCommands().indexOf(selectedPoint) + 1;
        }
        System.out.println("Index: " + index);

        OperateToCommand operateToCommand = new OperateToCommand(position.x, position.y);

        selectPoint(operateToCommand);
        compoundCommand.addCommand(operateToCommand, index);
        refreshScreen();
    }



    private void movePoint(Point position) {
        if (selectedPoint == null)
            return;
        selectedPoint.setX(position.x);
        selectedPoint.setY(position.y);
        refreshScreen();
    }

    private DriverCommand findCommand(Point point, Iterator<DriverCommand> iterator) {
        while (iterator.hasNext()) {
            DriverCommand command = iterator.next();
            if (command.getX() == point.x && command.getY() == point.y)
                return command;
        }
        return null;
    }

    private static class SelectedPointLine extends AbstractLine {
        public SelectedPointLine() {
            super();
            this.color = Color.RED;
            this.thickness = 10.0F;
        }
    }
    private static class SetPositionLine extends AbstractLine {
        public SetPositionLine() {
            super();
            this.color = Color.MAGENTA;
            this.thickness = 6.0F;
        }
    }
    private static class OperateToLine extends AbstractLine {
        public OperateToLine() {
            super();
            this.color = Color.BLUE;
            this.thickness = 6.0F;
        }
    }
}
