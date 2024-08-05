package edu.kis.powp.jobs2d;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.LineFactory;
import edu.kis.powp.appbase.Application;
import edu.kis.powp.jobs2d.canvas.CanvasA3;
import edu.kis.powp.jobs2d.canvas.CanvasA4;
import edu.kis.powp.jobs2d.command.HistoryFeature;
import edu.kis.powp.jobs2d.command.importer.ImporterFactory;
import edu.kis.powp.jobs2d.command.importer.JsonCommandImporter;
import edu.kis.powp.jobs2d.canvas.CanvasCircle;
import edu.kis.powp.jobs2d.canvas.ExceedingCanvasCheckVisitor;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindow;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindowCommandChangeObserver;
import edu.kis.powp.jobs2d.command.importer.TxtCommandImporter;
import edu.kis.powp.jobs2d.drivers.*;
import edu.kis.powp.jobs2d.drivers.LoggerDriver;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapterRecordingDecorator;
import edu.kis.powp.jobs2d.enums.Command;
import edu.kis.powp.jobs2d.drivers.transformators.TransformingJob2dDriverDecorator;
import edu.kis.powp.jobs2d.transformations.*;
import edu.kis.powp.jobs2d.transformations.changedriver.ApplyTransformationToNewDriver;
import edu.kis.powp.jobs2d.usage.ConfigOfDeviceUsageLoader;
import edu.kis.powp.jobs2d.usage.DeviceServiceWindow;
import edu.kis.powp.jobs2d.usage.Tank;
import edu.kis.powp.jobs2d.events.*;
import edu.kis.powp.jobs2d.features.*;


public class TestJobs2dApp {
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static Tank tank;
    /**
     * Setup test concerning canvas.
     *
     * @param application Application context.
     */
    private static void setupPresetCanvas(Application application) {
        CanvasA4 canvasA4 = new CanvasA4();
        CanvasFeature.addCanvas("Canvas A4", canvasA4);

        CanvasA3 canvasA3 = new CanvasA3();
        CanvasFeature.addCanvas("Canvas A3", canvasA3);

        CanvasCircle canvasCircle = new CanvasCircle();
        CanvasFeature.addCanvas("Canvas Circle, Radius " + canvasCircle.getRadius(), canvasCircle);

        CanvasFeature.updateCanvasInfo();
    }




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
    private static void setupCommandListeners(Application application) {
        CommandsFeature.addCommand("Run command", new SelectRunCurrentCommandOptionListener(DriverFeature.getDriverManager()));
        CommandsFeature.addCommand("Load Compound Rectangle command", new SelectCommandListener(Command.RECTANGLE));
        CommandsFeature.addCommand("Load secret command", new SelectCommandListener(Command.SECRET));
        CommandsFeature.addCommand("Load recorded command", new SelectCommandListener(Command.RECORDED));
        CommandsFeature.addCommand("Load deeply complex command", new SelectCommandListener(Command.DEEPLY_COMPLEX));
    }

    private static void setupCommandVisitorTests(Application application) {
        CommandsFeature.addCommand("Load deep copy of saved command", new DeepCopyVisitorTest());
        CommandsFeature.addCommand("Show current command stats", new VisitorTest());
        CommandsFeature.addCommand("Save deep copy of loaded command", new DeepCopyVisitorSaveTest());
    }

