package edu.kis.powp.jobs2d.extended_driver_options;

import edu.kis.powp.jobs2d.Job2dDriver;
import edu.kis.powp.jobs2d.features.DriverFeature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DriverOptionsComposite {

    private final List<Option> driverOptionList;
    private static Job2dDriver usingDriver;

    private static DriverOptionsComposite instance;


    private DriverOptionsComposite() {
        this.driverOptionList = new ArrayList<>();
    }


    public static DriverOptionsComposite getInstance() {
        if (instance != null)
            return instance;

        synchronized (DriverOptionsComposite.class) {
            if (instance == null)
                instance = new DriverOptionsComposite();

            return instance;
        }
    }


    public void setUsingDriver(Job2dDriver driver) {
        usingDriver = driver;
        refresh();
    }


    public void addOption(Option option) {
        driverOptionList.add(option);
        Collections.sort(driverOptionList);
        refresh();
    }


    public void removeOption(int menuIndex) {
        driverOptionList.removeIf(option -> option.getMenuIndex() == menuIndex);
        refresh();
    }


    private void printOptionList() {
        System.out.println("\nOPTIONS:");
        driverOptionList.forEach(System.out::println);
    }


    private void refresh() {
        DriverFeature.getDriverManager().setCurrentDriver(usingDriver);

        if (driverOptionList.isEmpty())
            return;

        Job2dDriver compositeDriver = DriverFeature.getDriverManager().getCurrentDriver();

        for (Option option : driverOptionList) {
            DriverOption driverOption = option.getDriverOption();
            driverOption.setDriver(compositeDriver);
            compositeDriver = driverOption;
        }

        DriverFeature.getDriverManager().setCurrentDriver(compositeDriver);
    }

}
