package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.command.manager.CommandManager;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class CanvasFeature {
    private static Application application;

    public static void setupCanvasFeature(Application app) {
        application = app;

        application.addComponentMenu(CanvasFeature.class, "Canvas Settings", 0);

        addCanvasSubMenu("A4", CanvasFeature::drawCanvasA4, CanvasFeature::loadCanvasA4);
        addCanvasSubMenu("B3", CanvasFeature::drawCanvasB3, CanvasFeature::loadCanvasB3);
    }

    private static void addCanvasSubMenu(String canvasType, Runnable drawCommand, Runnable loadCommand) {
        JMenuItem drawItem = new JMenuItem("Draw");
        JMenuItem loadItem = new JMenuItem("Load");
        drawItem.addActionListener(e -> drawCommand.run());
        loadItem.addActionListener(e -> loadCommand.run());

        JMenu canvasMenu = new JMenu(canvasType);
        canvasMenu.add(drawItem);
        canvasMenu.add(loadItem);

        JMenu mainMenu = findOrCreateMainMenu();
        mainMenu.add(canvasMenu);
    }

    private static JMenu findOrCreateMainMenu() {
        JMenuBar menuBar = application.getFreePanel().getRootPane().getJMenuBar();
        for (int i = 0; i < menuBar.getMenuCount(); i++) {
            JMenu menu = menuBar.getMenu(i);
            if (menu.getText().equals("Canvas Settings")) {
                return menu;
            }
        }
        JMenu mainMenu = new JMenu("Canvas Settings");
        menuBar.add(mainMenu);
        return mainMenu;
    }

    public static void loadCanvasA4() {
        loadCanvas(-100, -120, 210, 297);
    }

    public static void loadCanvasB3() {
        loadCanvas(-180, -130, 375, 263);
    }

    public static void drawCanvasA4() {
        drawCanvas(-100, -120, 210, 297);
    }

    public static void drawCanvasB3() {
        drawCanvas(-180, -130, 375, 263);
    }


    private static void loadCanvas(int x, int y, int width, int height) {
        List commands = Arrays.asList(
                new SetPositionCommand(x, y),
                new OperateToCommand(x + width, y),
                new OperateToCommand(x + width, y + height),
                new OperateToCommand(x, y + height),
                new OperateToCommand(x, y)
        );

        CommandManager commandManager = CommandsFeature.getCommandManager();
        commandManager.setCurrentCommand(commands, "Draw Canvas");
    }

    private static void drawCanvas(int x, int y, int width, int height) {
        Job2dDriver driver = DriverFeature.getDriverManager().getCurrentDriver();

        driver.setPosition(x, y);
        driver.operateTo(x + width, y);
        driver.operateTo(x + width, y + height);
        driver.operateTo(x, y + height);
        driver.operateTo(x, y);
    }
}
