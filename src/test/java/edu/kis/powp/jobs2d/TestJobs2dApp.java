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
import edu.kis.powp.jobs2d.canvas.CanvasA3;
import edu.kis.powp.jobs2d.canvas.CanvasA4;
import edu.kis.powp.jobs2d.canvas.CanvasCircle;
import edu.kis.powp.jobs2d.canvas.ExceedingCanvasCheckVisitor;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindow;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindowCommandChangeObserver;
import edu.kis.powp.jobs2d.command.importer.TxtCommandImporter;
import edu.kis.powp.jobs2d.drivers.*;
import edu.kis.powp.jobs2d.drivers.LoggerDriver;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.jobs2d.enums.Command;
import edu.kis.powp.jobs2d.drivers.transformators.TransformingJob2dDriverDecorator;
import edu.kis.powp.jobs2d.transformations.*;
import edu.kis.powp.jobs2d.events.*;
import edu.kis.powp.jobs2d.features.*;


public class TestJobs2dApp {
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

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
        CommandsFeature.addCommand("Flip command ↔ horizontally", new CommandHorizontalFlipTest());
        CommandsFeature.addCommand("Flip command ↕ vertically", new CommandVerticalFlipTest());
        CommandsFeature.addCommand("Scale command (scale = 2)", new CommandScaleTest(2));
        CommandsFeature.addCommand("Rotate command (degrees = 15)", new CommandRotateTest(15));
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

        driver = new RealTimeDecoratorDriver(new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"), application.getFreePanel());
        DriverFeature.addDriver("Basic line Simulator with real time drawing", driver);
        driver = new RealTimeDecoratorDriver(new LineDriverAdapter(drawerController, LineFactory.getSpecialLine(), "special"), application.getFreePanel());
        DriverFeature.addDriver("Special line Simulator with real time drawing", driver);

        DriverFeature.updateDriverInfo();

        DriversComposite driversComposite = new DriversComposite();
        driversComposite.addDriver(new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"));
        driversComposite.addDriver(new LoggerDriver(true));

        DriverFeature.addDriver("BasicLine with Logger", driversComposite);

        Job2dDriver lineFlippedDriver = new TransformingJob2dDriverDecorator(new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"), new VerticalFlipTransformation());
        DriverFeature.addDriver("Line vertical Flip", lineFlippedDriver);

        Job2dDriver lineShiftedDriver = new TransformingJob2dDriverDecorator(new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"), new ShiftTransformation(50, -20));
        Job2dDriver lineShiftedAndFlippedDriver = new TransformingJob2dDriverDecorator(lineShiftedDriver, new HorizontalFlipTransformation());
        DriverFeature.addDriver("Line Shift (50,-20) and horizontal Flip", lineShiftedAndFlippedDriver);

        Job2dDriver lineScaledDriver = new TransformingJob2dDriverDecorator(new LineDriverAdapter(drawerController, LineFactory.getBasicLine(), "basic"), new ScaleTransformation(1.5));
        Job2dDriver lineScaledAndRotatedDriver = new TransformingJob2dDriverDecorator(lineScaledDriver, new RotateTransformation(90));
        DriverFeature.addDriver("Line Scale 1.5 and Rotate 90deg", lineScaledAndRotatedDriver);

        Job2dDriver canvasAwareDriver = new CanvasAwareDriver(drawerController, LineFactory.getBasicLine());
        DriverFeature.addDriver("Canvas aware driver", canvasAwareDriver);

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

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                Application app = new Application("Jobs 2D");
                DrawerFeature.setupDrawerPlugin(app);
                CommandsFeature.setupCommandManager();
                CommandsFeature.setupPresetCommands(app);
                RecordFeature.setupRecorderPlugin(app);
                DriverFeature.setupDriverPlugin(app);
                MouseSettingsFeature.setupMouseSettingsFeature(app);
                CanvasFeature.setupCanvas(app);
                HistoryFeature.setupHistory(app);
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
