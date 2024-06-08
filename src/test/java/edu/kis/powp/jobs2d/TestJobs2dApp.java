package edu.kis.powp.jobs2d;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.ImporterFactory;
import edu.kis.powp.jobs2d.command.JsonCommandImporter;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindow;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindowCommandChangeObserver;
import edu.kis.powp.jobs2d.drivers.*;
import edu.kis.powp.jobs2d.drivers.LoggerDriver;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.drivers.transformators.TransformingJob2dDriver;
import edu.kis.powp.jobs2d.transformations.*;
import edu.kis.powp.jobs2d.events.*;
import edu.kis.powp.jobs2d.features.*;

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

        application.addTest("Load deeply complex command", new SelectLoadDeeplyComplexCommandOptionListener());

        application.addTest("Run command", new SelectRunCurrentCommandOptionListener(DriverFeature.getDriverManager()));

    }

    private static void setupVisitorTests(Application application) {
        application.addTest("Show current command stats", new VisitorTest());
        application.addTest("Save deep copy of loaded command", new DeepCopyVisitorSaveTest());
        application.addTest("Load deep copy of saved command", new DeepCopyVisitorTest());
    }

    private static void setupCommandTransformationVisitorTests(Application application) {
        application.addTest("Flip command ↔ horizontally", new CommandHorizontalFlipTest());
        application.addTest("Flip command ↕ vertically", new CommandVerticalFlipTest());
        application.addTest("Scale command (scale = 2)", new CommandScaleTest(2));
        application.addTest("Rotate command (degrees = 15)", new CommandRotateTest(15));
    }

    /**
     * Setup driver manager, and set default Job2dDriver for application.
     *
     * @param application Application context.
     */
    private static void setupDrivers(Application application) {
        DrawPanelController drawerController = DrawerFeature.getDrawerController();
        DriverFeature.addDriver("Basic line driver", new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"));
        DriverFeature.addDriver("Special line driver", new LineDriverAdapter(drawerController, LineFactory.getSpecialLine(), "special"));

        DriverFeature.updateDriverInfo();
    }

    private static void setupWindows(Application application) {

        CommandManagerWindow commandManager = new CommandManagerWindow(CommandsFeature.getCommandManager(), DriverFeature.getDriverManager());
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


    private static void setupImporters() {
        ImporterFactory.addImporter("json", new JsonCommandImporter());
    }

    private static void setupFeaturesMenu(Application application) {
        Job2dDriver loggerDriver = new LoggerDriver(false);
        AppFeature.addFeature("Simple logger", loggerDriver);

        Job2dDriver loggerDriver2 = new LoggerDriver(true);
        AppFeature.addFeature("Detailed logger", loggerDriver2);

        Job2dDriver recordingDriver = new RecordingDriverDecorator(DriverFeature.getDriverManager());
        AppFeature.addFeature("Recording support", recordingDriver);

        Job2dDriver usageMonitorDriver = new UsageMonitorDriverDecorator(DriverFeature.getDriverManager());
        AppFeature.addFeature("Usage monitor", usageMonitorDriver);

        Job2dDriver realTimeDriver = new RealTimeDecoratorDriver(DriverFeature.getDriverManager(), application.getFreePanel());
        AppFeature.addFeature("Real time drawer", realTimeDriver);

        Job2dDriver lineFlippedDriver = new TransformingJob2dDriver(new VerticalFlipTransformation());
        AppFeature.addFeature("Vertical flip", lineFlippedDriver);

        Job2dDriver shiftTransformation = new TransformingJob2dDriver(new ShiftTransformation(50, 50));
        AppFeature.addFeature("Shift by (50, 50)", shiftTransformation);

        Job2dDriver scaleTransformation = new TransformingJob2dDriver(new ScaleTransformation(1.5));
        AppFeature.addFeature("Scale by 1.5", scaleTransformation);

        Job2dDriver rotateTransformation = new TransformingJob2dDriver(new RotateTransformation(90));
        AppFeature.addFeature("Rotate by 90deg", rotateTransformation);

        Job2dDriver horizontalFlipTransformation = new TransformingJob2dDriver(new HorizontalFlipTransformation());
        AppFeature.addFeature("Horizontal flip", horizontalFlipTransformation);
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
                AppFeature.setupFeaturesMenu(app);
                setupFeaturesMenu(app);
                MouseSettingsFeature.setupMouseSettingsFeature(app);
                setupDrivers(app);
                setupPresetTests(app);
                setupCommandTests(app);
                setupVisitorTests(app);
                setupCommandTransformationVisitorTests(app);
                setupLogger(app);
                setupWindows(app);
                setupImporters();
                app.setVisibility(true);
            }
        });
    }

}