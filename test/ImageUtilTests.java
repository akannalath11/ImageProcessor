import org.junit.Test;


import controllers.ImageUtil;
import model.IPicture;
import model.AllPicture;

import static org.junit.Assert.assertEquals;

/**
 * This class is responsible for testing the ImageUtil Methods.
 */
public class ImageUtilTests {

  @Test
  public void testReadPPM() {
    AllPicture squareImage = ImageUtil.readPPM("res/square.ppm");

    String squareString = squareImage.toString();
    String ppmCorrectString = "P3\n" +
            "3 3\n" +
            "255\n" +
            "51 102 153 52 154 154 255 153 153 \n" +
            "204 51 153 204 230 153 102 230 153 \n" +
            "102 204 230 179 127 12 25 127 25 \n";

    // Checks that the object.toString is the same as the PPM files text directly copied
    // into a string
    assertEquals(ppmCorrectString, squareString);

    AllPicture squareImage2 = ImageUtil.readPPM("res/square-brighter-by-50.ppm");

    String squareString2 = squareImage2.toString();
    String ppmCorrectString2 = "P3\n" +
            "3 3\n" +
            "255\n" +
            "101 152 203 102 204 204 255 203 203 \n" +
            "254 101 203 254 255 203 152 255 203 \n" +
            "152 254 255 229 177 62 75 177 75 \n";

    // Checks that the object.toString is the same as the PPM files text directly copied
    // into a string
    assertEquals(ppmCorrectString2, squareString2);
  }

  @Test
  public void testReadPNG() {
    IPicture squareImage = ImageUtil.readAll("res/square.png");

    String squareString = squareImage.toString();
    String correctString = "P3\n" +
            "3 3\n" +
            "255\n" +
            "51 102 153 52 154 154 255 153 153 \n" +
            "204 51 153 204 230 153 102 230 153 \n" +
            "102 204 230 179 127 12 25 127 25 \n";

    // Checks that the object.toString is the same as the PPM files text directly copied
    // into a string
    assertEquals(correctString, squareString);
  }

  @Test
  public void testReadBMP() {
    IPicture squareImage = ImageUtil.readAll("res/square.bmp");

    String squareString = squareImage.toString();
    String correctString = "P3\n" +
            "3 3\n" +
            "255\n" +
            "51 102 153 52 154 154 255 153 153 \n" +
            "204 51 153 204 230 153 102 230 153 \n" +
            "102 204 230 179 127 12 25 127 25 \n";

    // Checks that the object.toString is the same as the PPM files text directly copied
    // into a string
    assertEquals(correctString, squareString);
  }

  @Test
  public void testReadJPG() {
    IPicture squareImage = ImageUtil.readAll("res/square.jpg");

    // NOTE: Some JPG image data is off by 1, due to compression, this is accounted for
    // in the correct string below.
    String squareString = squareImage.toString();
    String correctString = "P3\n" +
            "3 3\n" +
            "255\n" +
            "51 103 153 52 155 154 255 152 153 \n" +
            "203 51 152 203 230 153 102 230 155 \n" +
            "100 204 229 178 127 12 25 127 25 \n";

    // Checks that the object.toString is the same as the PPM files text directly copied
    // into a string
    assertEquals(correctString, squareString);
  }

  @Test
  public void testSave() {
    IPicture squareImage = ImageUtil.readAll("res/square.jpg");
    // test an AllPicture saves to multiple file types (.png, .bmp, .ppm)
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
}
