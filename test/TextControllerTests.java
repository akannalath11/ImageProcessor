import org.junit.Before;
import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import controllers.TextController;
import controllers.ImageUtil;
import model.AllPicture;
import model.IPicture;
import model.PictureStorageModel;
import model.ImageProcessorModel;
import view.ImageProcessorView;
import view.TextView;

import static org.junit.Assert.assertEquals;

/**
 * Tests that the controller works with different types of Readables and Appendable objects.
 */
public class TextControllerTests {
  private Reader moveSet;
  private ImageProcessorView mockView;
  private TextController controller;
  private Appendable viewLog;
  private StringBuilder modelLog;
  private ImageProcessorModel model;
  private ImageProcessorModel mockModel;
  private String programStart;

  @Before
  public void initData() {
    moveSet = new StringReader("load res/square.ppm square brighten -100 square square2"
            + " save square2 testConsole.txt q");
    model = new PictureStorageModel();
    viewLog = new StringBuilder();
    modelLog = new StringBuilder();
    mockView = new TextView(viewLog);
    mockModel = new MockModel(modelLog);

    programStart = "Welcome to the custom Image Processor. \n" +
            "The available commands are: \n" +
            "save 'imageName' 'outputFile.ppm'  |  load 'inputFile.ppm' 'imageName'\n" +
            "vertical-flip 'imageName' 'newName' |  horizontal-flip 'imageName' 'newName'\n" +
            "value-component 'imageName' 'newName'  |  luma-component 'imageName' 'newName'\n" +
            "intensity-component 'imageName' 'newName' |  red-component 'imageName' 'newName'\n" +
            "green-component 'imageName' 'newName'   |  blue-component 'imageName' 'newName'\n" +
            "brighten 'integerValue' 'imageName' 'newName'\n" +
            "darken 'integerValue 'imageName' 'newName'\n" +
            "blur 'imageName' 'newName' | sharpen 'imageName' 'newName'\n" +
            "sepia 'imageName' 'newName'\n" +
            "q or quit to exit program \n\n";
  }

  @Test
  public void testControllerWithOutput() {

    moveSet = new StringReader("load res/square.ppm square brighten 50 square square2"
            + " save square2 testConsole.ppm q");
    controller = new TextController(mockView, moveSet, model);

    controller.runProcessor();

    AllPicture savedImage = ImageUtil.readPPM("testConsole.ppm");
    AllPicture correctImage = ImageUtil.readPPM("res/square-brighter-by-50.ppm");
    // Check that the controller told the model to load, perform and action, and save to a picture
    // Check that this saved image is correct
    assertEquals(true, savedImage.equals(correctImage));
  }

  @Test
  public void testControllerInvalidCommandInput() {
    // Checks that if an invalid command is called it doesn't error
    moveSet = new StringReader("loadImage res/square.ppm square q");
    controller = new TextController(mockView, moveSet, model);
    controller.runProcessor();

    assertEquals(programStart
                    + "Invalid command given, please refer to the command list above!\n"
                    + "Invalid command given, please refer to the command list above!\n"
                    + "Invalid command given, please refer to the command list above!\n",
            viewLog.toString());
  }

  @Test
  public void testControllerInvalidTypeInput() {
    // Checks that if a valid command is called and the next is an illegal argument (String)
    moveSet = new StringReader("load res/square.ppm square brighten fifty square square2");
    controller = new TextController(mockView, moveSet, model);
    controller.runProcessor();

    assertEquals(programStart
                    + "Loaded Image named square\n"
                    + "Invalid integer value, re-enter the whole command with an integer!\n",
            viewLog.toString());
  }

  @Test
  public void testControllerMissingInput() {
    // Checks that if a valid command is called and the next is missing an argument
    moveSet = new StringReader("load res/square.ppm square brighten 50 square");
    controller = new TextController(mockView, moveSet, model);
    controller.runProcessor();

    assertEquals(programStart
                    + "Loaded Image named square\n"
                    + "Missing a command input, please re-enter the command!\n",
            viewLog.toString());
  }

