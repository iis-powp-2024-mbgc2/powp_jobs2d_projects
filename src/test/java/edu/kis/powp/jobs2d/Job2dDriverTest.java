package edu.kis.powp.jobs2d;

import edu.kis.powp.jobs2d.drivers.IDriver;
import edu.kis.powp.jobs2d.drivers.visitor.DriverVisitor;
import edu.kis.powp.jobs2d.magicpresets.FiguresJoe;

/**
 * Plotter test.
 *
 * @author Dominik
 */
public class Job2dDriverTest {
    private static IDriver driver = new StubDriver();

    /**
     * Driver test.
     */
    public static void main(String[] args) {
        FiguresJoe.figureScript1(driver);
    }

    private static class StubDriver implements IDriver {

        @Override
        public void operateTo(int x, int y) {
            System.out.println("Driver operateTo action...");
        }

        @Override
        public void setPosition(int x, int y) {
            System.out.println("Driver setPosition action...");
        }

        @Override
        public void accept(DriverVisitor visitor) { System.out.println("Driver accept action..."); }
    }

    ;
}
