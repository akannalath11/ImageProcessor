import org.junit.Before;
import org.junit.Test;

import commands.Command;
import commands.SaveImage;
import commands.ScaleImage;
import controllers.ImageUtil;
import model.IPicture;
import model.ImageProcessorModel;
import model.PictureStorageModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests that the Scale image command works as expected. Reads in a photo and applies a
 * transformation. Checks that the created photo matches the expected. Checks for error messages
 * when the command is given invalid inputs.
 */
public class ScaleImageTests {
  private IPicture dogImage;
  private Command scaleCommand;
  private ImageProcessorModel model;

  @Before
  public void initData() {
    dogImage = ImageUtil.readAll("res/dog.jpg");
    model = new PictureStorageModel();
  }

  @Test
  public void testExecuteScaledImage() {
    model = new PictureStorageModel();
    model.addPicture(dogImage, "dog");
    scaleCommand = new ScaleImage(model, ".5", ".5", dogImage,
            "small-dog");
    scaleCommand.execute();

    Command saveImage = new SaveImage(model.getPicture("small-dog"),
            "testSmall.jpg");
    saveImage.execute();

    // Load correct image (Manipulated in Gimp)
    IPicture halfScaleCorrect =
            ImageUtil.readAll("testSmall.jpg");
    // Check that the new picture was added to the Model and get this Picture
    IPicture newImage = model.getPicture("small-dog");
    // Check that the new picture is the same as the correct one
    assertTrue(halfScaleCorrect.equals(newImage));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testScaleWidthNull() {
    scaleCommand = new ScaleImage(model, null, ".5", dogImage,
            "small-dog");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testScaleHeightNull() {
    scaleCommand = new ScaleImage(model, ".5", null, dogImage,
            "small-dog");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testScaleNameNull() {
    scaleCommand = new ScaleImage(model, ".5", ".5", dogImage,
            null);
  }

  @Test
  public void testOutPutMessage() {
    scaleCommand = new ScaleImage(model, ".5", ".5", dogImage,
            "small-dog");
    String output = scaleCommand.outputMessage();
    assertEquals("Scaled image by w: 0.5 height: 0.5 and saved as small-dog\n", output);
  }
}
