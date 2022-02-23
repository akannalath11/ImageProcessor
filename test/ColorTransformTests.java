import org.junit.Before;
import org.junit.Test;

import commands.ColorTransform;
import commands.Command;
import controllers.ImageUtil;
import model.IPicture;
import model.AllPicture;
import model.ImageProcessorModel;
import model.PictureStorageModel;

import static org.junit.Assert.assertEquals;

/**
 * This class is responsible for testing the color transform commands.
 */
public class ColorTransformTests {
  private IPicture squareImage;
  private Command colorTransform;
  private ImageProcessorModel model;

  @Before
  public void initData() {
    squareImage = ImageUtil.readPPM("res/square.ppm");
    model = new PictureStorageModel();
    model.addPicture(squareImage, "square");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testColorTransformModelNull() {
    colorTransform =
            new ColorTransform(null, squareImage, "sepia", "square-sepia");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testColorTransformPictureNull() {
    colorTransform =
            new ColorTransform(model, null, "sepia", "square-sepia");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testColorTransformTypeNull() {
    colorTransform =
            new ColorTransform(model, squareImage, null, "square-sepia");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testColorTransformReturnNameNull() {
    colorTransform = new ColorTransform(model, squareImage, "sepia", null);
  }

  @Test
  public void testExecuteSepiaTransform() {
    colorTransform = new ColorTransform(model, squareImage,
            "sepia", "square-sepia");
    colorTransform.execute();

    AllPicture correctImage = ImageUtil.readPPM("res/square-sepia.ppm");
    IPicture sepiaSquare = model.getPicture("square-sepia");
    assertEquals(true, sepiaSquare.equals(correctImage));
  }

  @Test
  public void testExecuteSepiaTransformPNG() {
    squareImage = ImageUtil.readAll("res/square.png");
    model = new PictureStorageModel();
    model.addPicture(squareImage, "square");
    colorTransform = new ColorTransform(model, squareImage,
            "sepia", "square-sepia");
    colorTransform.execute();

    IPicture correctImage = ImageUtil.readAll("res/square-sepia.png");
    IPicture sepiaSquare = model.getPicture("square-sepia");
    assertEquals(true, sepiaSquare.equals(correctImage));
  }

  @Test
  public void testOutputMessage() {
    colorTransform = new ColorTransform(model, squareImage, "sepia", "square2");
    String output = colorTransform.outputMessage();

    assertEquals("Applied color transform sepia and saved as square2\n", output);

    colorTransform = new ColorTransform(model, squareImage, "other", "square3");
    output = colorTransform.outputMessage();
    assertEquals("Applied color transform other and saved as square3\n", output);
  }
}