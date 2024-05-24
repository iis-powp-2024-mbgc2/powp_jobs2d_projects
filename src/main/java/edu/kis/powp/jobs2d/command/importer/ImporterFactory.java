package edu.kis.powp.jobs2d.command.importer;

import java.util.HashMap;
import java.util.Map;

public class ImporterFactory {
    private static final Map<String, ICommandImporter> importers = new HashMap<>();

    public static void addImporter(String fileExtension, ICommandImporter importer) {
        importers.put(fileExtension, importer);
    }

    public static ICommandImporter getImporter(String fileExtension) {
        ICommandImporter importer = importers.get(fileExtension);
        if (importer == null) {
            throw new IllegalArgumentException("No importer found for file");
        }
        return importer;
    }
}
