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

        if(buttonPressed == MOUSE_BUTTON_LEFT) {

            List<Point> points = new ArrayList<>();
            compoundCommand.iterator().forEachRemaining(command -> {
                Point point = new Point((command).getX(), (command).getY());
                points.add(point);
                //System.out.println("operate" + point);
            });

            Optional<Point> selectedPoint = points.stream().min(Comparator.comparingDouble(point -> point.distance(position)));

            //selectedPoint.ifPresent(System.out::println);

            if (selectedPoint.isPresent()) {
                System.out.println("found point: " + selectedPoint.get());
                this.selectedCommands = findCommands(selectedPoint.get(), compoundCommand.iterator());
                System.out.println("all commands with the point: " + selectedCommands);

            }
        } else if (buttonPressed == MOUSE_BUTTON_MID){
            CompoundCommandBuilder commandBuilder = new CompoundCommandBuilder();
            System.out.println("Position: " + position);
            List<Point> points = new ArrayList<>();
            compoundCommand.iterator().forEachRemaining(command -> {
                Point point = new Point((command).getX(), (command).getY());
                points.add(point);
            });
            System.out.println(points);
            Optional<Point> nearest = points.stream().min(Comparator.comparingDouble(point -> point.distance(position)));
            System.out.println(nearest);
            System.out.println(nearest);
            if (nearest.isPresent()) {
                SetPositionCommand setPosition = new SetPositionCommand(position.x, position.y);
                OperateToCommand operateTo = new OperateToCommand(position.x, position.y);
                this.selectedCommands = findCommands(nearest.get(), compoundCommand.iterator());
                List<DriverCommand> commands = new ArrayList<>();
                Iterator<DriverCommand> iterator = compoundCommand.iterator();
                System.out.println(selectedCommands);
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
                if (found) {
                    commands.add(index, operateTo);
                } else {
                    commands.add(operateTo);
                }
                commands.forEach(command -> {
                    commandBuilder.addCommand(command);
                });
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
