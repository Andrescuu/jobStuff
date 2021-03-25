import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class waterMarker {

    static void addWatermark(File pixelImageFile, File watermarkImageFile, File sourceImageFile, File destImageFile) {
        try {
            BufferedImage sourceImage = ImageIO.read(sourceImageFile);
            BufferedImage watermarkImage = ImageIO.read(watermarkImageFile);
            BufferedImage pixelImage = ImageIO.read(pixelImageFile);

            // initializes necessary graphic properties
            Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();
            AlphaComposite alphaChannel;


            // Assigns height and width and
            // calculates the coordinate where the image is painted
            // theWidth and theHeight are the dimensions for the image to be marked
            // waterWidth and waterHeight are dimensions for the watermark

            int theWidth = sourceImage.getWidth();
            int theHeight = sourceImage.getHeight();

            int waterWidth = watermarkImage.getWidth();
            int waterHeight = watermarkImage.getHeight();
            int tracker = 0;

            int horizontalVar = 10;
            int verticalVar = 10;

            // while the verticalVar is lower than original image height
            // we increase the verticalVar.
            // we do the same with horizontalVal except when it reaches theWidth
            // we reset horizontalVal

            while (verticalVar < theHeight) {
                if (horizontalVar > theWidth) {
                    horizontalVar = 10;
                    verticalVar += 5*waterHeight;
                    tracker = 1 - tracker;
                    if (tracker == 1){
                        horizontalVar = (-waterWidth + waterWidth/6);
                    }
                }


                alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.015f);
                g2d.setComposite(alphaChannel);
                g2d.drawImage(pixelImage, horizontalVar, verticalVar  , null);

                alphaChannel = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.45f);
                g2d.setComposite(alphaChannel);
                g2d.drawImage(watermarkImage, horizontalVar, verticalVar, null);
                horizontalVar += 2*waterWidth;


            }

            System.out.println("Water mark created");
            ImageIO.write(sourceImage, "png", destImageFile);
            g2d.dispose();

        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public static void main(String[] args) {
        File sourceImageFile = new File("c:/JavaStuff/before2.jpg");
        File destImageFile = new File("c:/JavaStuff/image_watermarked.png");
        File watermarkImageFile = new File("c:/JavaStuff/waterMark.png");
        File pixelImageFile = new File("c:/JavaStuff/lastTry.png");

        addWatermark(pixelImageFile,watermarkImageFile, sourceImageFile, destImageFile);
    }
}