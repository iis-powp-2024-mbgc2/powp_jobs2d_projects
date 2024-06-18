package edu.kis.powp.jobs2d.command.gui;

import edu.kis.legacy.drawer.panel.DefaultDrawerFrame;
import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.ILine;
import edu.kis.legacy.drawer.shape.line.AbstractLine;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.*;
import edu.kis.powp.jobs2d.command.builder.CommandEditorBuilder;
import edu.kis.powp.jobs2d.command.memento.*;
import edu.kis.powp.jobs2d.drivers.MouseClickConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.*;
import java.util.List;

public class CommandEditor extends MouseClickConverter {
    private Job2dDriver driver;
    private CompoundCommand compoundCommand;
    private DriverCommand selectedCommand = null;
    private final DrawPanelController drawPanelController;
    private final DefaultDrawerFrame commandPreviewPanel;
    private EditHistory history;

    public void setDriver(Job2dDriver driver) {
        this.driver = driver;
    }

    public CommandEditor(CommandEditorBuilder builder) {
        super(builder.drawArea);
        this.compoundCommand = builder.compoundCommand;
        this.driver = builder.driver;
        this.drawPanelController = builder.drawPanelController;
        this.commandPreviewPanel = builder.commandPreviewPanel;
        this.history = new EditHistory(builder.commandManagerWindow);
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
                boolean succeed = selectPointNearby(position);
                if (!succeed) unselectPoint();
                break;
            case MOUSE_BUTTON_MID:
                unselectPoint();
                break;
            case MOUSE_BUTTON_RIGHT:
                selectPointNearby(position);
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
    }

    private void deleteSelectedCommand() {
        if (selectedCommand == null)
            return;
        execute(new DeleteCommand(compoundCommand, selectedCommand));
        selectedCommand = null;
        refreshScreen();
    }

    private void refreshScreen() {
        drawPanelController.clearPanel();
        if (compoundCommand == null)
            return;
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
        drawPanelController.drawLine(line);
    }

    private void showCommands() {
        compoundCommand.iterator().forEachRemaining(command -> {

            Point point = new Point((command).getX(), (command).getY());
            ILine line;
            if (command instanceof SetPositionCommand)
                line = new SetPositionLine();
            else if (command instanceof OperateToCommand)
                line = new OperateToLine();
            else
                return;
            line.setStartCoordinates(point.x, point.y);
            line.setEndCoordinates(point.x, point.y);
            drawPanelController.drawLine(line);
        });
    }

    private boolean selectPointNearby(Point position) {
        List<Point> points = commandToPoints();
        Optional<Point> closestPoint = points.stream().min(Comparator.comparingDouble(point -> point.distance(position)));
        if (closestPoint.isPresent() && closestPoint.get().distance(position) < 20) {
            this.selectedCommand = findCommand(closestPoint.get(), compoundCommand.iterator());
            refreshScreen();
            return true;
        }
        return false;
    }

    private void changeToSetPosition() {
        if (selectedCommand instanceof SetPositionCommand)
            return;
        execute(new DetachCommand(compoundCommand, selectedCommand));
        refreshScreen();
    }


    private void addCommand(Point position) {
        execute(new AddCommand(compoundCommand, selectedCommand, position));
        refreshScreen();
    }



    private void movePoint(Point position) {
        if (selectedCommand == null)
            return;
        execute(new MoveCommand(selectedCommand, position));
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

    public void setCompoundCommand(CompoundCommand currentCommand) {
        this.compoundCommand = currentCommand;
        refreshScreen();
    }

    public void restartMemento() {
        history.clear();
    }

    public EditHistory getHistory() {
        return this.history;
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

    public void execute(EditCommand c) {
        Memento m = new Memento(this);
        history.push(c, m);
        c.execute();
        m.setAfterState();
    }

    public void undo() {
        history.undo();
        unselectPoint();
        refreshScreen();
    }

    public void redo() {
        history.redo();
        unselectPoint();
        refreshScreen();
    }

    public String backup() {
        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream(); ObjectOutputStream objectStream = new ObjectOutputStream(byteStream)) {

            objectStream.writeObject(compoundCommand);
            objectStream.flush();
            byte[] bytes = byteStream.toByteArray();
            return Base64.getEncoder().encodeToString(bytes);

        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void restore(String state) {
        byte[] bytes = Base64.getDecoder().decode(state);
        try (ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes); ObjectInputStream objectStream = new ObjectInputStream(byteStream)) {

            compoundCommand.replace((CompoundCommand) objectStream.readObject());

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        refreshScreen();
    }
}
