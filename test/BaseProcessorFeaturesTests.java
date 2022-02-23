import org.junit.Before;
import org.junit.Test;

import java.awt.image.BufferedImage;

import controllers.BaseProcessorFeatures;
import controllers.Features;
import controllers.GUIController;
import model.ImageProcessorModel;
import model.PictureStorageModel;
import view.ImageProcessorGUIView;

import static org.junit.Assert.assertEquals;

/**
 * Tests the BaseProcessorFeaturesTests. Ensures that the controller accepts inputs from the GUI
 * view and passes data correctly to the model.
 */
public class BaseProcessorFeaturesTests {
  private Features baseFeatures;
  private StringBuilder viewLog;

  @Before
  public void initData() {
    ImageProcessorGUIView view;
    ImageProcessorModel model;
    GUIController controller;
    viewLog = new StringBuilder();
    model = new PictureStorageModel();
    view = new MockGUIView(viewLog);
    baseFeatures = new BaseProcessorFeatures(model, view);
    controller = new GUIController(baseFeatures);
    controller.runProcessor();
  }

  /**
   * Represents a mock GUIView. Instead of updating an actual GUI, this class tracks every time
   * a public method is called in a log.
   */
  private class MockGUIView implements ImageProcessorGUIView {
    StringBuilder log;

    MockGUIView(StringBuilder log) {
      this.log = log;
    }

    @Override
    public void addFeatures(Features features) {
      log.append("Add features called\n");
    }

    @Override
    public void setImage(BufferedImage image) {
      log.append("Received a new buffered image and updated view\n");
    }

    @Override
    public void drawHistogram(int[] redValues, int[] greenValues, int[] blueValues) {
      log.append("Received new histogram data and updated view\n");
    }

    @Override
    public void pictureError() {
      log.append("Updated view with error pop-up\n");
    }
  }

  /////////// BASE TESTING ///////////////////////////////////////////
  @Test
  public void testLoadImage() {
    // Check the controller calls the correct load method, and calls the correct view methods
    assertEquals("Add features called\n", viewLog.toString());
    baseFeatures.loadPicture("res/square.ppm");
    assertEquals("Add features called\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n", viewLog.toString());
  }

  @Test
  public void testSaveImage() {
    // Check the controller calls the correct load method, and calls the correct view methods
    assertEquals("Add features called\n", viewLog.toString());
    baseFeatures.loadPicture("res/square.ppm");
    // In this case save shouldn't update the view at all, so the log should remain the same
    baseFeatures.savePicture("res/square.ppm");
    assertEquals("Add features called\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n", viewLog.toString());
  }

  @Test
  public void testFlip() {
    // Check the controller calls the correct load method, and calls the correct view methods
    assertEquals("Add features called\n", viewLog.toString());
    baseFeatures.loadPicture("res/square.ppm");
    baseFeatures.flipPicture("horizontal");
    // Checks the view received a new image simulating updating the view (horizontal flip)
    assertEquals("Add features called\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n"
            + "Received a new buffered image and updated view\n", viewLog.toString());
    baseFeatures.flipPicture("vertical");
    // Checks the view received a new image simulating updating the view (vertical flip)
    assertEquals("Add features called\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n"
            + "Received a new buffered image and updated view\n"
            + "Received a new buffered image and updated view\n", viewLog.toString());
  }

  @Test
  public void testFilter() {
    // Check the controller calls the correct load method, and calls the correct view methods
    assertEquals("Add features called\n", viewLog.toString());
    baseFeatures.loadPicture("res/square.ppm");
    baseFeatures.filter("blur");
    // Checks the view received a new image simulating updating the view (blur)
    // Check the view was told to update the histogram
    assertEquals("Add features called\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n", viewLog.toString());
    baseFeatures.filter("sharpen");
    // Checks the view received a new image simulating updating the view (sharpen)
    // Check the view was told to update the histogram
    assertEquals("Add features called\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n", viewLog.toString());
  }

