package edu.kis.powp.jobs2d.command.gui;

import edu.kis.powp.appbase.gui.WindowComponent;
import edu.kis.powp.jobs2d.command.*;
import edu.kis.powp.jobs2d.command.editor.ComplexCommandEditor;
import edu.kis.powp.jobs2d.command.manager.ICommandManager;
import edu.kis.powp.jobs2d.features.CommandsFeature;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

enum PanelCreationOptions {
    LABEL_LABEL,
    LABEL_TEXTFIELD
}

public class ComplexCommandEditorWindow extends JFrame implements WindowComponent, CommandVisitor {
    public static final int NUMBER_OF_COLUMNS = 5;
    private JLabel commandNameValue;
    private JTextField paramXInput;
    private JTextField paramYInput;
    private JList<DriverCommand> commandList;
    private JLabel numberOfCommandsValue;
    private final DefaultListModel<DriverCommand> listModel;


    private ComplexCommandEditor complexCommandEditor;
    private ICompoundCommand currentCommand;
    private ICommandManager commandManager;

    public ComplexCommandEditorWindow(ICommandManager commandManager) {
        Container content = this.getContentPane();
        content.setLayout(new BorderLayout());
        this.setTitle("Complex command editor");
        this.setSize(640, 480);


        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(80, 120));
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        content.add(topPanel, BorderLayout.PAGE_START);

        // Top name panel
        JPanel topNamePanel = addNewPanelAndAddItToParentPanel(topPanel);

        commandNameValue = new JLabel();
        topNamePanel.add(commandNameValue);
        // Top statistics panel
        JPanel topStatisticsPanel = addNewPanelAndAddItToParentPanel(topPanel);
        preparePanel(PanelCreationOptions.LABEL_LABEL, "Number of commands: ", "N/A", topStatisticsPanel);

        // Top statistics number panel
        JPanel topStatisticsNumberPanel = addNewPanelAndAddItToParentPanel(topStatisticsPanel);
        addLabels("Number of commands: ", topStatisticsNumberPanel);
        numberOfCommandsValue = addLabels("", topStatisticsNumberPanel);

        // Main panel
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        content.add(mainPanel, BorderLayout.CENTER);

        JScrollPane mainLeftPanel = new JScrollPane();
        mainPanel.add(mainLeftPanel);

        JPanel mainRightPanel = new JPanel();
        mainRightPanel.setLayout(new BoxLayout(mainRightPanel, BoxLayout.Y_AXIS));
        mainPanel.add(mainRightPanel);

        // Main right parameters panel
        JPanel mainRightParametersPanel = addNewPanelAndAddItToParentPanel(mainRightPanel);
        addLabels("X: ", mainRightParametersPanel);
        paramXInput = addTextField("", mainRightParametersPanel);

        addLabels("Y: ", mainRightParametersPanel);
        paramYInput = addTextField("", mainRightParametersPanel);


        addButton("Change coordinates", this::handleChangeCoordinates, mainRightParametersPanel);

        // Main right order panel
        JPanel mainRightOrderPanel = addNewPanelAndAddItToParentPanel(mainRightPanel);
        addButton("Up", this::handleButtonUpClickedEvent, mainRightOrderPanel);
        addButton("Down", this::handleButtonDownClickedEvent, mainRightOrderPanel);

        // Main right bottom panel
        JPanel mainRightBottomPanel = addNewPanelAndAddItToParentPanel(mainRightPanel);
        addButton("Apply changes", this::handleConfirmButton, mainRightBottomPanel);

        this.commandManager = commandManager;
        listModel = new DefaultListModel<>();
        commandList = new JList<>(listModel);

        commandList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        commandList.addListSelectionListener(this::handleListElementSelection);

        mainLeftPanel.setViewportView(commandList);

