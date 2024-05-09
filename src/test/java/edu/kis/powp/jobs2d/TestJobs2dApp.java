package edu.kis.powp.jobs2d;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.CompoundCommand;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindow;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindowCommandChangeObserver;
import edu.kis.powp.jobs2d.command.loader.LoadCommand;
import edu.kis.powp.jobs2d.drivers.*;
import edu.kis.powp.jobs2d.drivers.LoggerDriver;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.events.*;
import edu.kis.powp.jobs2d.features.CommandsFeature;
import edu.kis.powp.jobs2d.features.DrawerFeature;
import edu.kis.powp.jobs2d.features.DriverFeature;
import edu.kis.powp.jobs2d.features.RecordFeature;

public class TestJobs2dApp {
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    /**
     * Setup test concerning preset figures in context.
     *
     * @param application Application context.
     */
    private static void setupPresetTests(Application application) {
        SelectTestFigureOptionListener selectTestFigureOptionListener = new SelectTestFigureOptionListener(
                DriverFeature.getDriverManager());
        SelectTestFigure2OptionListener selectTestFigure2OptionListener = new SelectTestFigure2OptionListener(
                DriverFeature.getDriverManager());

        application.addTest("Figure Joe 1", selectTestFigureOptionListener);
        application.addTest("Figure Joe 2", selectTestFigure2OptionListener);
    }

    /**
     * Setup test using driver commands in context.
     *
     * @param application Application context.
     */
    private static void setupCommandTests(Application application) {
        application.addTest("Load Compound Rectangle command", new SelectLoadCompoundRectangleCommandOptionListener());

        application.addTest("Load secret command", new SelectLoadSecretCommandOptionListener());

        application.addTest("Load recorded command", new SelectLoadRecordedCommandOptionListener());

        application.addTest("Run command", new SelectRunCurrentCommandOptionListener(DriverFeature.getDriverManager()));

    }
  
    private static void setupVisitorTest(Application application) {
        application.addTest("Show current command stats", new VisitorTest());
    }

    /**
     * Setup driver manager, and set default Job2dDriver for application.
     *
     * @param application Application context.
     */
    private static void setupDrivers(Application application) {
        Job2dDriver loggerDriver = new LoggerDriver(false);
        DriverFeature.addDriver("Simple Logger driver", loggerDriver);

        Job2dDriver loggerDriver2 = new LoggerDriver(true);
        DriverFeature.addDriver("Detailed Logger driver", loggerDriver2);

        DrawPanelController drawerController = DrawerFeature.getDrawerController();
        Job2dDriver driver = new RecordingDriverDecorator(new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"));
        DriverFeature.addDriver("Line Simulator with Recording Support", driver);
        DriverFeature.getDriverManager().setCurrentDriver(driver);

        driver = new RecordingDriverDecorator(new LineDriverAdapter(drawerController, LineFactory.getSpecialLine(), "special"));
        DriverFeature.addDriver("Special Line Simulator with Recording Support", driver);
        driver = new LineDriverAdapter(drawerController, LineFactory.getSpecialLine(), "special");
        DriverFeature.addDriver("Special line Simulator", driver);

        driver = new LoggerDriver(false);
        UsageMonitorDriverDecorator usageMonitorDriver = new UsageMonitorDriverDecorator(driver);
        DriverFeature.addDriver("Usage monitor with logger", usageMonitorDriver);

        driver = new LineDriverAdapter(drawerController, LineFactory.getSpecialLine(), "special");
        UsageMonitorDriverDecorator usageMonitorDriver2 = new UsageMonitorDriverDecorator(driver);
        DriverFeature.addDriver("Special line Simulator with usage monitor", usageMonitorDriver2);

        DriverFeature.updateDriverInfo();

        DriversComposite driversComposite = new DriversComposite();
        driversComposite.addDriver(new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"));
        driversComposite.addDriver(new LoggerDriver(true));
        DriverFeature.addDriver("BasicLine with Logger", driversComposite);
    }

    private static void setupWindows(Application application) {

        CommandManagerWindow commandManager = new CommandManagerWindow(CommandsFeature.getCommandManager());
        application.addWindowComponent("Command Manager", commandManager);

        CommandManagerWindowCommandChangeObserver windowObserver = new CommandManagerWindowCommandChangeObserver(
                commandManager);
        CommandsFeature.getCommandManager().getChangePublisher().addSubscriber(windowObserver);
    }

    /**
     * Setup menu for adjusting logging settings.
     *
     * @param application Application context.
     */
    private static void setupLogger(Application application) {

        application.addComponentMenu(Logger.class, "Logger", 0);
        application.addComponentMenuElement(Logger.class, "Clear log",
                (ActionEvent e) -> application.flushLoggerOutput());
        application.addComponentMenuElement(Logger.class, "Fine level", (ActionEvent e) -> logger.setLevel(Level.FINE));
        application.addComponentMenuElement(Logger.class, "Info level", (ActionEvent e) -> logger.setLevel(Level.INFO));
        application.addComponentMenuElement(Logger.class, "Warning level",
                (ActionEvent e) -> logger.setLevel(Level.WARNING));
        application.addComponentMenuElement(Logger.class, "Severe level",
                (ActionEvent e) -> logger.setLevel(Level.SEVERE));
        application.addComponentMenuElement(Logger.class, "OFF logging", (ActionEvent e) -> logger.setLevel(Level.OFF));
    }

    private static void loadCommand(Application application) {
        CompoundCommand command = LoadCommand.loadCommandsFromFile("./Rectangle.txt");

        application.addTest("./Rectangle.txt", e -> command.execute(DriverFeature.getDriverManager().getCurrentDriver()));
    }

    private static void setupMouseHandler(Application application) {
        new MouseClickConverter(application.getFreePanel());
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Application app = new Application("Jobs 2D");
                DrawerFeature.setupDrawerPlugin(app);
                CommandsFeature.setupCommandManager();
                RecordFeature.setupRecorderPlugin(app);
                DriverFeature.setupDriverPlugin(app);
                setupDrivers(app);
                setupPresetTests(app);
                setupCommandTests(app);
                setupVisitorTest(app);
                setupLogger(app);
                setupWindows(app);
                setupMouseHandler(app);
                loadCommand(app);

                app.setVisibility(true);
            }
        });
    }

}
