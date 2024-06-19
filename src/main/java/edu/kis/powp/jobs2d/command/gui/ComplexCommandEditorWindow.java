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
    public static final int WINDOW_HEIGHT = 600;
    public static final int WINDOW_WIDTH = 850;
    public static final int DIMENSION_WIDTH = 80;
    public static final int DIMENSION_HEIGHT = 120;
    private JLabel commandNameValue;
    private JTextField paramXInput;
    private JTextField paramYInput;
    private JList<DriverCommand> commandList;
    private JLabel numberOfCommandsValue;
    private DefaultListModel<DriverCommand> listModel;


    private ComplexCommandEditor complexCommandEditor;
    private final ICommandManager commandManager;

    public ComplexCommandEditorWindow(ICommandManager commandManager) {
        Container content = this.getContentPane();
        initializeWindow();
        initializeTopPanel(content);
        initializeMainPanel(content);
        this.commandManager = commandManager;
        updateViewToCurrentCommand();
    }

    private void initializeWindow() {
        setTitle("Complex command editor");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLayout(new BorderLayout());
    }

    private void initializeTopPanel(Container content) {
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(DIMENSION_WIDTH, DIMENSION_HEIGHT));
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        content.add(topPanel, BorderLayout.PAGE_START);

        JPanel topNamePanel = addNewPanelAndAddItToParentPanel(topPanel);
        commandNameValue = new JLabel();
        topNamePanel.add(commandNameValue);

        initializeTopStatisticsPanel(topPanel);
    }

    private void initializeTopStatisticsPanel(JPanel topPanel) {
        JPanel topStatisticsPanel = addNewPanelAndAddItToParentPanel(topPanel);
        preparePanel(PanelCreationOptions.LABEL_LABEL, "Number of commands: ", "N/A", topStatisticsPanel);
        JPanel topStatisticsNumberPanel = addNewPanelAndAddItToParentPanel(topStatisticsPanel);
        addLabels("Number of commands: ", topStatisticsNumberPanel);
        numberOfCommandsValue = addLabels("", topStatisticsNumberPanel);
    }

    private void initializeMainPanel(Container content) {
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        content.add(mainPanel, BorderLayout.CENTER);

        JScrollPane mainLeftPanel = new JScrollPane();
        mainPanel.add(mainLeftPanel);

        JPanel mainRightPanel = new JPanel();
        mainRightPanel.setLayout(new BoxLayout(mainRightPanel, BoxLayout.Y_AXIS));
        mainPanel.add(mainRightPanel);

        initializeMainStatisticsPanel(mainRightPanel);
        initializeMainRightOrderPanel(mainRightPanel, mainLeftPanel);
    }

    private void initializeMainStatisticsPanel(JPanel mainRightPanel) {
        JPanel mainRightParametersPanel = addNewPanelAndAddItToParentPanel(mainRightPanel);
        addLabels("X: ", mainRightParametersPanel);
        paramXInput = addTextField("", mainRightParametersPanel);

        addLabels("Y: ", mainRightParametersPanel);
        paramYInput = addTextField("", mainRightParametersPanel);


        addButton("Change coordinates", this::handleChangeCoordinates, mainRightParametersPanel);
    }

    private void initializeMainRightOrderPanel(JPanel mainRightPanel, JScrollPane mainLeftPanel) {
        JPanel mainRightOrderPanel = addNewPanelAndAddItToParentPanel(mainRightPanel);
        addButton("Up", this::handleButtonUpClickedEvent, mainRightOrderPanel);
        addButton("Down", this::handleButtonDownClickedEvent, mainRightOrderPanel);

        JPanel mainRightBottomPanel = addNewPanelAndAddItToParentPanel(mainRightPanel);
        addButton("Apply changes", this::handleConfirmButton, mainRightBottomPanel);

        listModel = new DefaultListModel<>();
        commandList = new JList<>(listModel);

        commandList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        commandList.addListSelectionListener(this::handleListElementSelection);

        mainLeftPanel.setViewportView(commandList);
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
        updateJList(complexCommandEditor.getEditedCompoundCommand());
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
        ICompoundCommand currentCommand = (ICompoundCommand) commandManager.getCurrentCommand();
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
