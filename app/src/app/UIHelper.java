package app;

import org.eclipse.swt.graphics.Color;

public class UIHelper {
    private static Color color;

    /**
     * @return the color
     */
    public static Color getColor() {
        return color;
    }

    /**
     * @param color
     *            the color to set
     */
    public static void setColor(Color color) {
        UIHelper.color = color;
    }
}
