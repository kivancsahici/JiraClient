package huseyin;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;

import javax.swing.JPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;

/**
 * Utility class gathering some useful and general method.
 * Mainly convert forth and back graphical stuff between
 * awt and swt.
 */
public class SWTUtils {

    private final static String Az = "ABCpqr";

    /** A dummy JPanel used to provide font metrics. */
    protected static final JPanel DUMMY_PANEL = new JPanel();


    /**
     * Creates an AWT <code>MouseEvent</code> from a swt event.
     * This method helps passing SWT mouse event to awt components.
     * @param event The swt event.
     * @return A AWT mouse event based on the given SWT event.
     */
    public static MouseEvent toAwtMouseEvent(org.eclipse.swt.events.MouseEvent event) {
        int button = MouseEvent.NOBUTTON;
        switch (event.button) {
        case 1: button = MouseEvent.BUTTON1; break;
        case 2: button = MouseEvent.BUTTON2; break;
        case 3: button = MouseEvent.BUTTON3; break;
        }
        int modifiers = 0;
        if ((event.stateMask & SWT.CTRL) != 0) {
            modifiers |= InputEvent.CTRL_DOWN_MASK;
        }
        if ((event.stateMask & SWT.SHIFT) != 0) {
            modifiers |= InputEvent.SHIFT_DOWN_MASK;
        }
        if ((event.stateMask & SWT.ALT) != 0) {
            modifiers |= InputEvent.ALT_DOWN_MASK;
        }
        MouseEvent awtMouseEvent = new MouseEvent(DUMMY_PANEL, event.hashCode(),
                event.time, modifiers, event.x, event.y, 1, false, button);
        return awtMouseEvent;
    }

    /**
     * Converts an AWT image to SWT.
     *
     * @param image  the image (<code>null</code> not permitted).
     *
     * @return Image data.
     */
    public static ImageData convertAWTImageToSWT(Image image) {
        if (image == null) {
            throw new IllegalArgumentException("Null 'image' argument.");
        }
        int w = image.getWidth(null);
        int h = image.getHeight(null);
        if (w == -1 || h == -1) {
            return null;
        }
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return convertToSWT(bi);
    }

    /**
     * Converts a buffered image to SWT <code>ImageData</code>.
     *
     * @param bufferedImage  the buffered image (<code>null</code> not
     *         permitted).
     *
     * @return The image data.
     */
    public static ImageData convertToSWT(BufferedImage bufferedImage) {
        if (bufferedImage.getColorModel() instanceof DirectColorModel) {
            DirectColorModel colorModel
                    = (DirectColorModel) bufferedImage.getColorModel();
            PaletteData palette = new PaletteData(colorModel.getRedMask(),
                    colorModel.getGreenMask(), colorModel.getBlueMask());
            ImageData data = new ImageData(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), colorModel.getPixelSize(),
                    palette);
            WritableRaster raster = bufferedImage.getRaster();
            int[] pixelArray = new int[3];
            for (int y = 0; y < data.height; y++) {
                for (int x = 0; x < data.width; x++) {
                    raster.getPixel(x, y, pixelArray);
                    int pixel = palette.getPixel(new RGB(pixelArray[0],
                            pixelArray[1], pixelArray[2]));
                    data.setPixel(x, y, pixel);
                }
            }
            return data;
        }
        else if (bufferedImage.getColorModel() instanceof IndexColorModel) {
            IndexColorModel colorModel = (IndexColorModel)
                    bufferedImage.getColorModel();
            int size = colorModel.getMapSize();
            byte[] reds = new byte[size];
            byte[] greens = new byte[size];
            byte[] blues = new byte[size];
            colorModel.getReds(reds);
            colorModel.getGreens(greens);
            colorModel.getBlues(blues);
            RGB[] rgbs = new RGB[size];
            for (int i = 0; i < rgbs.length; i++) {
                rgbs[i] = new RGB(reds[i] & 0xFF, greens[i] & 0xFF,
                        blues[i] & 0xFF);
            }
            PaletteData palette = new PaletteData(rgbs);
            ImageData data = new ImageData(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), colorModel.getPixelSize(),
                    palette);
            data.transparentPixel = colorModel.getTransparentPixel();
            WritableRaster raster = bufferedImage.getRaster();
            int[] pixelArray = new int[1];
            for (int y = 0; y < data.height; y++) {
                for (int x = 0; x < data.width; x++) {
                    raster.getPixel(x, y, pixelArray);
                    data.setPixel(x, y, pixelArray[0]);
                }
            }
            return data;
        }
        else if (bufferedImage.getColorModel() instanceof ComponentColorModel) {
            ComponentColorModel colorModel = (ComponentColorModel)bufferedImage.getColorModel();
            //ASSUMES: 3 BYTE BGR IMAGE TYPE
            PaletteData palette = new PaletteData(0x0000FF, 0x00FF00,0xFF0000);
            ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(),
                    colorModel.getPixelSize(), palette);
            //This is valid because we are using a 3-byte Data model with no transparent pixels
            data.transparentPixel = -1;
            WritableRaster raster = bufferedImage.getRaster();
            int[] pixelArray = new int[3];
            for (int y = 0; y < data.height; y++) {
                for (int x = 0; x < data.width; x++) {
                    raster.getPixel(x, y, pixelArray);
                    int pixel = palette.getPixel(new RGB(pixelArray[0], pixelArray[1], pixelArray[2]));
                    data.setPixel(x, y, pixel);
                }
            }
            return data;
        }
        return null;
    }
}

