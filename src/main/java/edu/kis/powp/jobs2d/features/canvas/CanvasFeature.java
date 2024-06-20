package edu.kis.powp.jobs2d.features.canvas;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.features.CommandsFeature;
import edu.kis.powp.jobs2d.features.DriverFeature;

import javax.swing.*;

public class CanvasFeature {
    private static Application application;

    public static void setupCanvasFeature(Application app) {
        application = app;

        application.addComponentMenu(CanvasFeature.class, "Canvas Settings", 0);

        addCanvasSubMenu(new A4Canvas());
        addCanvasSubMenu(new B3Canvas());
        addCanvasSubMenu(new A6Canvas());
        addCanvasSubMenu(new CircleCanvas());
    }

    private static void addCanvasSubMenu(Canvas canvas) {
        JMenuItem drawItem = new JMenuItem("Draw");
        JMenuItem loadItem = new JMenuItem("Load");
        drawItem.addActionListener(e -> drawCanvas(canvas));
        loadItem.addActionListener(e -> loadCanvas(canvas));

        JMenu canvasMenu = new JMenu(canvas.getName());
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

    public static void loadCanvas(Canvas canvas) {
        CommandManager commandManager = CommandsFeature.getCommandManager();
        commandManager.setCurrentCommand(canvas.getCommands(), "Draw Canvas");
    }

    public static void drawCanvas(Canvas canvas) {
        Job2dDriver driver = DriverFeature.getDriverManager().getCurrentDriver();
        canvas.draw(driver);
    }

    public static class A4Canvas extends Canvas {
        public A4Canvas() {
            super("A4", new RectangleCanvasShape(-100, -120, 210, 297));
        }
    }

    public static class B3Canvas extends Canvas {
        public B3Canvas() {
            super("B3", new RectangleCanvasShape(-180, -130, 375, 263));
        }
    }

    public static class A6Canvas extends Canvas {
        public A6Canvas() {
            super("A6", new RectangleCanvasShape(-50, -50, 105, 74));
        }
    }

    public static class CircleCanvas extends Canvas {
        public CircleCanvas() {
            super("Circle", new CircleCanvasShape(0, 0, 100));
        }
    }
}
