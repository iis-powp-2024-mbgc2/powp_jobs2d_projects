package edu.kis.powp.jobs2d.features;

import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.SetPositionCommand;
import edu.kis.powp.jobs2d.command.OperateToCommand;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.jobs2d.Job2dDriver;

import java.util.Arrays;
import java.util.List;

public class CanvasFeature {
    private static Application application;

    public static void setupCanvasFeature(Application app) {
        application = app;

        application.addComponentMenu(CanvasFeature.class, "Canvas Settings");
        application.addComponentMenuElement(CanvasFeature.class, "A4", (e) -> drawCanvasA4());
        application.addComponentMenuElement(CanvasFeature.class, "B3", (e) -> drawCanvasB3());
    }

    public static void drawCanvasA4() {
        drawRectangle(-100, -120,210, 297);
    }

    public static void drawCanvasB3() {
        drawRectangle(-180, -130, 375, 263);
    }

    private static void drawRectangle(int x, int y, int width, int height) {
        List<DriverCommand> commands = Arrays.asList(
                new SetPositionCommand(x, y),
                new OperateToCommand(x + width, y),
                new OperateToCommand(x + width, y + height),
                new OperateToCommand(x, y + height),
                new OperateToCommand(x, y)
        );

        CommandManager commandManager = CommandsFeature.getCommandManager();
        commandManager.setCurrentCommand(commands, "Draw Canvas");

        Job2dDriver driver = DriverFeature.getDriverManager().getCurrentDriver();
        commands.forEach(command -> command.execute(driver));
    }
}