        updateViewToCurrentCommand();
    }

    private void handleUpAndDownButtonsEvent(int index, int offset) {
        complexCommandEditor.moveCommand(index, index + offset);
        updateJList(complexCommandEditor.getEditedCompoundCommand());
        commandList.setSelectedIndex(index + offset);
    }

    private void handleButtonUpClickedEvent(ActionEvent e) {
        handleUpAndDownButtonsEvent(commandList.getSelectedIndex(), -1);
    }

    private void handleButtonDownClickedEvent(ActionEvent e) {
        handleUpAndDownButtonsEvent(commandList.getSelectedIndex(), 1);
    }

    private void handleConfirmButton(ActionEvent e) {
        java.util.List<DriverCommand> driverCommandList = new ArrayList<>();
        if(complexCommandEditor != null) {
            complexCommandEditor.getEditedCompoundCommand().iterator().forEachRemaining(driverCommandList::add);
        }
        CommandsFeature.getCommandManager().setCurrentCommand(driverCommandList, "Command made in Complex Command Editor");
    }

    private void handleChangeCoordinates(ActionEvent e) {
        complexCommandEditor.modifyCoordinates(
                commandList.getSelectedIndex(),
                Integer.parseInt(paramXInput.getText()),
                Integer.parseInt(paramYInput.getText())
        );
    }

    private JPanel addNewPanelAndAddItToParentPanel(JPanel mainPanel) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        mainPanel.add(panel);
        return panel;
    }

    private void preparePanel(PanelCreationOptions option, String firstLabel, String secondLabel, JPanel mainPanel) {
        switch (option) {
            case LABEL_LABEL:
                break;
            case LABEL_TEXTFIELD:
                addLabelAndTextField(firstLabel, mainPanel);
                addLabelAndTextField(secondLabel, mainPanel);
                break;
        }
    }


    private void addLabelAndTextField(String label, JPanel panel) {
        panel.add(new JLabel(label));
        panel.add(new JTextField(NUMBER_OF_COLUMNS));
    }

    private JTextField addTextField(String label, JPanel panel) {
        JTextField textField = new JTextField(NUMBER_OF_COLUMNS);
        panel.add(textField);
        return textField;
    }

    private JLabel addLabels(String label, JPanel panel) {
        JLabel jLabel = new JLabel(label);
        panel.add(jLabel);
        return jLabel;
    }

    private void addButton(String text, ActionListener listener, JPanel panel) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        panel.add(button);
    }

    public void updateViewToCurrentCommand() {
//        currentCommand = (CompoundCommand) CommandsFeature.getCommandManager().getCurrentCommand();
        currentCommand = (ICompoundCommand) commandManager.getCurrentCommand();
        if (currentCommand != null) {
            updateCommandStatistics(currentCommand);
            complexCommandEditor = new ComplexCommandEditor(currentCommand);
            updateJList(complexCommandEditor.getEditedCompoundCommand());
            commandNameValue.setText(CommandsFeature.getCommandManager().getCurrentCommandString());
        } else {
            commandNameValue.setText("No command selected");
            numberOfCommandsValue.setText("N/A");
            listModel.clear();
        }

        paramXInput.setText("");
        paramYInput.setText("");
    }

    private void updateCommandStatistics(ICompoundCommand iCompoundCommand) {
        CommandCounterVisitor commandCounterVisitor = new CommandCounterVisitor();
        commandCounterVisitor.visit(iCompoundCommand);
        numberOfCommandsValue.setText(String.valueOf(commandCounterVisitor.getAllCommandsCount()));
    }

    private void handleListElementSelection(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int index = commandList.getSelectedIndex();
            if (index > -1) {
                DriverCommand command = listModel.getElementAt(index);
                command.accept(this);
            }
        }
    }

    private void updateJList(ICompoundCommand editedComplexCommand) {
        listModel.clear();
        if (editedComplexCommand != null) {
            editedComplexCommand.iterator().forEachRemaining(listModel::addElement);
        }

    }

    @Override
    public void HideIfVisibleAndShowIfHidden() {
        this.setVisible(!this.isVisible());
    }

    @Override
    public void visit(OperateToCommand operateToCommand) {
        paramXInput.setText(String.valueOf(operateToCommand.getX()));
        paramYInput.setText(String.valueOf(operateToCommand.getY()));
    }

    @Override
    public void visit(SetPositionCommand setPositionCommand) {
        paramXInput.setText(String.valueOf(setPositionCommand.getX()));
        paramYInput.setText(String.valueOf(setPositionCommand.getY()));
    }

    @Override
    public void visit(ICompoundCommand compoundCommand) {

    }
}
