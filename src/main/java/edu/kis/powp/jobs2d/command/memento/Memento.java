package edu.kis.powp.jobs2d.command.memento;

import edu.kis.powp.jobs2d.command.gui.CommandEditor;

public class Memento {
    private final String backup;
    private String after;
    private final CommandEditor editor;

    public Memento(CommandEditor editor) {
        this.editor = editor;
        this.backup = editor.backup();
    }
    public void setAfterState() {
        this.after = editor.backup();
    }

    public void restore() {
        editor.restore(backup);
    }
    public void restoreAfter() {
        editor.restore(after);
    }
}