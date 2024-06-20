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
    private CommandManager commandManager;

    private JTextArea currentCommandField;
    private Canvas commandPreviewPanel;
    private DrawPanelController drawPanelController;

    private String observerListString;
    private JTextArea observerListField;
    private Job2dDriver previewLineDriver;
    private GridBagConstraints canvasPositionContraintsPrototype;
    private int canvasContentIndex;

    private CommandEditor commandEditor;
    private JTextArea explanationField;
    private JTextArea historyField;
    private JButton btnUndo, btnRedo;

    private final JPanel drawArea;
    final private Job2dDriver previewLineDriver;


    private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


    /**
     *
     */
    private static final long serialVersionUID = 9204679248304669948L;

    public CommandManagerWindow(CommandManager commandManager) {

        this.setTitle("Command Manager");
        this.setSize(400, 500);
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
        canvasContentIndex = content.getComponentCount();
        content.add(currentCommandField, c);

        commandPreviewPanel = new JPanelRectCanvas();

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
        drawPanelController.initialize((JPanel) commandPreviewPanel);
        previewLineDriver = new LineDriverAdapter(drawPanelController, new BasicLine(), "preview");
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.weighty = 5;

        JPanel drawArea = (JPanel) commandPreviewPanel;

        this.drawArea = commandPreviewPanel.getDrawArea();
        drawArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        content.add(drawArea, c);
        canvasPositionContraintsPrototype = (GridBagConstraints) c.clone();

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
        this.pack();
    }

    public Canvas getDrawPanel()
    {
        return commandPreviewPanel;
    }
    public void setDrawPanel(Canvas canvas)
    {
        this.commandPreviewPanel = canvas;
        drawPanelController = new DrawPanelController();
        drawPanelController.initialize((JPanel) commandPreviewPanel);
        previewLineDriver = new LineDriverAdapter(drawPanelController, new BasicLine(), "preview");
        canvasPositionContraintsPrototype.gridy = 0;


        Container content = this.getContentPane();
        JPanel drawArea = (JPanel) commandPreviewPanel;
        drawArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        content.remove(canvasContentIndex);
        canvasContentIndex = content.getComponentCount();
        content.add(drawArea, canvasPositionContraintsPrototype);
        this.revalidate();
        this.repaint();
    }

    public CommandManager getCommandManager() {
        return commandManager;
=======

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

        JTextArea historyLabel = new JTextArea("History:");
        historyLabel.setEditable(false);
        historyVBox.add(historyLabel);

        historyField = new JTextArea("");
        historyField.setEditable(false);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.add(historyField);
        historyVBox.add(scrollPane);
        historyVBox.setMaximumSize(new Dimension(200,100));

        historyHBox.add(historyVBox);
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
        updateHistory();
    }

    public void updateCurrentCommandField() {
        currentCommandField.setText(commandManager.getCurrentCommandString());
        drawPanelController.clearPanel();
        commandEditor.setCompoundCommand((CompoundCommand) commandManager.getCurrentCommand());
        commandEditor.restartMemento();
        if (commandManager.getCurrentCommand() != null)
            commandManager.getCurrentCommand().execute(previewLineDriver);
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
        int virtualSize = commandEditor.getHistory().getVirtualSize()-1;
        if (virtualSize >= 0)
            history.set(virtualSize, history.get(virtualSize) + " <-");

        String historyString = String.join("\n", history);
        this.historyField.setText(historyString);
    }
}
