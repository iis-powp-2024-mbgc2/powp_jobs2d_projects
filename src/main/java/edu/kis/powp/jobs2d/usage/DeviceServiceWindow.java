package edu.kis.powp.jobs2d.usage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import edu.kis.powp.appbase.gui.WindowComponent;
import edu.kis.powp.jobs2d.enums.DeviceManagementWarnings;

public class DeviceServiceWindow extends JFrame implements WindowComponent, TankObserver {
    private Tank tank;
    private JProgressBar tankFillBar;
    private JLabel warningLabel;
    private JLabel statusLabel;
    private JPanel statusPanel;

    public DeviceServiceWindow(Tank tank) {
        this.tank = tank;

        this.setTitle("DeviceServiceWindow");
        this.setSize(400, 400);
        Container content = this.getContentPane();
        content.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        warningLabel = new JLabel("Warnings");
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 10, 10, 10);
        content.add(warningLabel, c);

        statusPanel = new JPanel();
        statusPanel.setPreferredSize(new Dimension(20, 20));
        updateStatusPanelColor(Color.GREEN);
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(10, 10, 10, 10);
        content.add(statusPanel, c);

        statusLabel = new JLabel("Status: OK");
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0, 10, 10, 10);
        content.add(statusLabel, c);

        tankFillBar = new JProgressBar();
        tankFillBar.setMinimum(0);
        tankFillBar.setMaximum((int) ConfigOfDeviceUsageLoader.TANK_CAPACITY);
        tankFillBar.setStringPainted(true);
        tankFillBar.setValue((int) tank.getCurrentLevel());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        content.add(tankFillBar, c);

        JButton btnRefill = new JButton("Refill");
        btnRefill.addActionListener((ActionEvent e) -> tank.refill());
        btnRefill.setPreferredSize(new Dimension(100, 50));
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(50, 10, 10, 10);
        content.add(btnRefill, c);
    }

    public void updateTankFillBar() {
        int scaledLevel = (int) (tank.getCurrentLevel() * 1.0);
        tankFillBar.setValue(scaledLevel);
        updateStatusPanelColor(tank.determineWarning().getColor());
    }

    private void updateStatusPanelColor(Color color) {
        if (statusPanel != null) {
            statusPanel.setBackground(color);
            statusPanel.repaint();
        }
    }

    @Override
    public void HideIfVisibleAndShowIfHidden() {
        if (this.isVisible()) {
            this.setVisible(false);
        } else {
            this.setVisible(true);
        }
    }

    @Override
    public void update(DeviceManagementWarnings warning) {
        updateStatusLabel(warning);
        updateTankFillBar();
    }

    public void updateStatusLabel(DeviceManagementWarnings warning) {
        String errorCode = warning.name(); 
        statusLabel.setText("Status: " + errorCode);
    }
}
