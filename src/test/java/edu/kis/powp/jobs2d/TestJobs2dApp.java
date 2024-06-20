package edu.kis.powp.jobs2d;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.command.ExceedingCanvasCommandVisitor;
import edu.kis.powp.jobs2d.command.ImporterFactory;
import edu.kis.powp.jobs2d.command.JsonCommandImporter;
import edu.kis.powp.jobs2d.command.gui.A4Canvas;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindow;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindowCommandChangeObserver;
import edu.kis.powp.jobs2d.command.gui.JPanelRectCanvas;
import edu.kis.powp.jobs2d.drivers.LoggerDriver;
import edu.kis.powp.jobs2d.drivers.RecordingDriverDecorator;
import edu.kis.powp.jobs2d.drivers.UsageMonitor.UsageMonitorDriverDecorator;
import edu.kis.powp.jobs2d.drivers.UsageMonitor.UsageMonitorFeature;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.drivers.transformators.FlippingDriverDecoratorTransformation;
import edu.kis.powp.jobs2d.drivers.transformators.RotatingDriverDecoratorTransformation;
import edu.kis.powp.jobs2d.drivers.transformators.ScalingDriverDecoratorTransformation;
import edu.kis.powp.jobs2d.drivers.transformators.ShiftingDriverDecoratorTransformation;
import edu.kis.powp.jobs2d.events.*;
import edu.kis.powp.jobs2d.features.*;
import edu.kis.powp.jobs2d.features.canvas.CanvasFeature;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

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
     */
    private static void setupDrivers() {
        DrawPanelController drawerController = DrawerFeature.getDrawerController();

        Job2dDriver basicLineDriver = new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic");
        Job2dDriver specialLineDriver = new LineDriverAdapter(drawerController, LineFactory.getSpecialLine(), "special");

        DriverFeature.addDriver("Basic Line Driver", basicLineDriver);
        DriverFeature.addDriver("Special Line Driver", specialLineDriver);

        DriverFeature.updateDriverInfo();
    }


    private static void setupExtendedDrivers() {
        ExtendedDriverFeature.addOption("Shift", new ShiftingDriverDecoratorTransformation(50, -20));
        ExtendedDriverFeature.addOption("Flip Horizontal", FlippingDriverDecoratorTransformation.getFlipHorizontalDecorator());
        ExtendedDriverFeature.addOption("Flip Vertical", FlippingDriverDecoratorTransformation.getFlipVerticalDecorator());
        ExtendedDriverFeature.addOption("Rotate", RotatingDriverDecoratorTransformation.getRotating90DegClockwiseDecorator());
        ExtendedDriverFeature.addOption("Scale", new ScalingDriverDecoratorTransformation(1.5F));

        ExtendedDriverFeature.addOption("Usage Monitoring", new UsageMonitorDriverDecorator());
        ExtendedDriverFeature.addOption("Recording Support", new RecordingDriverDecorator());

        ExtendedDriverFeature.addOption("Detailed Logger", new LoggerDriver(true));
        ExtendedDriverFeature.addOption("Simple Logger", new LoggerDriver(false));
    }


    private static void setupWindows(Application application) {

        CommandManagerWindow commandManager = new CommandManagerWindow(CommandsFeature.getCommandManager());
        application.addWindowComponent("Command Manager", commandManager);
        ExceedingCanvasCommandVisitor visitor = new ExceedingCanvasCommandVisitor(commandManager.getDrawPanel());

        CommandManagerWindowCommandChangeObserver windowObserver = new CommandManagerWindowCommandChangeObserver(
                commandManager,visitor);
        CommandsFeature.getCommandManager().getChangePublisher().addSubscriber(windowObserver);
        application.addComponentMenu(Canvas.class,"Canvas");

        application.addComponentMenuElement(Canvas.class, "BasicCanvas", (ActionEvent e) -> {
            commandManager.setDrawPanel(new JPanelRectCanvas());});

        application.addComponentMenuElement(Canvas.class, "A4Canvas", (ActionEvent e) -> {
            commandManager.setDrawPanel(new A4Canvas(300));});

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
        EventQueue.invokeLater(() -> {
            Application app = new Application("Jobs 2D");

            DrawerFeature.setupDrawerPlugin(app);
            CommandsFeature.setupCommandManager();
            RecordFeature.setupRecorderPlugin(app);
            DriverFeature.setupDriverPlugin(app);
            UsageMonitorFeature.setupUsageMonitorPlugin(app);
            MouseSettingsFeature.setupMouseSettingsFeature(app);

            DriverFeature.setupDriverPlugin(app);
            ExtendedDriverFeature.setupExtendedDriverPlugin(app);


            CanvasFeature.setupCanvasFeature(app);



            setupDrivers();
            setupExtendedDrivers();
            setupPresetTests(app);
            setupCommandTests(app);
            setupVisitorTests(app);
            setupCommandTransformationVisitorTests(app);
            setupLogger(app);
            setupWindows(app);
            setupImporters();

            app.setVisibility(true);
        });
    }

}