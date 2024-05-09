package edu.kis.powp.jobs2d.command.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.*;

import edu.kis.legacy.drawer.panel.DefaultDrawerFrame;
import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.legacy.drawer.shape.line.BasicLine;
import edu.kis.powp.appbase.gui.WindowComponent;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.CommandImporter;
import edu.kis.powp.jobs2d.command.DriverCommand;
import edu.kis.powp.jobs2d.command.ImporterFactory;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.command.visitor.VisitorStatisticsGenerator;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.observer.Subscriber;

public class CommandManagerWindow extends JFrame implements WindowComponent {
    private CommandManager commandManager;

    private JTextArea currentCommandField;
    private JTextArea statisticCommandField;
    private DefaultDrawerFrame commandPreviewPanel;
    private DrawPanelController drawPanelController;

    private final VisitorStatisticsGenerator visitorStatisticsGenerator = new VisitorStatisticsGenerator();

    private String observerListString;
    private JTextArea observerListField;
    final private Job2dDriver previewLineDriver;

    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    /**
     *
     */
    private static final long serialVersionUID = 9204679248304669948L;

    public CommandManagerWindow(CommandManager commandManager) {
        this.setTitle("Command Manager");
        this.setSize(400, 400);
        Container content = this.getContentPane();
        content.setLayout(new GridBagLayout());

        this.commandManager = commandManager;

        GridBagConstraints c = new GridBagConstraints();

        observerListField = new JTextArea("");
        observerListField.setEditable(false);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.weighty = 1;
        content.add(observerListField, c);
        updateObserverListField();

        currentCommandField = new JTextArea("");
        currentCommandField.setEditable(false);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.weighty = 1;
        content.add(currentCommandField, c);

        statisticCommandField = new JTextArea("");
        statisticCommandField.setEditable(false);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.weighty = 1;
        content.add(statisticCommandField, c);

        commandPreviewPanel = new DefaultDrawerFrame();
        drawPanelController = new DrawPanelController();
        drawPanelController.initialize(commandPreviewPanel.getDrawArea());
        previewLineDriver = new LineDriverAdapter(drawPanelController, new BasicLine(), "preview");
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.weighty = 5;
        JPanel drawArea = commandPreviewPanel.getDrawArea();
        drawArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        content.add(drawArea, c);

        JButton btnImportCommand = new JButton("Import command");
        btnImportCommand.addActionListener((ActionEvent e) -> this.importCommandFromFile());

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.weighty = 1;
        content.add(btnImportCommand, c);

        JButton btnClearCommand = new JButton("Clear command");
        btnClearCommand.addActionListener((ActionEvent e) -> this.clearCommand());
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.weighty = 1;
        content.add(btnClearCommand, c);

        JButton btnClearObservers = new JButton("Delete observers");
        btnClearObservers.addActionListener((ActionEvent e) -> this.deleteObservers());
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.weighty = 1;
        content.add(btnClearObservers, c);
    }

    private void importCommandFromFile() {
        try {
            JFileChooser chooser = new JFileChooser();
            int status = chooser.showOpenDialog(null);
            if (status == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                if (file == null)
                    throw new IllegalArgumentException("Invalid file");

                String filePath = chooser.getSelectedFile().getAbsolutePath();
                String fileExtension = filePath.substring(filePath.lastIndexOf(".") + 1);
                CommandImporter importer = ImporterFactory.getImporter(fileExtension);
                String content = new String(Files.readAllBytes(Paths.get(filePath)));
                DriverCommand command = importer.importCommands(content);
                commandManager.setCurrentCommand(command);
            }
        } catch (Exception e) {
            logger.warning("Error while importing command from file: " + e.getMessage());
        }
    }

    private void clearCommand() {
        commandManager.clearCurrentCommand();
        updateCurrentCommandField();
        updateStatisticCommandField();
    }

    public void updateCurrentCommandField() {
        currentCommandField.setText(commandManager.getCurrentCommandString());

        drawPanelController.clearPanel();
        commandManager.getCurrentCommand().execute(previewLineDriver);
    }

    public void updateStatisticCommandField() {
        statisticCommandField.setText(
                visitorStatisticsGenerator.generateStatistics(commandManager.getCurrentCommand()));
    }

    public void deleteObservers() {
        commandManager.getChangePublisher().clearObservers();
        this.updateObserverListField();
    }

    private void updateObserverListField() {
        observerListString = "";
        List<Subscriber> commandChangeSubscribers = commandManager.getChangePublisher().getSubscribers();
        for (Subscriber observer : commandChangeSubscribers) {
            observerListString += observer.toString() + System.lineSeparator();
        }
        if (commandChangeSubscribers.isEmpty())
            observerListString = "No observers loaded";

        observerListField.setText(observerListString);
    }

    @Override
    public void HideIfVisibleAndShowIfHidden() {
        updateObserverListField();
        if (this.isVisible()) {
            this.setVisible(false);
        } else {
            this.setVisible(true);
        }
    }
}
