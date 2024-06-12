package edu.kis.powp.jobs2d.drivers;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.command.builder.CompoundCommandBuilder;
import edu.kis.powp.jobs2d.events.MouseClickListener;
import edu.kis.powp.jobs2d.features.DriverFeature;

import javax.annotation.processing.SupportedSourceVersion;
import javax.swing.*;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class MouseClickEditor extends MouseClickConverter implements MouseClickListener {
    private Job2dDriver driver;
    private ICompoundCommand compoundCommand;
    private List<DriverCommand> selectedCommands;
    private DrawPanelController drawPanelController;

    public MouseClickEditor(JPanel drawArea, ICompoundCommand compoundCommand, Job2dDriver driver, DrawPanelController drawPanelController) {
        super(drawArea);
        this.compoundCommand = compoundCommand;
        this.driver = driver;
        this.drawPanelController = drawPanelController;
    }

    @Override
    public void handleDriver(Point position, int buttonPressed) {
        List<Point> points = new ArrayList<>();
        compoundCommand.iterator().forEachRemaining(command -> {
            Point point = new Point((command).getX(), (command).getY());
            points.add(point);
        });
        if(buttonPressed == MOUSE_BUTTON_LEFT) {
            Optional<Point> selectedPoint = points.stream().min(Comparator.comparingDouble(point -> point.distance(position)));

            selectedPoint.ifPresent(point -> this.selectedCommands = findCommands(point, compoundCommand.iterator()));
        } else if (buttonPressed == MOUSE_BUTTON_MID){
            CompoundCommandBuilder commandBuilder = new CompoundCommandBuilder();

            Optional<Point> nearest = points.stream().min(Comparator.comparingDouble(point -> point.distance(position)));
            if (nearest.isPresent()) {
                SetPositionCommand setPosition = new SetPositionCommand(position.x, position.y);
                OperateToCommand operateTo = new OperateToCommand(position.x, position.y);
                this.selectedCommands = findCommands(nearest.get(), compoundCommand.iterator());
                List<DriverCommand> commands = new ArrayList<>();
                Iterator<DriverCommand> iterator = compoundCommand.iterator();
                int index = 0;
                boolean found = false;
                while (iterator.hasNext()) {
                    DriverCommand command = iterator.next();
                    commands.add(command);
                    if (!found && command.getX() == nearest.get().x && command.getY() == nearest.get().y) {
                        found = true;
                        index = commands.size();
                    }
                }
                if (nearest.get().distance(position) > 100) {
                    commands.add(setPosition);
                    commands.add(operateTo);
                }
                else if (found) {
                    commands.add(index, operateTo);
                } else {
                    commands.add(operateTo);
                }
                commands.forEach(commandBuilder::addCommand);
                drawPanelController.clearPanel();
                compoundCommand = commandBuilder.build();
                compoundCommand.execute(driver);
            }
        } else if (buttonPressed == MOUSE_BUTTON_RIGHT) {
            selectedCommands.forEach(command -> {
                command.setX(position.x);
                command.setY(position.y);
            });
            drawPanelController.clearPanel();
            compoundCommand.execute(driver);
        }
    }

    private List<DriverCommand> findCommands(Point point, Iterator<DriverCommand> iterator) {
        List<DriverCommand> commands = new ArrayList<>();
        while (iterator.hasNext()) {
            DriverCommand command = iterator.next();
            if (command.getX() == point.x && command.getY() == point.y) {
                commands.add(command);
            }
        }
        return commands;
    }

}
