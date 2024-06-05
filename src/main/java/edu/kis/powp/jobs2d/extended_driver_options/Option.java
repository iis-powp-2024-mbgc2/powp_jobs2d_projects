package edu.kis.powp.jobs2d.extended_driver_options;

public class Option implements Comparable<Option> {

    private final DriverOption driver;
    private final int menuIndex;

    public Option(DriverOption driver, int menuIndex) {
        this.driver = driver;
        this.menuIndex = menuIndex;
    }


    public DriverOption getDriverOption() {
        return driver;
    }

    public int getMenuIndex() {
        return menuIndex;
    }

    @Override
    public int compareTo(Option other) {
        return Integer.compare(this.menuIndex, other.menuIndex);
    }
}
