package materials;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class DimensionsHandler {
    private static DimensionsHandler dim = null;
    private static int height;
    private static int width;
    private static int fontsize;
    private static int pressOffset;

    private static final double SCALE_FACTOR = .9;

    public static int getHeight() {
        return height;
    }
    public static int getWidth() {
        return width;
    }
    public static int getFontsize() {
        return fontsize;
    }
    public static int getPressOffset() {
        return pressOffset;
    }


    public DimensionsHandler() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        this.width = (int) (primaryScreenBounds.getWidth());
        this.height = (int) (primaryScreenBounds.getHeight());
        this.width = (int) (this.width * SCALE_FACTOR);
        this.height = (int) (this.height * SCALE_FACTOR);
        this.fontsize = calculateFontSize(23);
        this.pressOffset = (this.customHeight(10));
        //System.out.println("I am running!");
    }

    public double getHRatio(double calcHeight) {
        double ratio = (calcHeight) / 900;
        return ratio;
    }

    public double getWRatio(double calcWidth) {
        double ratio = calcWidth / 1600;
        return ratio;
    }

    private int calculateFontSize(int originalSize) {
        double origDiagonal = Math.sqrt(Math.pow(900, 2) + Math.pow(1600, 2));
        double newDiagonal = Math.sqrt(Math.pow(height, 2) + Math.pow(width, 2));
        double fontSize = (newDiagonal / origDiagonal) * Math.pow(23, 2);
        fontSize = Math.sqrt(fontSize);
        fontSize = (fontSize * SCALE_FACTOR);
        return (int) fontSize;
    }

    /**
     * returns the resized X value based on original 1600 scale
     * @param x x value to be resized
     * @return the x value that correspond's with this user's size/preference
     */

    public int customWidth(double x) {
        return (int) (width * getWRatio(x));
    }

    /**
     * returns the resized Y value based on original 900 scale
     * @param y the y value to be resized
     * @return (ratio of old scale to resized scale)*height
     */
    public int customHeight(double y) {
        return (int) (height * getHRatio(y));
    }


    /**
     * copy and paste this into the top of each subscene
     private static DimensionsHandler dim = new DimensionsHandler();
     private static final int HEIGHT = dim.getHEIGHT();
     private static final int WIDTH = dim.getWIDTH();
     private static final int FONTSIZE = dim.getFONTSIZE();
     */
}