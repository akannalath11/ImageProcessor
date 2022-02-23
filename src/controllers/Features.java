package controllers;

/**
 * Represents the handling features of a controller used in a GUI for the ImageProcessor. A GUI
 * controller should be able to perform each of the given features, passing data inputting from
 * the View along to the model. After features are performed, the controller must update the view
 * to demonstrate the current model state.
 */
public interface Features {

  /**
   * Runs the GUI View passed into this features object. This feature is responsible for
   * acting as the controller, passing data from the model to the view.
   */
  void runProcessor();

  /**
   * Opens a Picture from a root path given by the current View. The file location is passed to
   * the model to load the image. Updates the view to show the loaded image.
   * @param fileLocation The root location of the image to be loaded
   */
  void loadPicture(String fileLocation);

  /**
   * Saves a Picture to a root path given by the current view. The file location is passed to the
   * model to save the image with.
   * @param fileLocation The root save path for the image
   */
  void savePicture(String fileLocation);

  /**
   * Flips a Picture horizontally or vertically based on the given type. Calls the model's method
   * on the current IPicture object the view is displaying.
   * @param type Type of flip to perform on the current IPicture
   */
  void flipPicture(String type);

  /**
   * Brightens a Picture by a given integer value. Calls the model's method
   * on the current IPicture object the view is displaying.
   * @param value the integer amount to brighten the image by
   */
  void brighten(int value);

  /**
   * Applies a filter to a Picture based on the given type. Calls the model's method
   * on the current IPicture object the view is displaying.
   * @param type Type of filter to perform on the current IPicture
   */
  void filter(String type);

  /**
   * Applies a greyscale to a Picture based on the given type. Calls the model's method
   * on the current IPicture object the view is displaying.
   * @param type Type of greyscale to perform on the current IPicture
   */
  void greyscale(String type);

  /**
   * Applies a color transformation to a Picture based on the given type. Calls the model's method
   * on the current IPicture object the view is displaying.
   * @param type Type of color transformation to perform on the current IPicture
   */
  void colorTransform(String type);

  /**
   * Undos the last transformation performed on the model. Sets the current view to display the
   * previous IPicture.
   */
  void undo();

  void downscaleImage(String heightRatio, String widthRatio);
}
