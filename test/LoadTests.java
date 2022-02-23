import org.junit.Before;
import org.junit.Test;

import commands.Command;
import commands.LoadImage;
import controllers.ImageUtil;
import model.IPicture;
import model.AllPicture;
import model.ImageProcessorModel;
import model.PictureStorageModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class is responsible for testing the load command.
 */
public class LoadTests {
  private AllPicture squareImage;
  private Command loadCommand;
  private ImageProcessorModel model;

  @Before
  public void initData() {
    squareImage = ImageUtil.readPPM("res/square.ppm");
    model = new PictureStorageModel();
    model.addPicture(squareImage, "square");
  }

  @Test
  public void testExecuteLoad() {
    loadCommand = new LoadImage(model, "res/square.ppm", "square");
    loadCommand.execute();

    // Checks that the given image is loaded by adding it to the model
    // Model is now able to get the newly loaded image by the proper name
    IPicture addedPicture = model.getPicture("square");
    // Make sure the loaded image is the same as the originally read image
    assertTrue(addedPicture.equals(squareImage));

    loadCommand = new LoadImage(model, "res/square.ppm", "square");
    loadCommand.execute();
  }

  @Test
  public void testExecuteLoadPNG() {
    loadCommand = new LoadImage(model, "res/square.png", "square");
    loadCommand.execute();

    // Checks that the given image is loaded by adding it to the model
    // Model is now able to get the newly loaded image by the proper name
    IPicture addedPicture = model.getPicture("square");
    // Make sure the loaded image is the same as the originally read image
    assertTrue(addedPicture.equals(squareImage));

    loadCommand = new LoadImage(model, "res/square.png", "square");
    loadCommand.execute();
  }

  @Test(expected = IllegalArgumentException.class)
  // checks exception when the file name is null
  public void testLoadEmptyFile() {
    loadCommand = new LoadImage(model, null, "square");
  }

  @Test(expected = IllegalArgumentException.class)
  // checks exception when the return name is invalid
  public void testLoadFakeFile() {
    loadCommand = new LoadImage(model, "res/square.ppm", null);
  }

  @Test(expected = IllegalArgumentException.class)
  // checks exception when the model is null
  public void testLoadModelNull() {
    loadCommand = new LoadImage(null, "", "square");
  }

  @Test
  public void testOutputMessage() {
    loadCommand = new LoadImage(model, "res/square.ppm", "square");
    String output = loadCommand.outputMessage();

    assertEquals("Loaded Image named square\n", output);

    loadCommand = new LoadImage(model, "res/square.ppm", "square-test-2");
    output = loadCommand.outputMessage();
    assertEquals("Loaded Image named square-test-2\n", output);
  }
}