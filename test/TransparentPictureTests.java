import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import controllers.ImageUtil;
import model.IPicture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Tests the methods and constructors for the Transparent Picture class. Currently, our program
 * reads PNGs as transparent objects since they store an alpha value.
 */
public class TransparentPictureTests {
  private IPicture squareImage;

  @Before
  public void initData() {
    squareImage = ImageUtil.readAll("res/square.png");
  }

  @Test
  public void testPictureToString() {
    String correctString = "P3\n" +
            "3 3\n" +
            "255\n" +
            "51 102 153 52 154 154 255 153 153 \n" +
            "204 51 153 204 230 153 102 230 153 \n" +
            "102 204 230 179 127 12 25 127 25 \n";
    String squareString = squareImage.toString();
    assertEquals(correctString, squareString);
  }

  @Test
  public void testClone() {
    IPicture newImage = squareImage.clone();
    assertEquals(squareImage, newImage);

    squareImage.greyScale("red");
    IPicture greyscaleClone = squareImage.clone();
    assertEquals(squareImage, greyscaleClone);
  }

  @Test
  public void testEquals() {
    IPicture newImage = squareImage.clone();
    assertEquals(squareImage, newImage);

    squareImage.greyScale("red");
    assertFalse(squareImage.equals(newImage));
  }

  @Test
  public void testBrightenSquareImage() {
    squareImage.brightenImage(50);
    IPicture brightenImage2 = ImageUtil.readAll("res/square-brighter-by-50.png");
    assertEquals(true, brightenImage2.equals(squareImage));
  }

  @Test
  public void testDarkenImage() {
    squareImage.brightenImage(-50);
    IPicture darkenImage = ImageUtil.readAll("res/square-darker-by-50.png");
    assertEquals(false, darkenImage.equals(squareImage));
  }

  // Flipped Vertical or Horizontal Tests
  @Test
  public void testVertical() {
    squareImage.verticalFlip();
    IPicture verticalPuppy = ImageUtil.readAll("res/square-vertical.png");
    assertEquals(true, squareImage.equals(verticalPuppy));
  }

  @Test
  public void testHorizontal() {
    squareImage.horizontalFlip();
    IPicture horizontalPuppy = ImageUtil.readAll("res/square-horizontal.png");
    assertEquals(true, squareImage.equals(horizontalPuppy));
  }

  @Test
  public void testHorizontalVertical() {
    squareImage.horizontalFlip();
    squareImage.verticalFlip();

    IPicture hozVertFlip2 = ImageUtil.readAll("res/square-horizontal-vertical.png");
    assertEquals(true, squareImage.equals(hozVertFlip2));
  }

  // GreyScale Tests
  @Test
  public void testValueComponent() {
    squareImage.greyScale("value");
    IPicture valueComp = ImageUtil.readAll("res/square-value.png");
    assertEquals(true, squareImage.equals(valueComp));
  }

  @Test
  public void testIntensityComponent() {
    // In the case the average is a decimal, it will ROUND DOWN
    squareImage.greyScale("intensity");
    IPicture intensityComp = ImageUtil.readAll("res/square-intensity.png");
    assertEquals(true, squareImage.equals(intensityComp));
  }

  @Test
  public void testLumaComponent() {
    squareImage.greyScale("luma");
    IPicture lumaComp = ImageUtil.readAll("res/square-luma.png");
    assertEquals(true, squareImage.equals(lumaComp));
  }

  @Test
  public void testRedComponent() {
    squareImage.greyScale("red");
    IPicture redComp = ImageUtil.readAll("res/square-red.png");
    assertEquals(true, squareImage.equals(redComp));
  }

  @Test
  public void testBlueComponent() {
    squareImage.greyScale("blue");
    IPicture blueComp = ImageUtil.readAll("res/square-blue.png");
    assertEquals(true, squareImage.equals(blueComp));
  }

  @Test
  public void testGreenComponent() {
    squareImage.greyScale("green");
    IPicture greenComp = ImageUtil.readAll("res/square-green.png");
    assertEquals(true, squareImage.equals(greenComp));
  }

  @Test
  public void testSepia() {
    List<Double> transformList = new ArrayList<>(Arrays.asList(0.3930, .3490, .2720, .7690, .6860,
            .5340, .1890, .1680, .131));
    squareImage.colorTransform(transformList);
    IPicture sepiaImage = ImageUtil.readAll("res/square-sepia.png");
    assertEquals(true, squareImage.equals(sepiaImage));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testColorTransformInvalidArray() {
    List<Double> transformList = new ArrayList<>(Arrays.asList(0.3930, .3490, .2720, .7690, .6860,
            .5340, .1890));
    squareImage.colorTransform(transformList);
  }

  @Test
  public void testBlur() {
    List<Double> blurValues = new ArrayList<>(Arrays.asList(
            .0625, .125, .0625,
            .125, .25, .125,
            .0625, .125, .0625));
    squareImage.filter(blurValues, 1);
    IPicture blurImage = ImageUtil.readAll("res/square-blur.png");
    assertEquals(true, squareImage.equals(blurImage));
  }

  @Test
  public void testSharpen() {
    List<Double> sharpenValues = new ArrayList<>(Arrays.asList(
            -.125, -.125, -.125, -.125, -.125,
            -.125, .25, .25, .25, -.125,
            -.125, .25, 1.0, .25, -.125,
            -.125, .25, .25, .25, -.125,
            -.125, -.125, -.125, -.125, -.125));
    squareImage.filter(sharpenValues, 2);
    IPicture sharpenImage = ImageUtil.readAll("res/square-sharpen.png");
    assertEquals(true, squareImage.equals(sharpenImage));
  }

  @Test
  public void testSave() {
    // test an TransparentPicture saves to multiple file types (.png, .bmp, .ppm)
    squareImage.save("testConsole.png");
    squareImage.save("testConsole.ppm");
    squareImage.save("testConsole.bmp");
    // Read the newly exported images in, check that the data is equal
    IPicture newPPM = ImageUtil.readPPM("testConsole.ppm");
    IPicture newPNG = ImageUtil.readAll("testConsole.png");
    IPicture newBMP = ImageUtil.readAll("testConsole.bmp");
    assertEquals(true, squareImage.equals(newPPM));
    assertEquals(true, squareImage.equals(newPNG));
    assertEquals(true, squareImage.equals(newBMP));
  }

  @Test
  public void testBufferedImage() throws IOException {
    // Convert to buffered image and export
    BufferedImage image = squareImage.getBufferedImage();
    File outputFile = new File("res/squareImageTest.png");
    ImageIO.write(image, "png", outputFile);

    // Read in the buffered image, check equality to make sure all the data transmitted properly
    IPicture comparision = ImageUtil.readAll("res/squareImageTest.png");
    assertEquals(comparision, squareImage);
  }

  /*
  P3
  #Created with GIMP
  #
  3 3
  255
  51 102 153       52 154 154         255 153 153
  204 51 153       204 230 153        102 230 153
  102 204 230      179 127 12         25 127 25
   */

  @Test
  public void testColorValues() {
    // For results see text above
    int[] redResults = squareImage.colorValues("red");
    assertEquals(256, redResults.length); // check that the length is correct
    assertEquals(0, redResults[0]);
    assertEquals(2, redResults[204]);
    assertEquals(2, redResults[102]);

    int[] blueResults = squareImage.colorValues("blue");
    assertEquals(256, blueResults.length); // check that the length is correct
    assertEquals(0, blueResults[0]);
    assertEquals(5, blueResults[153]);
    assertEquals(1, blueResults[154]);
  }
}
