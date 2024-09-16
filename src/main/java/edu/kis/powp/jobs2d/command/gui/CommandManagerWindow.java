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
import edu.kis.powp.jobs2d.command.*;
import edu.kis.powp.jobs2d.command.builder.CommandEditorBuilder;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.observer.Subscriber;

public class CommandManagerWindow extends JFrame implements WindowComponent {

    private static CommandManagerWindow instance = null;
    private CommandManager commandManager;
    private JTextArea currentCommandField;
    private DefaultDrawerFrame commandPreviewPanel;
    private DrawPanelController drawPanelController;

    private String observerListString;
    private JTextArea observerListField;
    private CommandEditor commandEditor;
    private JTextArea explanationField;
    private JTextArea historyField;

    private JPanel  commandHistoryField;
    private JButton btnUndo, btnRedo;

    private final JPanel drawArea;
    final private Job2dDriver previewLineDriver;

    CommandHistoryHandlerGUI guiHandler;
    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);



    /**
     *
     */
    private static final long serialVersionUID = 9204679248304669948L;
    private CommandHistoryLogger commandHistoryLogger;

    public CommandManagerWindow(CommandManager commandManager) {

        this.setTitle("Command Manager");
        this.setSize(400, 500);
        Container content = this.getContentPane();
        content.setLayout(new GridBagLayout());

        this.commandManager = commandManager;
        instance = this;

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

        explanationField = new JTextArea("");
        explanationField.setEditable(false);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.weighty = 1;
        content.add(explanationField, c);
        this.updateExplanationField();

        Box historyPanel = createHistoryPanel();
        content.add(historyPanel, c);


        commandPreviewPanel = new DefaultDrawerFrame();
        drawPanelController = new DrawPanelController();
        drawPanelController.initialize(commandPreviewPanel.getDrawArea());
        previewLineDriver = new LineDriverAdapter(drawPanelController, new BasicLine(), "preview");
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.weighty = 5;
        this.drawArea = commandPreviewPanel.getDrawArea();
        drawArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        content.add(drawArea, c);

        JButton btnImportCommand = new JButton("Import command");
        btnImportCommand.addActionListener((ActionEvent e) -> this.importCommandFromFile());

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.weighty = 1;
        content.add(btnImportCommand, c);

        guiHandler = new CommandHistoryHandlerGUI(commandHistoryField,commandManager);
        commandHistoryLogger = new CommandHistoryLogger(commandManager, guiHandler);

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

        this.commandEditor = new CommandEditorBuilder()
                .setDrawArea(drawArea)
                .setCompoundCommand((CompoundCommand) (commandManager.getCurrentCommand()))
                .setDriver(previewLineDriver)
                .setDrawPanelController(drawPanelController)
                .setCommandPreviewPanel(commandPreviewPanel)
                .setCommandManagerWindow(this)
                .build();

    }

    private Box createHistoryPanel() {
        Box historyHBox = Box.createHorizontalBox();

        btnUndo = new JButton("Undo");
        btnUndo.addActionListener((ActionEvent e) -> this.commandEditor.undo());
        historyHBox.add(btnUndo);

        btnRedo = new JButton("Redo");
        btnRedo.addActionListener((ActionEvent e) -> this.commandEditor.redo());
        historyHBox.add(btnRedo);

        Box historyVBox = Box.createVerticalBox();
        Box commandHistoryVBox = Box.createVerticalBox();

        JTextArea historyLabel = new JTextArea("Edit History:");
        historyLabel.setEditable(false);
        historyLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, historyLabel.getPreferredSize().height));
        historyVBox.add(historyLabel);

        historyField = new JTextArea("");
        historyField.setEditable(false);
        JScrollPane historyScrollPane = new JScrollPane(historyField);
        historyScrollPane.setPreferredSize(new Dimension(200, 100));
        historyScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        historyVBox.add(historyScrollPane);

        JTextArea commandHistoryLabel = new JTextArea("Command History:");
        commandHistoryLabel.setEditable(false);
        commandHistoryLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, commandHistoryLabel.getPreferredSize().height));
        commandHistoryVBox.add(commandHistoryLabel);

        commandHistoryField = new JPanel();
        JScrollPane commandScrollPane = new JScrollPane(commandHistoryField);
        commandScrollPane.setPreferredSize(new Dimension(200, 100));
        commandScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        commandHistoryVBox.add(commandScrollPane);

        historyVBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        commandHistoryVBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        historyVBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        commandHistoryVBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        historyHBox.add(historyVBox);
        historyHBox.add(commandHistoryVBox);

        historyHBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        historyHBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        return historyHBox;
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
        commandHistoryLogger.logCurrentCommand();
        updateHistory();
    }

    public void updateCurrentCommandField() {
        currentCommandField.setText(commandManager.getCurrentCommandString());
        drawPanelController.clearPanel();
        commandEditor.setCompoundCommand((CompoundCommand) commandManager.getCurrentCommand());
        commandEditor.restartMemento();
        if (commandManager.getCurrentCommand() != null)
            commandManager.getCurrentCommand().execute(previewLineDriver);
        commandHistoryLogger.logCurrentCommand();
        System.out.println("updateCurrentCommandField");
    }

    public void updateExplanationField() {
        explanationField.setText(
                "LMB - Drag to move points\n" +
                        "RMB - Open context menu");
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

    public void updateHistory() {

        this.historyField.setText("");
        this.btnUndo.setEnabled(commandEditor.getHistory().getVirtualSize() > 0);
        this.btnRedo.setEnabled(commandEditor.getHistory().getVirtualSize() < commandEditor.getHistory().getHistoryList().size());

        List<String> history = commandEditor.getHistory().getHistoryList();
        int virtualSize = commandEditor.getHistory().getVirtualSize() - 1;


        if (virtualSize >= 0)
            history.set(virtualSize, history.get(virtualSize) + " <-");

        String historyString = String.join("\n", history);
        this.historyField.setText(historyString);


        if (historyString.contains("Move"))
            return;

        for (int i = 0; i < history.size(); i++) {
            JPanel entryPanel = new JPanel();
            entryPanel.setLayout(new BorderLayout());

            JLabel historyLabel = new JLabel(history.get(i));
            entryPanel.add(historyLabel, BorderLayout.CENTER);

            JButton btnR = new JButton("R");
            int commandIndex = i;
            btnR.addActionListener((ActionEvent e) -> redoCommand(commandIndex));
            entryPanel.add(btnR, BorderLayout.EAST);

            commandHistoryField.add(entryPanel);
        }
    }

    private void redoCommand(int commandIndex) {
        guiHandler.redoCommand(commandIndex);
    }


    public JPanel getCommandHistoryField() {
        return commandHistoryField;
    }

    public static CommandManagerWindow getInstance() {
        return instance;
    }

}
