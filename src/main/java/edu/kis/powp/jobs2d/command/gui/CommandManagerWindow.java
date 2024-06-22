package edu.kis.powp.jobs2d.command.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
import edu.kis.powp.jobs2d.command.manager.ICommandManager;
import edu.kis.powp.jobs2d.drivers.DriverManager;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.observer.Subscriber;
import edu.kis.powp.jobs2d.command.factory.CommandFactory;

public class CommandManagerWindow extends JFrame implements WindowComponent {
    private ICommandManager commandManager;

    private JTextArea currentCommandField;
    private DefaultDrawerFrame commandPreviewPanel;
    private DrawPanelController drawPanelController;

    private String observerListString;
    private JTextArea observerListField;

    private DriverManager driverManager;
    final private Job2dDriver previewLineDriver;

    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private DefaultListModel commandFactory;

    /**
     *
     */
    private static final long serialVersionUID = 9204679248304669948L;

    public CommandManagerWindow(ICommandManager commandManager, DriverManager driverManager1) {
        this.setTitle("Command Manager");
        this.setSize(400, 800);
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

        this.driverManager = driverManager1;
        driverManager.setCurrentDriver(new LineDriverAdapter(drawPanelController,new BasicLine(),"preview"));
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

        JButton btnRunCommand = new JButton("Run command");
        btnRunCommand.addActionListener((ActionEvent e) -> this.runCommand());
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.weighty = 1;
        content.add(btnRunCommand, c);

        JButton btnClearCommand = new JButton("Clear command");
        btnClearCommand.addActionListener((ActionEvent e) -> this.clearCommand());
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.weighty = 1;
        content.add(btnClearCommand, c);

        JButton btnResetObservers = new JButton("Reset observers");
        btnResetObservers.addActionListener((ActionEvent e) -> this.resetObservers());
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.weighty = 1;
        content.add(btnResetObservers, c);

        JButton btnClearObservers = new JButton("Delete observers");
        btnClearObservers.addActionListener((ActionEvent e) -> this.deleteObservers());
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.weighty = 1;
        content.add(btnClearObservers, c);

        setupCommandFactoryView(content, c);
    }

    private void setupCommandFactoryView(Container content, GridBagConstraints c) {
        List<Component> components = new ArrayList<>();

        JLabel titleLabel = new JLabel("Commands Factory:");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        components.add(titleLabel);

        commandFactory = new DefaultListModel();
        JList commandFactoryList = new JList(commandFactory);
        JScrollPane scrollPane = new JScrollPane(commandFactoryList);
        components.add(scrollPane);
        components.add(createActionButton("Add current command to factory", (ActionEvent event) -> {
            try {
                CommandFactory.getInstance().addCommand(commandManager.getCurrentCommand());
                addCommandToFactoryList(commandManager.getCurrentCommandString().replace("CompoundCommand name: ", ""));
            } catch (CloneNotSupportedException exception) {
                exception.printStackTrace();
            }
        }));
        components.add(createActionButton("Set current command", (ActionEvent event) -> {
            if (commandFactoryList.getSelectedIndex() != -1) {
                DriverCommand command = CommandFactory.getInstance().getCommand((String) commandFactoryList.getSelectedValue());
                commandManager.setCurrentCommand(command);
            }
        }));
        components.add(createActionButton("Run current command", (ActionEvent event) -> {
            this.runCommand();
        }));
        components.add(createActionButton("Remove command from factory", (ActionEvent event) -> {
            if (commandFactoryList.getSelectedIndex() != -1) {
                CommandFactory.getInstance().removeCommand((String) commandFactoryList.getSelectedValue());
                commandFactory.remove(commandFactoryList.getSelectedIndex());
            }
        }));

        for (Component component : components) {
            content.add(component, c);
        }
    }

    private JButton createActionButton(String text, ActionListener action) {
        JButton result = new JButton(text);
        result.addActionListener(action);
        return result;
    }

    public void addCommandToFactoryList(String commandName) {
        if (!commandFactory.contains(commandName)) {
            commandFactory.add(commandFactory.getSize(), commandName);
        }
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
    }

    private void runCommand(){
        commandManager.runCommand(driverManager.getCurrentDriver());
    }

    public void updateCurrentCommandField() {
        currentCommandField.setText(commandManager.getCurrentCommandString());

        drawPanelController.clearPanel();
        commandManager.getCurrentCommand().execute(previewLineDriver);
    }

    public void deleteObservers() {
        commandManager.getChangePublisher().clearObservers();
        this.updateObserverListField();
    }

    public void resetObservers() {
        commandManager.setCurrentCommand(null);
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
