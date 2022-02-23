import org.junit.Before;
import org.junit.Test;

import commands.Command;
import commands.FilterImage;
import controllers.ImageUtil;
import model.IPicture;
import model.AllPicture;
import model.ImageProcessorModel;
import model.PictureStorageModel;

import static org.junit.Assert.assertEquals;

/**
 * This class is responsible for testing the filter command class. This includes any image filter
 * such as blurring or sharpening.
 */
public class FilterTests {
  private IPicture squareImage;
  private Command filterCommand;
  private ImageProcessorModel model;

  @Before
  public void initData() {
    squareImage = ImageUtil.readPPM("res/square.ppm");
    model = new PictureStorageModel();
    model.addPicture(squareImage, "square");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFilterModelNull() {
    filterCommand = new FilterImage(null, squareImage,
            "blur", "square-blur");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFilterPictureNull() {
    filterCommand = new FilterImage(model, null, "blur", "square-blur");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFilterReturnNameNull() {
    filterCommand = new FilterImage(model, squareImage, null, "square-blur");
  }

  @Test
  public void testExecuteBlur() {
    filterCommand = new FilterImage(model, squareImage, "square-blur","blur");
    filterCommand.execute();

    AllPicture correctImage = ImageUtil.readPPM("res/square-blur.ppm");
    IPicture valueSquare = model.getPicture("square-blur");
    assertEquals(true, valueSquare.equals(correctImage));
  }

  @Test
  public void testExecuteSharpen() {
    filterCommand = new FilterImage(model, squareImage, "square-sharp", "sharpen");
    filterCommand.execute();

    AllPicture correctImage = ImageUtil.readPPM("res/square-sharpen.ppm");
    IPicture valueSquare = model.getPicture("square-sharp");
    assertEquals(true, valueSquare.equals(correctImage));
  }

  @Test
  public void testExecuteSharpenPNG() {
    squareImage = ImageUtil.readAll("res/square.png");
    model = new PictureStorageModel();
    model.addPicture(squareImage, "square");

    filterCommand = new FilterImage(model, squareImage, "square-sharp", "sharpen");
    filterCommand.execute();

    IPicture correctImage = ImageUtil.readAll("res/square-sharpen.png");
    IPicture valueSquare = model.getPicture("square-sharp");
    assertEquals(true, valueSquare.equals(correctImage));
  }

  @Test
  public void testOutputMessage() {
    filterCommand = new FilterImage(model, squareImage,"square-blur", "blur");
    String output = filterCommand.outputMessage();

    assertEquals("Filtered picture and saved as square-blur\n", output);

    filterCommand = new FilterImage(model, squareImage,"square-sharpen","sharpen");
    output = filterCommand.outputMessage();
    assertEquals("Filtered picture and saved as square-sharpen\n", output);
  }
}