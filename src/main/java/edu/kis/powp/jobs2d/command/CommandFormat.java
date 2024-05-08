package edu.kis.powp.jobs2d.command;

public class CommandFormat {
    private static String format_name = "A4";
    private static int width = 210;
    private static int height = 297;

    public static void setFormatA4()
    {
        format_name = "A4";
        width = 210;
        height = 297;
    }

    public static void setFormatA3()
    {
        format_name = "A3";
        width = 297;
        height = 420;
    }

    public static void setFormatB3()
    {
        format_name = "B3";
        width = 353;
        height = 500;
    }

    public static String getFormatName()
    {
        return format_name;
    }

    public static boolean checkY(int y_cord)
    {
        if(y_cord > height/2 || y_cord < -(height /2))
        {
            return false;
        }
        return true;
    }
    public static boolean checkX(int x_cord)
    {
        if(x_cord > width/2 || x_cord < -(width/2))
        {
            return false;
        }
        return true;
    }
 }