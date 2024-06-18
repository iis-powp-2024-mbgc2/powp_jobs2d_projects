package edu.kis.powp.jobs2d.events;

import edu.kis.powp.jobs2d.features.WorkspaceTransformationFeature;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectWorkspaceTransformationListener implements ActionListener {
    private boolean isTransforming;

    public SelectWorkspaceTransformationListener(boolean isTransforming) {
        this.isTransforming = isTransforming;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isTransforming) {
            WorkspaceTransformationFeature.disableTransformation();
            isTransforming = false;
        } else {
            WorkspaceTransformationFeature.enableTransformation();
            isTransforming = true;
        }
    }
}
