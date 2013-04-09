package richtext;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class RichTextImages {

    private static final String IMG_BOLD = "sample.gif";

    private static final String IMG_ITALIC = "sample.gif";
    private static final String IMG_UNDERLINE = "sample2.gif";
    private static final String IMG_STRIKE_THROUGH = "sample.gif";
    private static final String IMG_CUT = "cut.png";
    private static final String IMG_COPY = "copy.png";
    private static final String IMG_PASTE = "paste.png";
    private static final String IMG_ERASER = "sample.gif";

    private static Image loadImage(String string) {
        InputStream stream = RichTextImages.class.getResourceAsStream(string);
        if (stream == null)
            return null;
        Image image = null;
        try {
            image = new Image(Display.getCurrent(), stream);
        } catch (SWTException ex) {
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
            }
        }
        return image;
    }

    /**
     * @return the imgBold
     */
    public static Image getImgBold() {
        return loadImage(IMG_BOLD);
    }

    /**
     * @return the imgItalic
     */
    public static Image getImgItalic() {
        return loadImage(IMG_ITALIC);
    }

    /**
     * @return the imgUnderline
     */
    public static Image getImgUnderline() {
        return loadImage(IMG_UNDERLINE);
    }

    /**
     * @return the imgStrikeThrough
     */
    public static Image getImgStrikeThrough() {
        return loadImage(IMG_STRIKE_THROUGH);
    }

    /**
     * @return the imgCut
     */
    public static Image getImgCut() {
        return loadImage(IMG_CUT);
    }

    /**
     * @return the imgCopy
     */
    public static Image getImgCopy() {
        return loadImage(IMG_COPY);
    }

    /**
     * @return the imgPaste
     */
    public static Image getImgPaste() {
        return loadImage(IMG_PASTE);
    }

    /**
     * @return the imgEraser
     */
    public static Image getImgEraser() {
        return loadImage(IMG_ERASER);
    }
}
