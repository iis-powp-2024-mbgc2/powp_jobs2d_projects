package edu.kis.powp.jobs2d.usage;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class ConfigOfDeviceUsageLoader {
    public static double TANK_CAPACITY;
    public static double CONSUMPTION_PER_DISTANCE;
    public static double WARNING_INTERVAL_MAX;
    public static double WARNING_INTERVAL_MEDIUM;
    public static double WARNING_INTERVAL_LOW;
    public static double WARNING_INTERVAL_REFILL;

    public static void loadConfig(String filePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(filePath);
            document.getDocumentElement().normalize();

            Element root = document.getDocumentElement();

            TANK_CAPACITY = Double.parseDouble(getTagValue("tankCapacity", root));
            CONSUMPTION_PER_DISTANCE = Double.parseDouble(getTagValue("consumptionPerDistance", root));

            Element warningIntervals = (Element) root.getElementsByTagName("warningIntervals").item(0);
            WARNING_INTERVAL_MAX = Double.parseDouble(getTagValue("maxLevel", warningIntervals));
            WARNING_INTERVAL_MEDIUM = Double.parseDouble(getTagValue("mediumLevel", warningIntervals));
            WARNING_INTERVAL_LOW = Double.parseDouble(getTagValue("lowLevel", warningIntervals));
            WARNING_INTERVAL_REFILL = Double.parseDouble(getTagValue("needsRefill", warningIntervals));

        } catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            System.out.println("Loaded Successfully" + " TANK: " + TANK_CAPACITY);

        }

    }

    private static String getTagValue(String tag, Element element) {
        return element.getElementsByTagName(tag).item(0).getTextContent();
    }
}
