package edu.kis.powp.jobs2d.command.memento;

import edu.kis.powp.jobs2d.command.gui.CommandManagerWindow;

import java.util.ArrayList;
import java.util.List;

public class EditHistory {
    private List<Pair> history = new ArrayList<>();
    private final EditHistoryObserver editObserver;
    private int virtualSize = 0;


    public EditHistory(CommandManagerWindow commandManagerWindow) {
        editObserver = new EditHistoryObserver(commandManagerWindow);
    }

    public int getVirtualSize() {
        return virtualSize;
    }

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


    public List<String> getHistoryList() {
        List<String> historyList = new ArrayList<>();
        for (Pair pair : history) {
            historyList.add(pair.getCommand().toString());
        }
        return historyList;
    }

    public void push(EditCommand c, Memento m) {
        if (virtualSize != history.size() && virtualSize > 0) {
            history = history.subList(0, virtualSize - 1);
        }else if (virtualSize == 0){
            history.clear();
        }
        history.add(new Pair(c, m));
        virtualSize = history.size();
        editObserver.update();
    }

    public boolean undo() {
        Pair pair = getUndo();
        if (pair == null)
            return false;
        pair.getMemento().restore();
        editObserver.update();
        return true;
    }

    public boolean redo() {
        Pair pair = getRedo();
        if (pair == null)
            return false;
        pair.getMemento().restoreAfter();
        editObserver.update();
        return true;
    }

    private Pair getUndo() {
        if (virtualSize == 0)
            return null;
        virtualSize = Math.max(0, virtualSize - 1);
        return history.get(virtualSize);
    }

    private Pair getRedo() {
        if (virtualSize == history.size())
            return null;
        virtualSize = Math.min(history.size(), virtualSize + 1);
        return history.get(virtualSize - 1);
    }

    public void clear() {
        history.clear();
        virtualSize = 0;
        editObserver.update();
    }
}