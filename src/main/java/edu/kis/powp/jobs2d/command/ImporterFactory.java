package edu.kis.powp.jobs2d.command;

import java.util.HashMap;
import java.util.Map;

public class ImporterFactory {
    private static final Map<String, CommandImporter> importers = new HashMap<>();

    public static void addImporter(String fileExtension, CommandImporter importer) {
        importers.put(fileExtension, importer);
    }

    public static CommandImporter getImporter(String fileExtension) {
        CommandImporter importer = importers.get(fileExtension);
        if (importer == null) {
            throw new IllegalArgumentException("No importer found for file");
        }
        return importer;
    }
}
