package edu.kis.powp.jobs2d.drivers;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ICompoundCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.events.MouseClickListener;
import edu.kis.powp.jobs2d.features.DriverFeature;

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
            compoundCommand.iterator().forEachRemaining(command->{
                Point point = new Point((command).getX(),(command).getY());
                points.add(point);
                //System.out.println("operate" + point);
            });

            Optional<Point> selectedPoint = points.stream().min(Comparator.comparingDouble(point -> point.distance(position)));

            //selectedPoint.ifPresent(System.out::println);

            if(selectedPoint.isPresent()) {
                System.out.println("found point: " + selectedPoint.get());
                this.selectedCommands = findCommands(selectedPoint.get(), compoundCommand.iterator());
                System.out.println("all commands with the point: "+ selectedCommands);

            }

        } else if (buttonPressed == MOUSE_BUTTON_RIGHT) {
            selectedCommands.forEach(command -> {
                command.setX(position.x);
                command.setY(position.y);
            });
            drawPanelController.clearPanel();
            compoundCommand.execute(driver); //TODO zanim to wykonasz, wyczyść ekran
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
