package edu.kis.powp.jobs2d.transformations.changedriver;

import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.builder.CompoundCommandBuilder;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.RecordingDriverDecorator;

public class RecordCommandsForTransformation {
    private static CompoundCommandBuilder recordCommandBuilder;
    private static DriverManager driverManager = DriverManager.getInstance();
    private static final RecordingDriverDecorator recordingDriverDecorator = new RecordingDriverDecorator(driverManager.getCurrentDriver());
    private static boolean isRecording = false;

    public static void setupRecorder() {
        recordCommandBuilder = new CompoundCommandBuilder().setName("Record Command For Transformation");

        start();

        driverManager.getChangePublisher().addSubscriber(new WrapDriverWithRecordingDecoratorObserverWhileChaningDriver());
    }

    public static void wrapDriverWithRecordingDecorator() {
        recordingDriverDecorator.setDriver(driverManager.getCurrentDriver());
        driverManager.setCurrentDriver(recordingDriverDecorator);
    }

    public static void setCommand(DriverCommand command){
        if(isRecording){
            recordCommandBuilder.addCommand(command);
        }
    }

    public static void start(){
        isRecording = !isRecording;
    }

    public static void clear(){
        recordCommandBuilder = new CompoundCommandBuilder().setName("Record Command For Transformation");
    }

    public static DriverCommand getRecordedCommand() {
        return recordCommandBuilder.build();
    }
}
