package edu.kis.powp.jobs2d.command.builder;

import edu.kis.legacy.drawer.panel.DefaultDrawerFrame;
import edu.kis.legacy.drawer.panel.DrawPanelController;
import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.command.CompoundCommand;
import edu.kis.powp.jobs2d.command.gui.CommandEditor;
import edu.kis.powp.jobs2d.command.gui.CommandManagerWindow;

import javax.swing.*;

public class CommandEditorBuilder {
    public JPanel drawArea;
    public CompoundCommand compoundCommand;
    public Job2dDriver driver;
    public DrawPanelController drawPanelController;
    public DefaultDrawerFrame commandPreviewPanel;
    public CommandManagerWindow commandManagerWindow;

    public CommandEditorBuilder setDrawArea(JPanel drawArea) {
        this.drawArea = drawArea;
        return this;
    }

    public CommandEditorBuilder setCompoundCommand(CompoundCommand compoundCommand) {
        this.compoundCommand = compoundCommand;
        return this;
    }

    public CommandEditorBuilder setDriver(Job2dDriver driver) {
        this.driver = driver;
        return this;
    }

    public CommandEditorBuilder setDrawPanelController(DrawPanelController drawPanelController) {
        this.drawPanelController = drawPanelController;
        return this;
    }

    public CommandEditorBuilder setCommandPreviewPanel(DefaultDrawerFrame commandPreviewPanel) {
        this.commandPreviewPanel = commandPreviewPanel;
        return this;
    }

    public CommandEditorBuilder setCommandManagerWindow(CommandManagerWindow commandManagerWindow) {
        this.commandManagerWindow = commandManagerWindow;
        return this;
    }

    public CommandEditor build() {
        return new CommandEditor(this);
    }
}