  @Test
  public void testGreyscale() {
    assertEquals("Add features called\n", viewLog.toString());
    baseFeatures.loadPicture("res/square.ppm");
    baseFeatures.greyscale("red");
    // Checks the view received a new image simulating updating the view (red greyscale)
    // Check the view was told to update the histogram
    assertEquals("Add features called\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n", viewLog.toString());
    baseFeatures.greyscale("intensity");
    // Checks the view received a new image simulating updating the view (intensity grey scale)
    // Check the view was told to update the histogram
    assertEquals("Add features called\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n", viewLog.toString());
  }

  @Test
  public void testBrighten() {
    assertEquals("Add features called\n", viewLog.toString());
    baseFeatures.loadPicture("res/square.ppm");
    baseFeatures.brighten(50);
    // Checks the view received a new image simulating updating the view
    // Check the view was told to update the histogram
    assertEquals("Add features called\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n", viewLog.toString());
    baseFeatures.brighten(-50);
    // Checks the view received a new image simulating updating the view
    // Check the view was told to update the histogram
    assertEquals("Add features called\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n", viewLog.toString());

  }

  @Test
  public void testColorTransform() {
    assertEquals("Add features called\n", viewLog.toString());
    baseFeatures.loadPicture("res/square.ppm");
    baseFeatures.colorTransform("sepia");
    // Checks the view received a new image simulating updating the view (sepia)
    // Check the view was told to update the histogram
    assertEquals("Add features called\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n", viewLog.toString());
  }

  @Test
  public void testUndo() {
    assertEquals("Add features called\n", viewLog.toString());
    baseFeatures.loadPicture("res/square.ppm");
    baseFeatures.colorTransform("sepia");
    baseFeatures.undo();
    // Checks the view received a new image simulating updating the view (sepia)
    // Checks the view received a new image after undo transform was called
    assertEquals("Add features called\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n", viewLog.toString());
  }

  ///////////////// ERROR TESTING /////////////////////////////////////////

  @Test
  public void testNoPictureLoaded() {
    assertEquals("Add features called\n", viewLog.toString());
    baseFeatures.flipPicture("vertical");

    // Checks the controller told the view to display an error message since no photo is loaded
    assertEquals("Add features called\n"
            + "Updated view with error pop-up\n", viewLog.toString());

    baseFeatures.flipPicture("horizontal");
    baseFeatures.colorTransform("sepia");
    baseFeatures.filter("blur");
    baseFeatures.filter("sharpen");
    baseFeatures.brighten(50);

    assertEquals("Add features called\n"
            + "Updated view with error pop-up\n"
            + "Updated view with error pop-up\n"
            + "Updated view with error pop-up\n"
            + "Updated view with error pop-up\n"
            + "Updated view with error pop-up\n"
            + "Updated view with error pop-up\n", viewLog.toString());
  }

  @Test
  public void testEmptyFilename() {
    assertEquals("Add features called\n", viewLog.toString());
    baseFeatures.loadPicture("");

    // Simulates when the view passes an empty filename to the controller (for load)
    // Instead of throwing error, controller will not call load and ignores input
    assertEquals("Add features called\n", viewLog.toString());


    baseFeatures.loadPicture("res/puppy.ppm");
    assertEquals("Add features called\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n", viewLog.toString());
    baseFeatures.savePicture("");

    // Simulates when the view passes an empty filename to the controller (for save)
    // Instead of throwing error, controller will not call load and ignores input
    assertEquals("Add features called\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n", viewLog.toString());
  }

  @Test
  public void testInvalidUndo() {
    baseFeatures.loadPicture("res/puppy.ppm");
    // Load picture
    assertEquals("Add features called\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n", viewLog.toString());

    baseFeatures.undo();
    // Checks undo doesn't throw error when a picture is not able to revert to a previous image
    assertEquals("Add features called\n"
            + "Received a new buffered image and updated view\n"
            + "Received new histogram data and updated view\n", viewLog.toString());
  }

}