  @Test
  public void testControllerInvalidImage() {
    // Checks that if a valid command is called and the next is missing an argument
    moveSet = new StringReader("load res/square-fake.ppm koala");
    controller = new TextController(mockView, moveSet, model);
    controller.runProcessor();

    assertEquals(programStart
                    + "Invalid input to command, please try again!\n",
            viewLog.toString());
  }

  @Test
  public void testControllerMissingImageSave() {
    // Checks that if a valid command is called and the next is missing an argument
    moveSet = new StringReader("load res/square.ppm");
    controller = new TextController(mockView, moveSet, model);
    controller.runProcessor();

    assertEquals(programStart
                    + "Missing a command input, please re-enter the command!\n",
            viewLog.toString());

  }

  private class MockModel implements ImageProcessorModel {
    StringBuilder log;
    private Map<String, IPicture> pictureList;

    private MockModel(StringBuilder log) {
      this.log = log;
      this.pictureList = new HashMap<>();
    }

    @Override
    public void addPicture(IPicture picture, String pictureName) throws IllegalArgumentException {
      log.append("Added picture to model with name " + pictureName + "\n");
      this.pictureList.put(pictureName, picture.clone());
    }

    @Override
    public IPicture getPicture(String pictureName) throws IllegalArgumentException {
      log.append("Got picture with stored name " + pictureName + "\n");
      for (Map.Entry<String, IPicture> p : pictureList.entrySet()) {
        if (p.getKey().equals(pictureName)) {
          return p.getValue();
        }
      }
      throw new IllegalArgumentException("No picture is loaded with that name!");
    }

    @Override
    public String getCurrCount() {
      return "";
    }


    @Override
    public void undo() {
      log.append("Undo");
    }

    @Override
    public int[] colorValues(String type) {
      return new int[0];
    }

    @Override
    public String getLastCount() {
      return "";
    }
  }

  @Test
  public void testControllerMockFlip() {
    moveSet = new StringReader("load res/square.ppm square horizontal-flip square square2"
            + " vertical-flip square2 square3 save square3 testConsole.txt q");
    controller = new TextController(mockView, moveSet, mockModel);
    controller.runProcessor();

    // Check that the expected methods in the model are called and run from this controller
    assertEquals("Added picture to model with name square\n" +
            "Got picture with stored name square\n" +
            "Added picture to model with name square2\n" +
            "Got picture with stored name square2\n" +
            "Added picture to model with name square3\n" +
            "Got picture with stored name square3\n", modelLog.toString());
    assertEquals(programStart +
            "Loaded Image named square\n" +
            "Flipped horizontally and saved as square2\n" +
            "Flipped vertically and saved as square3\n" +
            "Saved to testConsole.txt\n", viewLog.toString());
  }

  @Test
  public void testControllerMockGreyscale1() {
    moveSet = new StringReader("load res/square.ppm square red-component square square2"
            + " blue-component square square3  blue-component square square4"
            + " save square4 testConsole.txt q");
    controller = new TextController(mockView, moveSet, mockModel);
    controller.runProcessor();

    // Check that the expected methods in the model are called and run from this controller
    assertEquals("Added picture to model with name square\n" +
            "Got picture with stored name square\n" +
            "Added picture to model with name square2\n" +
            "Got picture with stored name square\n" +
            "Added picture to model with name square3\n" +
            "Got picture with stored name square\n" +
            "Added picture to model with name square4\n" +
            "Got picture with stored name square4\n", modelLog.toString());
    assertEquals(programStart
            + "Loaded Image named square\n" +
            "Converted to red greyscale and saved as square2\n" +
            "Converted to blue greyscale and saved as square3\n" +
            "Converted to blue greyscale and saved as square4\n" +
            "Saved to testConsole.txt\n", viewLog.toString());
  }

