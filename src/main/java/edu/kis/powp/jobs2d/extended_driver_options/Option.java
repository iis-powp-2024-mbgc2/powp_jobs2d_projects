package edu.kis.powp.jobs2d.extended_driver_options;

public class Option implements Comparable<Option> {

    private final DriverOptionDecorator driver;
    private final int menuIndex;

    public Option(DriverOptionDecorator driver, int menuIndex) {
        this.driver = driver;
        this.menuIndex = menuIndex;
    }


    public DriverOptionDecorator getDriverOption() {
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
