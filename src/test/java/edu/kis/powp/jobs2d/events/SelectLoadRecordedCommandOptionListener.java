package edu.kis.powp.jobs2d.events;

import edu.kis.powp.jobs2d.command.manager.ICommandManager;
import edu.kis.powp.jobs2d.features.CommandsFeature;
import edu.kis.powp.jobs2d.features.HistoryFeature;
import edu.kis.powp.jobs2d.features.RecordFeature;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SelectLoadRecordedCommandOptionListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        ICommandManager manager = CommandsFeature.getCommandManager();
        manager.setCurrentCommand(RecordFeature.getRecordedCommand());

        String pattern = "MM/dd/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        String todayAsString = df.format(today);

        HistoryFeature.addToHistory(RecordFeature.getRecordedCommand(), "Recorded command " + todayAsString);
    }
}
