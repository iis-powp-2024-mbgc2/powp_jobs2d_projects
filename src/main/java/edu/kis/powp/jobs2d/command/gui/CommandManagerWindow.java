package edu.kis.powp.jobs2d.command.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
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
import edu.kis.powp.jobs2d.command.*;
import edu.kis.powp.jobs2d.command.manager.CommandManager;
import edu.kis.powp.jobs2d.drivers.MouseClickConverter;
import edu.kis.powp.jobs2d.drivers.EditorDriver;
import edu.kis.powp.jobs2d.drivers.MouseClickEditor;
import edu.kis.powp.jobs2d.drivers.adapter.LineDriverAdapter;
import edu.kis.powp.observer.Subscriber;

public class CommandManagerWindow extends JFrame implements WindowComponent {
    private CommandManager commandManager;

    private JTextArea currentCommandField;
    private DefaultDrawerFrame commandPreviewPanel;
    private DrawPanelController drawPanelController;

    private String observerListString;
    private JTextArea observerListField;
    private ArrayList<Point> points;
    private MouseClickEditor mouseClickEditor;
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

        JButton btnToggleEdit = new JButton("Toggle edit");
        btnToggleEdit.addActionListener((ActionEvent e)->toggleEdit());
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.gridx = 0;
        c.weighty = 1;
        content.add(btnToggleEdit, c);

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


        this.points = new ArrayList<>();
        this.mouseClickEditor = new MouseClickEditor(drawArea, points, previewLineDriver);
    }

    private void toggleEdit() {
        System.out.println("toggle edit");
        ((ICompoundCommand) commandManager.getCurrentCommand()).iterator().forEachRemaining((DriverCommand command) ->{
            //System.out.println(command);
            if (command instanceof OperateToCommand) {
                int x = ((OperateToCommand) command).getX();
                int y = ((OperateToCommand) command).getY();
                //System.out.println("operate " + x + ", " + y);
                points.add(new Point(x,y));
            }
            if (command instanceof SetPositionCommand) {
                int x = ((SetPositionCommand) command).getX();
                int y = ((SetPositionCommand) command).getY();
                //System.out.println("set " + x + ", " + y);
                points.add(new Point(x,y));
            }
        });
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

    public void updateCurrentCommandField() {
        currentCommandField.setText(commandManager.getCurrentCommandString());

        drawPanelController.clearPanel();
        commandManager.getCurrentCommand().execute(previewLineDriver);
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
