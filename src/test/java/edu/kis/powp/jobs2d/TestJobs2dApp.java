package edu.kis.powp.jobs2d;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.ImporterFactory;
import edu.kis.powp.jobs2d.command.JsonCommandImporter;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindow;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindowCommandChangeObserver;
import edu.kis.powp.jobs2d.drivers.DriversComposite;
import edu.kis.powp.jobs2d.drivers.LoggerDriver;
import edu.kis.powp.jobs2d.drivers.RecordingDriverDecorator;
import edu.kis.powp.jobs2d.drivers.UsageMonitor.UsageMonitorFeature;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.drivers.transformators.FlippingDriverDecorator;
import edu.kis.powp.jobs2d.drivers.transformators.RotatingDriverDecorator;
import edu.kis.powp.jobs2d.drivers.transformators.ScalingDriverDecorator;
import edu.kis.powp.jobs2d.drivers.transformators.ShiftingDriverDecorator;
import edu.kis.powp.jobs2d.events.*;
import edu.kis.powp.jobs2d.features.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

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

        DriversComposite driversComposite = new DriversComposite();
        driversComposite.addDriver(new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"));
        driversComposite.addDriver(new LoggerDriver(true));
        DriverFeature.addDriver("BasicLine with Logger", driversComposite);

        Job2dDriver lineShiftAndFlip = FlippingDriverDecorator.getFlipVerticalDecorator(new ShiftingDriverDecorator(new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"), 50, -20));
        DriverFeature.addDriver("Line Shift and Flip", lineShiftAndFlip);

        Job2dDriver lineShiftAndRotate = RotatingDriverDecorator.getRotating90DegClockwiseDecorator(new ScalingDriverDecorator(new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"), 0.5F));
        DriverFeature.addDriver("Line Scale and Rotate", lineShiftAndRotate);

        // Decorators in that order will cause the object first to flip horizontally, then rotate 90 deg clockwise, and then scale 1.5
        Job2dDriver lineShiftAndRotateAndFlip = FlippingDriverDecorator.getFlipHorizontalDecorator(RotatingDriverDecorator.getRotating90DegClockwiseDecorator(new ScalingDriverDecorator(new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"), 1.5F)));
        DriverFeature.addDriver("Line Flip, Rotate and Scale", lineShiftAndRotateAndFlip);

        DriverFeature.updateDriverInfo();
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


    private static void setupImporters() {
        ImporterFactory.addImporter("json", new JsonCommandImporter());
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
                UsageMonitorFeature.setupUsageMonitorPlugin(app);
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
