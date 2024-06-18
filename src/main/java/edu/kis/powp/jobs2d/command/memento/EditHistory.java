package edu.kis.powp.jobs2d.command.memento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditHistory {
    private List<Pair> history = new ArrayList<>();
    private int virtualSize = 0;

    private class Pair {
        EditCommand command;
        Memento memento;
        Pair(EditCommand c, Memento m) {
            command = c;
            memento = m;
        }

        private EditCommand getCommand() {
            return command;
        }

        private Memento getMemento() {
            return memento;
        }
    }

    public void printHistory() {
        System.out.println("History:");
        System.out.println(Arrays.toString(history.stream().map(pair1 -> pair1.getCommand().toString().split("\\$")[1]).toArray()));
    }
    public void push(EditCommand c, Memento m) {
        System.out.printf("Pushing: %s\n", c);
        if (virtualSize != history.size() && virtualSize > 0) {
            history = history.subList(0, virtualSize - 1);
        }
        history.add(new Pair(c, m));
        virtualSize = history.size();
        System.out.printf("Pushed: %s\n", c);
        printHistory();
    }

    public boolean undo() {
        Pair pair = getUndo();
        if (pair == null) {
            System.out.println("No more undos");
            return false;
        }
        System.out.println("Undoing: " + pair.getCommand());
        printHistory();
        pair.getMemento().restore();
        return true;
    }

    public boolean redo() {
        Pair pair = getRedo();
        if (pair == null) {
            System.out.println("No more redos");
            return false;
        }
        System.out.println("Redoing: " + pair.getCommand());
        printHistory();
        pair.getMemento().restore();
        pair.getCommand().execute();
        return true;
    }

    private Pair getUndo() {
        if (virtualSize == 0) {
            return null;
        }
        virtualSize = Math.max(0, virtualSize - 1);
        return history.get(virtualSize);
    }

    private Pair getRedo() {
        if (virtualSize == history.size()) {
            System.out.print("No more redos");
            return null;
        }
        virtualSize = Math.min(history.size(), virtualSize + 1);
        return history.get(virtualSize - 1);
    }

    public void clear() {
        history.clear();
        virtualSize = 0;
    }
}