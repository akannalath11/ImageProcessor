import org.junit.Before;
import org.junit.Test;

import commands.Command;
import commands.GreyScale;
import controllers.ImageUtil;
import model.IPicture;
import model.AllPicture;
import model.ImageProcessorModel;
import model.PictureStorageModel;

import static org.junit.Assert.assertEquals;

/**
 * This class is responsible for testing the greyscale commands.
 */
public class GreyScaleTests {
  private IPicture squareImage;
  private Command greyscaleCommand;
  private ImageProcessorModel model;

  @Before
  public void initData() {
    squareImage = ImageUtil.readPPM("res/square.ppm");
    model = new PictureStorageModel();
    model.addPicture(squareImage, "square");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreyscaleModelNull() {
    greyscaleCommand = new GreyScale(null, squareImage, "value",
            "square-value");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreyscalePictureNull() {
    greyscaleCommand = new GreyScale(model, null, "value", "square-value");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreyscaleTypeNull() {
    greyscaleCommand = new GreyScale(model, squareImage, null, "square-value");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreyscaleReturnNameNull() {
    greyscaleCommand = new GreyScale(model, squareImage, "red", null);
  }

  @Test
  public void testExecuteValueGreyScale() {
    greyscaleCommand = new GreyScale(model, squareImage, "value", "square-value");
    greyscaleCommand.execute();

    AllPicture correctImage = ImageUtil.readPPM("res/square-value.ppm");
    IPicture valueSquare = model.getPicture("square-value");
    assertEquals(true, valueSquare.equals(correctImage));
  }

  @Test
  public void testExecuteLumaGreyScale() {
    greyscaleCommand = new GreyScale(model, squareImage, "luma", "square-luma");
    greyscaleCommand.execute();

    AllPicture correctImage = ImageUtil.readPPM("res/square-luma.ppm");
    IPicture valueSquare = model.getPicture("square-luma");
    assertEquals(true, valueSquare.equals(correctImage));
  }

  @Test
  public void testExecuteIntensityGreyScale() {
    greyscaleCommand = new GreyScale(model, squareImage,
            "intensity", "square-intensity");
    greyscaleCommand.execute();

    AllPicture correctImage = ImageUtil.readPPM("res/square-intensity.ppm");
    IPicture valueSquare = model.getPicture("square-intensity");
    assertEquals(true, valueSquare.equals(correctImage));
  }

  @Test
  public void testExecuteRedGreyScale() {
    greyscaleCommand = new GreyScale(model, squareImage,
            "red", "square-red");
    greyscaleCommand.execute();

    AllPicture correctImage = ImageUtil.readPPM("res/square-red.ppm");
    IPicture valueSquare = model.getPicture("square-red");
    assertEquals(true, valueSquare.equals(correctImage));
  }

  @Test
  public void testExecuteGreenGreyScale() {
    greyscaleCommand = new GreyScale(model, squareImage,
            "green", "square-green");
    greyscaleCommand.execute();

    AllPicture correctImage = ImageUtil.readPPM("res/square-green.ppm");
    IPicture valueSquare = model.getPicture("square-green");
    assertEquals(true, valueSquare.equals(correctImage));
  }

  @Test
  public void testExecuteBlueGreyScale() {
    greyscaleCommand = new GreyScale(model, squareImage,
            "blue", "square-blue");
    greyscaleCommand.execute();

    AllPicture correctImage = ImageUtil.readPPM("res/square-blue.ppm");
    IPicture valueSquare = model.getPicture("square-blue");
    assertEquals(true, valueSquare.equals(correctImage));
  }

  @Test
  public void testExecuteBlueGreyScalePNG() {
    squareImage = ImageUtil.readAll("res/square.png");
    model = new PictureStorageModel();
    model.addPicture(squareImage, "square");
    greyscaleCommand = new GreyScale(model, squareImage,
            "blue", "square-blue");
    greyscaleCommand.execute();

    IPicture correctImage = ImageUtil.readAll("res/square-blue.png");
    IPicture valueSquare = model.getPicture("square-blue");
    assertEquals(true, valueSquare.equals(correctImage));
  }

  @Test
  public void testOutputMessage() {
    greyscaleCommand = new GreyScale(model, squareImage, "red", "koala-red");
    String output = greyscaleCommand.outputMessage();

    assertEquals("Converted to red greyscale and saved as koala-red\n", output);

    greyscaleCommand = new GreyScale(model, squareImage, "luma", "square-luma");
    output = greyscaleCommand.outputMessage();
    assertEquals("Converted to luma greyscale and saved as square-luma\n", output);
  }
}