    private static void setupCommandTransformationTests(Application application) {
        TransformationsFeature.addCommand("Flip command ↔ horizontally", new CommandHorizontalFlipTest());
        TransformationsFeature.addCommand("Flip command ↕ vertically", new CommandVerticalFlipTest());
        TransformationsFeature.addCommand("Scale command (scale = 2)", new CommandScaleTest(2));
        TransformationsFeature.addCommand("Rotate command (degrees = 15)", new CommandRotateTest(15));
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

        Job2dDriver simpleLoggerDriver = new LoggerDriver(false);
        Job2dDriver detailedLoggerDriver = new LoggerDriver(true);
        LineDriverAdapter basicLineDriver = new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic");
        Job2dDriver specialLineDriver = new LineDriverAdapter(drawerController, LineFactory.getSpecialLine(), "special");

        DriverFeature.addDriver("Simple Logger Driver", simpleLoggerDriver);
        DriverFeature.addDriver("Detailed Logger Driver", detailedLoggerDriver);

        DriverFeature.addDriver("Basic Line Simulator", basicLineDriver);
        DriverFeature.addDriver("Special Line Simulator", specialLineDriver);

        DriversComposite driversComposite = new DriversComposite();
        driversComposite.addDriver(basicLineDriver);
        driversComposite.addDriver(detailedLoggerDriver);
        DriverFeature.addDriver("Basic Line with Detailed Logger", driversComposite);

        driversComposite = new DriversComposite();
        driversComposite.addDriver(specialLineDriver);
        driversComposite.addDriver(detailedLoggerDriver);
        DriverFeature.addDriver("Special Line with Detailed Logger", driversComposite);

        Job2dDriver basicLineWithRecordingSupportDriver = new RecordingDriverDecorator(basicLineDriver);
        DriverFeature.addDriver("Basic Line Simulator with Recording Support", basicLineWithRecordingSupportDriver);

        Job2dDriver specialLineWithRecordingSupportDriver = new RecordingDriverDecorator(specialLineDriver);
        DriverFeature.addDriver("Special Line Simulator with Recording Support", specialLineWithRecordingSupportDriver);

        Job2dDriver simpleLoggerWithUsageMonitorDriver = new UsageMonitorDriverDecorator(simpleLoggerDriver, tank);
        DriverFeature.addDriver("Simple Logger with Usage Monitor", simpleLoggerWithUsageMonitorDriver);

        Job2dDriver basicLineWithUsageMonitorDriver = new UsageMonitorDriverDecorator(basicLineDriver, tank);
        DriverFeature.addDriver("Basic Line Simulator with Usage Monitor", basicLineWithUsageMonitorDriver);

        Job2dDriver specialLineWithUsageMonitorDriver = new UsageMonitorDriverDecorator(specialLineDriver, tank);
        DriverFeature.addDriver("Special Line Simulator with Usage Monitor", specialLineWithUsageMonitorDriver);

        Job2dDriver basicLineWithRealTimeDrawingDriver = new RealTimeDecoratorDriver(basicLineDriver, application.getFreePanel());
        DriverFeature.addDriver("Basic Line Simulator with Real Time Drawing", basicLineWithRealTimeDrawingDriver);

        Job2dDriver specialLineWithRealTimeDrawingDriver = new RealTimeDecoratorDriver(specialLineDriver, application.getFreePanel());
        DriverFeature.addDriver("Special Line Simulator with Real Time Drawing", specialLineWithRealTimeDrawingDriver);

        // Start of transforming
        Job2dDriver basicLineWithVerticalFlipDriver = new TransformingJob2dDriverDecorator(basicLineDriver, new VerticalFlipTransformation());
        DriverFeature.addDriver("Basic Line with Vertical Flip", basicLineWithVerticalFlipDriver);

        driversComposite = new DriversComposite();
        driversComposite.addDriver(basicLineWithVerticalFlipDriver);
        driversComposite.addDriver(detailedLoggerDriver);
        DriverFeature.addDriver("Basic Line with Vertical Flip and Detailed Logger", driversComposite);

        Job2dDriver specialLineWithVerticalFlipDriver = new TransformingJob2dDriverDecorator(specialLineDriver, new VerticalFlipTransformation());
        DriverFeature.addDriver("Special Line with Vertical Flip", specialLineWithVerticalFlipDriver);

        driversComposite = new DriversComposite();
        driversComposite.addDriver(specialLineWithVerticalFlipDriver);
        driversComposite.addDriver(detailedLoggerDriver);
        DriverFeature.addDriver("Special Line with Vertical Flip and Detailed Logger", driversComposite);

        Job2dDriver lineShiftedDriver = new TransformingJob2dDriverDecorator(basicLineDriver, new ShiftTransformation(50, -20));
        Job2dDriver lineShiftedAndFlippedDriver = new TransformingJob2dDriverDecorator(lineShiftedDriver, new HorizontalFlipTransformation());
        DriverFeature.addDriver("Basic Line Shift (50,-20) and Horizontal Flip", lineShiftedAndFlippedDriver);

        driversComposite = new DriversComposite();
        driversComposite.addDriver(lineShiftedAndFlippedDriver);
        driversComposite.addDriver(detailedLoggerDriver);
        DriverFeature.addDriver("Basic Line Shift (50,-20) and Horizontal Flip with Detailed Logger", driversComposite);

        Job2dDriver lineScaledDriver = new TransformingJob2dDriverDecorator(basicLineDriver, new ScaleTransformation(1.5));
        Job2dDriver lineScaledAndRotatedDriver = new TransformingJob2dDriverDecorator(lineScaledDriver, new RotateTransformation(90));
        DriverFeature.addDriver("Basic Line Scale 1.5 and Rotate 90deg", lineScaledAndRotatedDriver);

        driversComposite = new DriversComposite();
        driversComposite.addDriver(lineScaledAndRotatedDriver);
        driversComposite.addDriver(detailedLoggerDriver);
        DriverFeature.addDriver("Basic Line Scale 1.5 and Rotate 90deg with Detailed Logger", driversComposite);

        Job2dDriver canvasAwareDriver = new CanvasAwareDriverDecorator(basicLineDriver);
        DriverFeature.addDriver("Canvas Aware Driver", canvasAwareDriver);

        driversComposite = new DriversComposite();
        driversComposite.addDriver(canvasAwareDriver);
        driversComposite.addDriver(detailedLoggerDriver);
        DriverFeature.addDriver("Canvas Aware Driver with Detailed Logger", driversComposite);

        basicLineDriver = new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic");
        DriverFeature.addDriver("BasicLine with workspace transformation", new LineDriverAdapterRecordingDecorator(basicLineDriver));

        DriverFeature.updateDriverInfo();
    }

