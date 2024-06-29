package edu.kis.powp.jobs2d.command;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonCommandImporter implements CommandImporter {

    @Override
    public DriverCommand importCommands(String commands) throws JSONException, IllegalArgumentException {

        JSONObject jsonObject = new JSONObject(commands);

        String name = jsonObject.getString("name");
        JSONArray commandsArray = jsonObject.getJSONArray("commands");

        List<DriverCommand> commandsList = new ArrayList<>();
        for (int i = 0; i < commandsArray.length(); i++) {
            JSONObject commandObject = commandsArray.getJSONObject(i);
            DriverCommand command = createCommandFromJson(commandObject);
            commandsList.add(command);
        }

        return new CompoundCommand(commandsList, name);

    }

    private DriverCommand createCommandFromJson(JSONObject jsonObject) throws JSONException, IllegalArgumentException {
        String commandType = jsonObject.getString("command");
        int x = jsonObject.getInt("x");
        int y = jsonObject.getInt("y");

        switch (commandType) {
            case "setPosition":
                return new SetPositionCommand(x, y);
            case "operateTo":
                return new OperateToCommand(x, y);
            default:
                throw new IllegalArgumentException("Invalid command type: " + commandType);
        }
    }
}