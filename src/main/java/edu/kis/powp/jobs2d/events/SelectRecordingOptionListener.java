package edu.kis.powp.jobs2d.events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.kis.powp.jobs2d.enums.RecordingOption;
import edu.kis.powp.jobs2d.features.RecordFeature;

public class SelectRecordingOptionListener implements ActionListener {

    private final RecordingOption option;

    public SelectRecordingOptionListener(RecordingOption option) {
        this.option = option;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (option){
            case CLEAR: RecordFeature.clear(); break;
            case START: RecordFeature.start();break;
        }
    }
}