    private static void setupWindows(Application application) {

        CommandManagerWindow commandManager = new CommandManagerWindow(CommandsFeature.getCommandManager(), DriverFeature.getDriverManager() );
        application.addWindowComponent("Command Manager", commandManager);
        ExceedingCanvasCheckVisitor visitor = new ExceedingCanvasCheckVisitor(CanvasFeature.getCanvasManager().getCurrentCanvas());

        CommandManagerWindowCommandChangeObserver windowObserver = new CommandManagerWindowCommandChangeObserver(
                commandManager, visitor);
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

    private static void setupMouseHandler(Application application) {
        new MouseClickConverter(application.getFreePanel());
    }

    private static void setupImporters() {
        ImporterFactory.addImporter("json", new JsonCommandImporter());
        ImporterFactory.addImporter("txt", new TxtCommandImporter());
    }

    private static void setupWarningSystem(Application application) {
        tank = new Tank(ConfigOfDeviceUsageLoader.TANK_CAPACITY);

        DeviceServiceWindow deviceServiceWindow = new DeviceServiceWindow(tank);
        application.addWindowComponent("Device Service", deviceServiceWindow);

        tank.addObserver(deviceServiceWindow);
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Application app = new Application("Jobs 2D");
                ConfigOfDeviceUsageLoader.loadConfig("src\\configOfDeviceUsage.xml");

                DrawerFeature.setupDrawerPlugin(app);
                CommandsFeature.setupCommandManager();
                CommandsFeature.setupPresetCommands(app);
                TransformationsFeature.setupPresetCommands(app);
                RecordFeature.setupRecorderPlugin(app);
                DriverFeature.setupDriverPlugin(app);
                MouseSettingsFeature.setupMouseSettingsFeature(app);
                WorkspaceTransformationFeature.setupWorkspaceTransformationFeature(app);
                CanvasFeature.setupCanvas(app);
                HistoryFeature.setupHistory(app);
                ApplyTransformationToNewDriver.setUpTransformationToNewDriver();
                
                setupWarningSystem(app);
                setupDrivers(app);
                setupCommandListeners(app);
                setupCommandVisitorTests(app);
                setupCommandTransformationTests(app);
                setupLogger(app);
                setupWindows(app);
                setupMouseHandler(app);
                setupPresetCanvas(app);
                setupImporters();
                setupPresetTests(app);

                app.setVisibility(true);
            }
        });
    }

}
