package controllers;

import model.ImageProcessorModel;
import model.PictureStorageModel;
import view.ImageProcessorGUIView;
import view.GUIView;

/**
 * This class represents the main class that runs the GUI for the Image Processor. When run,
 * the GUI will display and wait for user input. This GUI view has the same functionality as the
 * text based scripting.
 */
public class ImageProcessorGUIMain {

  /**
   * The main method to run the GUI view. Creates a new GUI controller with an empty model
   * and the default GUI view.
   * @param args optional input for this application
   */
  public static void main(String[] args) {
    ImageProcessorModel model = new PictureStorageModel();
    ImageProcessorGUIView view = new GUIView();
    Features baseFeatures = new BaseProcessorFeatures(model, view);
    GUIController controller = new GUIController(baseFeatures);

    controller.runProcessor();
  }
}
