import org.junit.Before;
import org.junit.Test;

import commands.BrightenImage;
import commands.Command;
import controllers.ImageUtil;
import model.IPicture;
import model.AllPicture;
import model.ImageProcessorModel;
import model.PictureStorageModel;

import static org.junit.Assert.assertEquals;

/**
 * Tests that the Brighten Command works.
 * This tests both Brightening and Darkening an image including the cases where a color
 * value exceeds the max value available.
 */
public class   BrightenTests {
  private IPicture squareImage;
  private Command brightenCommand;
  private ImageProcessorModel model;

  @Before
  public void initData() {
    squareImage = ImageUtil.readPPM("res/square.ppm");
    model = new PictureStorageModel();
  }

  @Test
  public void testExecuteSquare() {
    model = new PictureStorageModel();
    model.addPicture(squareImage, "square");
    brightenCommand = new BrightenImage(model, "50", "b", squareImage,
            "square-brightened");
    brightenCommand.execute();

    // Load correct image (Manipulated in Gimp)
    AllPicture brightenImageCorrect =
            ImageUtil.readPPM("res/square-brighter-by-50.ppm");
    // Check that the new picture was added to the Model and get this Picture
    IPicture newImage = model.getPicture("square-brightened");
    // Check that the new picture is the same as the correct one
    assertEquals(true, brightenImageCorrect.equals(newImage));
  }

  @Test
  public void testExecuteSquarePNG() {
    squareImage = ImageUtil.readAll("res/square.png");
    model = new PictureStorageModel();
    model.addPicture(squareImage, "square");
    brightenCommand = new BrightenImage(model, "50", "b", squareImage,
            "square-brightened");
    brightenCommand.execute();

    // Load correct image (Manipulated in Gimp)
    IPicture brightenImageCorrect =
            ImageUtil.readAll("res/square-brighter-by-50.png");
    // Check that the new picture was added to the Model and get this Picture
    IPicture newImage = model.getPicture("square-brightened");
    // Check that the new picture is the same as the correct one
    assertEquals(true, brightenImageCorrect.equals(newImage));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBrightenModelNull() {
    brightenCommand = new BrightenImage(null, "50", "b",
             squareImage, "koala");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBrightenPictureNull() {
    brightenCommand = new BrightenImage(model, "50", "b",
            null, "koala");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBrightenNameNull() {
    brightenCommand = new BrightenImage(model, "50", "b",
            squareImage, null);
  }

  @Test
  public void testOutPutMessage() {
    brightenCommand = new BrightenImage(model, "50", "b", squareImage,
            "square-brightened");
    String output = brightenCommand.outputMessage();
    assertEquals("Brightened image by 50 and saved as square-brightened\n", output);
  }
}