  @Test
  public void testControllerGreyScaleSepia() {
    moveSet = new StringReader("load res/square.ppm square luma-component square square2"
            + " value-component square square3 intensity-component square square4"
            + " sepia square square5 "
            + " save square4 testConsole.txt q");
    controller = new TextController(mockView, moveSet, mockModel);
    controller.runProcessor();

    // Check that the expected methods in the model are called and run from this controller
    assertEquals("Added picture to model with name square\n" +
            "Got picture with stored name square\n" +
            "Added picture to model with name square2\n" +
            "Got picture with stored name square\n" +
            "Added picture to model with name square3\n" +
            "Got picture with stored name square\n" +
            "Added picture to model with name square4\n" +
            "Got picture with stored name square\n" +
            "Added picture to model with name square5\n" +
            "Got picture with stored name square4\n", modelLog.toString());
    assertEquals(programStart
            + "Loaded Image named square\n" +
            "Converted to luma greyscale and saved as square2\n" +
            "Converted to value greyscale and saved as square3\n" +
            "Converted to intensity greyscale and saved as square4\n" +
            "Applied color transform sepia and saved as square5\n" +
            "Saved to testConsole.txt\n", viewLog.toString());
  }

  @Test
  public void testControllerMockBrightness() {
    moveSet = new StringReader("load res/square.ppm square brighten 50 square square-brighter"
            + " darken 100 square-brighter square-dark save square-dark testConsole2.ppm");
    controller = new TextController(mockView, moveSet, mockModel);
    controller.runProcessor();

    // Check that the expected methods in the model are called and run from this controller
    assertEquals("Added picture to model with name square\n"
            + "Got picture with stored name square\n"
            + "Added picture to model with name square-brighter\n"
            + "Got picture with stored name square-brighter\n"
            + "Added picture to model with name square-dark\n"
            + "Got picture with stored name square-dark\n", modelLog.toString());
    assertEquals(programStart
            + "Loaded Image named square\n"
            + "Brightened image by 50 and saved as square-brighter\n"
            + "Darkened image by 100 and saved as square-dark\n"
            + "Saved to testConsole2.ppm\n", viewLog.toString());
  }

  @Test
  public void testControllerFilters() {
    moveSet = new StringReader("load res/square.ppm square blur square square-blur"
            + " sharpen square square-sharp save square-blur testConsole2.ppm"
            + " save square-sharp testConsole.ppm");
    controller = new TextController(mockView, moveSet, mockModel);
    controller.runProcessor();

    // Check that the expected methods in the model are called and run from this controller
    assertEquals("Added picture to model with name square\n"
            + "Got picture with stored name square\n"
            + "Added picture to model with name square-blur\n"
            + "Got picture with stored name square\n"
            + "Added picture to model with name square-sharp\n"
            + "Got picture with stored name square-blur\n"
            + "Got picture with stored name square-sharp\n", modelLog.toString());
    assertEquals(programStart
            + "Loaded Image named square\n"
            + "Filtered picture and saved as square-blur\n"
            + "Filtered picture and saved as square-sharp\n"
            + "Saved to testConsole2.ppm\n"
            + "Saved to testConsole.ppm\n", viewLog.toString());
  }

  @Test
  public void testControllerNoInputAfterBlur() {
    moveSet = new StringReader("load res/square.ppm square blur square ");
    controller = new TextController(mockView, moveSet, mockModel);
    controller.runProcessor();

    // Check that the expected methods in the model are called and run from this controller
    assertEquals("Added picture to model with name square\n"
            + "Got picture with stored name square\n", modelLog.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  // checks exception readable field null
  public void testControllerReadableNull() {
    new TextController(mockView, null, model);
  }

  @Test(expected = IllegalArgumentException.class)
  // checks exception pictureView field null
  public void testControllerPictureViewNull() {
    new TextController(null, moveSet, model);
  }

  @Test(expected = IllegalArgumentException.class)
  // checks exception model field null
  public void testControllerModelViewNull() {
    new TextController(mockView, moveSet, null);
  }

}
