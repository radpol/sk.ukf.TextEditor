package richtext;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class Images {


    private static Image loadImage(String name) {
        InputStream stream = Images.class.getResourceAsStream(name+".png");
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
    
    public static Image [] getImage(String name){
    	Image[] images = new Image[2];
    	images[0] = loadImage(name);
    	images[1] = loadImage(name+"2");
    	return images;
    }

}
