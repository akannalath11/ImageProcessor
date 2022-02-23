import org.junit.Before;
import org.junit.Test;


import commands.Command;
import commands.HorizontalFlip;
import commands.VerticalFlip;
import controllers.ImageUtil;
import model.IPicture;
import model.AllPicture;
import model.ImageProcessorModel;
import model.PictureStorageModel;

import static org.junit.Assert.assertEquals;

/**
 * This class is responsible for testing the flip commands, both vertical and horizontal.
 */
public class FlipTests {
  private IPicture squareImage;
  private Command flippedCommand;
  private ImageProcessorModel model;

  @Before
  public void initData() {
    squareImage = ImageUtil.readPPM("res/square.ppm");
    model = new PictureStorageModel();
    model.addPicture(squareImage, "square");

  }

  @Test
  public void testExecuteVertical() {
    flippedCommand = new VerticalFlip(model, squareImage,"square-vertical");
    flippedCommand.execute();

    // Load correct image (Manipulated in Gimp)
    AllPicture correctImage = ImageUtil.readPPM("res/square-vertical.ppm");
    // Check that the new picture was added to the Model and get this Picture
    IPicture newImage = model.getPicture("square-vertical");
    // Check that the new picture is the same as the correct one
    assertEquals(true, newImage.equals(correctImage));
  }

  @Test
  public void testExecuteHorizontal() {
    flippedCommand = new HorizontalFlip(model, squareImage,"square-horizontal");
    flippedCommand.execute();

    // Load correct image (Manipulated in Gimp)
    AllPicture correctImage = ImageUtil.readPPM("res/square-horizontal.ppm");
    // Check that the new picture was added to the Model and get this Picture
    IPicture newImage = model.getPicture("square-horizontal");
    // Check that the new picture is the same as the correct one
    assertEquals(true, newImage.equals(correctImage));
  }

  @Test
  public void testExecuteHorizontalVertical() {
    flippedCommand = new HorizontalFlip(model, squareImage,"square-horizontal");
    flippedCommand.execute();
    IPicture horizontalPuppy = model.getPicture("square-horizontal");
    flippedCommand = new VerticalFlip(model, horizontalPuppy,
            "square-horizontal-vertical");
    flippedCommand.execute();

    IPicture horizontalVertical = model.getPicture("square-horizontal-vertical");

    // Load correct image (Manipulated in Gimp)
    AllPicture correctSquare = ImageUtil.readPPM("res/square-horizontal-vertical.ppm");
    assertEquals(true, horizontalVertical.equals(correctSquare));

  }

  @Test
  public void testExecuteHorizontalVerticalPNG() {
    squareImage = ImageUtil.readAll("res/square.png");
    model = new PictureStorageModel();
    model.addPicture(squareImage, "square");
    flippedCommand = new HorizontalFlip(model, squareImage,"square-horizontal");
    flippedCommand.execute();
    IPicture horizontalPuppy = model.getPicture("square-horizontal");
    flippedCommand = new VerticalFlip(model, horizontalPuppy,
            "square-horizontal-vertical");
    flippedCommand.execute();

    IPicture horizontalVertical = model.getPicture("square-horizontal-vertical");

    // Load correct image (Manipulated in Gimp)
    IPicture correctSquare = ImageUtil.readAll("res/square-horizontal-vertical.png");
    assertEquals(true, horizontalVertical.equals(correctSquare));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testHorizontalModelNull() {
    flippedCommand = new HorizontalFlip(null, squareImage,"square-horizontal");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHorizontalPictureNull() {
    flippedCommand = new HorizontalFlip(model, null,"square-horizontal");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHorizontalNameNull() {
    flippedCommand = new HorizontalFlip(model, squareImage,null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testVerticalModelNull() {
    flippedCommand = new VerticalFlip(null, squareImage,"square-horizontal");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testVerticalPictureNull() {
    flippedCommand = new VerticalFlip(model, null,"square-horizontal");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testVerticalNameNull() {
    flippedCommand = new VerticalFlip(model, squareImage,null);
  }

  @Test
  public void testOutputMessage() {
    flippedCommand = new VerticalFlip(model, squareImage,"square-vertical");
    String output = flippedCommand.outputMessage();

    assertEquals("Flipped vertically and saved as square-vertical\n", output);

    flippedCommand = new HorizontalFlip(model, squareImage,"koala-horizontal");
    output = flippedCommand.outputMessage();
    assertEquals("Flipped horizontally and saved as koala-horizontal\n", output);
  }
}
