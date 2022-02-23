import org.junit.Before;
import org.junit.Test;

import commands.Command;
import commands.SaveImage;
import controllers.ImageUtil;
import model.AllPicture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class is responsible for testing the save command.
 */
public class SaveTests {
  private AllPicture squareImage;
  private Command saveCommand;

  @Before
  public void initData() {
    squareImage = ImageUtil.readPPM("res/square.ppm");
  }

  @Test(expected = IllegalArgumentException.class)
  // checks exception when the file name is null
  public void testSavePictureNull() {
    saveCommand = new SaveImage(null, "res/square.ppm");
  }

  @Test(expected = IllegalArgumentException.class)
  // checks exception when the file name is invalid
  public void testSaveNameNull() {
    saveCommand = new SaveImage(squareImage, null);
  }

  @Test
  public void testExecuteSave() {
    saveCommand = new SaveImage(squareImage, "res/square-saved.ppm");
    saveCommand.execute();

    // Check that the new picture was saved to the correct location relative to the
    // main IntelliJ file location
    AllPicture savedImage = ImageUtil.readPPM("res/square-saved.ppm");
    // Check that the new saved Image when read is the same as the originally saved image
    assertTrue(savedImage.equals(squareImage));

  }

  @Test
  public void testExecuteSavePNG() {
    saveCommand = new SaveImage(squareImage, "res/square-saved.png");
    saveCommand.execute();

    // Check that the new picture was saved to the correct location relative to the
    // main IntelliJ file location
    AllPicture savedImage = ImageUtil.readPPM("res/square-saved.ppm");
    // Check that the new saved Image when read is the same as the originally saved image
    assertTrue(savedImage.equals(squareImage));

  }

  @Test
  public void testOutputMessage() {
    saveCommand = new SaveImage(squareImage, "square-value");
    String output = saveCommand.outputMessage();

    assertEquals("Saved to square-value\n", output);

    saveCommand = new SaveImage(squareImage, "res/square.ppm");
    output = saveCommand.outputMessage();
    assertEquals("Saved to res/square.ppm\n", output);
  }
}