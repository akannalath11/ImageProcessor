package controllers;

/**
 * Represents a controller that runs the image processor with a GUI view. When run is called,
 * this class delegate's to the Feature object, which then launches its current GUI View.
 */
public class GUIController implements ImageProcessorController {
  private Features feature;

  /**
   * Creates a new GUI controller with a given model and view. The view will open to its starting
   * state and wait for user input. The controller will delegate necessary information to the
   * model and tell the view ot update when needed.
   * @param feature The current feature object that represents this controller abilities
   */
  public GUIController(Features feature) {
    this.feature = feature;
  }

  @Override
  public void runProcessor() throws IllegalStateException {
    feature.runProcessor();
  }
}
