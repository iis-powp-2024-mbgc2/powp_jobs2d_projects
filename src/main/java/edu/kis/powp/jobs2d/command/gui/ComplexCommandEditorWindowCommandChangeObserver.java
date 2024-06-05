package edu.kis.powp.jobs2d.command.gui;

import edu.kis.powp.observer.Subscriber;

public class ComplexCommandEditorWindowCommandChangeObserver implements Subscriber {
    private ComplexCommandEditorWindow targetWindow;

    public ComplexCommandEditorWindowCommandChangeObserver(ComplexCommandEditorWindow window) {
        super();
        this.targetWindow = window;
    }

    public String toString() {
        return "Current command change observer for complex command editor";
    }

    @Override
    public void update() {
        targetWindow.updateViewToCurrentCommand();
    }
}
