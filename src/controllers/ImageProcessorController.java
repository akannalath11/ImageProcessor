package controllers;

/**
 * Represents a controller for the Image Processing Application. This interface determines the
 * current model based on user input. Responsible for handling all inputs and exchanging this
 * information with the corresponding commands. Asks the user for specific command inputs and
 * determines when to exit the application.
 */
public interface ImageProcessorController {

  /**
   * Runs the Image Processing application. Depending on the view, the application will run,
   * and wait for user input. The controller will then delegate user input to the view and model
   * as needed.
   *
   * @throws IllegalStateException If transmission to the view fails or an attempt to read fails
   */
  void runProcessor() throws IllegalStateException;
}
