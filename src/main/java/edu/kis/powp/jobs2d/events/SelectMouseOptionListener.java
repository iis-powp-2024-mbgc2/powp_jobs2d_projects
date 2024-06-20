package edu.kis.powp.jobs2d.events;

import edu.kis.powp.jobs2d.enums.MouseOption;
import edu.kis.powp.jobs2d.features.MouseSettingsFeature;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SelectMouseOptionListener implements ActionListener {

    private final MouseOption option;

    public SelectMouseOptionListener(MouseOption option) {
        this.option = option;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (option) {
            case LEFT_ONLY:
                MouseSettingsFeature.setMouseClickConverterLeftOnly();
                break;
            case LEFT_AND_RIGHT:
                MouseSettingsFeature.setMouseClickConverterLeftAndRight();
                break;
        }
    }
}