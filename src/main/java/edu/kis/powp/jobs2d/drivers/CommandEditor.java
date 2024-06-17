package edu.kis.powp.jobs2d.drivers;

import edu.kis.legacy.drawer.panel.DefaultDrawerFrame;
import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.ILine;
import edu.kis.legacy.drawer.shape.line.AbstractLine;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

public class CommandEditor extends MouseClickConverter {
    private Job2dDriver driver;
    private CompoundCommand compoundCommand;
    private DriverCommand selectedCommand = null;
    private final DrawPanelController drawPanelController;
    private final DefaultDrawerFrame commandPreviewPanel;

    public void setDriver(Job2dDriver driver) {
        this.driver = driver;
    }

    public CommandEditor(JPanel drawArea, CompoundCommand compoundCommand, Job2dDriver driver, DrawPanelController drawPanelController, DefaultDrawerFrame commandPreviewPanel) {
        super(drawArea);
        this.compoundCommand = compoundCommand;
        this.driver = driver;
        this.drawPanelController = drawPanelController;
        this.commandPreviewPanel = commandPreviewPanel;
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        if (compoundCommand == null)
            return;
        int buttonPressed = event.getButton();
        Point position = getClickPosition(event);

        if (buttonPressed == MOUSE_BUTTON_LEFT)
            movePoint(position);

    }
    @Override
    public void mousePressed(MouseEvent event) {
        if (compoundCommand == null)
            return;
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
        selectedCommand = null;
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


        deleteItem.addActionListener(e -> deleteSelectedCommand());
        addItem.addActionListener(e -> addCommand(position));
        changeToSetPositionItem.addActionListener(e -> changeToSetPosition());

        if (selectedCommand == null) {
            deleteItem.setEnabled(false);
            changeToSetPositionItem.setEnabled(false);

        }else{
            if (!(selectedCommand instanceof OperateToCommand))
                changeToSetPositionItem.setEnabled(false);
        }


        popupMenu.add(deleteItem);
        popupMenu.add(addItem);
        popupMenu.add(changeToSetPositionItem);

        popupMenu.show(commandPreviewPanel.getDrawArea(), contextMenuPosition.x, contextMenuPosition.y);
        popupMenu.setVisible(true);

        System.out.println("Right button clicked");
    }

    private void deleteSelectedCommand() {
        if (selectedCommand == null)
            return;
        compoundCommand.removeCommand(selectedCommand);
        selectedCommand = null;
        refreshScreen();
    }

    private void refreshScreen() {
        drawPanelController.clearPanel();
        compoundCommand.execute(driver);
        showSelectedPoint();
        showCommands();
    }

    private void showSelectedPoint() {
        if (selectedCommand == null)
            return;
        Point position = selectedCommand.getPoint();
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
            this.selectedCommand = findCommand(closestPoint.get(), compoundCommand.iterator());
        } else {
            unselectPoint();
        }
        refreshScreen();
    }

    private void changeToSetPosition() {
        if (selectedCommand instanceof SetPositionCommand)
            return;
        int index = compoundCommand.getCommands().indexOf(selectedCommand);
        SetPositionCommand setPositionCommand = new SetPositionCommand(selectedCommand.getX(), selectedCommand.getY());
        compoundCommand.addCommand(setPositionCommand, index);
        compoundCommand.removeCommand(selectedCommand);
        selectedCommand = setPositionCommand;
        refreshScreen();
    }

    private void selectPoint(DriverCommand command) {
        this.selectedCommand = command;
        refreshScreen();
    }


    private void addCommand(Point position) {
        int index;
        if (selectedCommand == null) {
            index = compoundCommand.getCommands().toArray().length;
            SetPositionCommand setPositionCommand = new SetPositionCommand(position.x, position.y);
            compoundCommand.addCommand(setPositionCommand, index);
            index++;
        }
        else {
            index = compoundCommand.getCommands().indexOf(selectedCommand) + 1;
        }
        System.out.println("Index: " + index);

        OperateToCommand operateToCommand = new OperateToCommand(position.x, position.y);

        selectPoint(operateToCommand);
        compoundCommand.addCommand(operateToCommand, index);
        refreshScreen();
    }



    private void movePoint(Point position) {
        if (selectedCommand == null)
            return;
        selectedCommand.setX(position.x);
        selectedCommand.setY(position.y);
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

    public void clear() {
        selectedCommand = null;
    }

    public void setCompoundCommand(CompoundCommand currentCommand) {
        clear();
        this.compoundCommand = currentCommand;
        refreshScreen();
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
