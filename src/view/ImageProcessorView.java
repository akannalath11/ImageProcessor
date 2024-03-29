package view;

import java.io.IOException;

/**
 * This interface represents the view for an ImageProcessor. The view can display messages to
 * an Appendable notifying user of any changes made.
 */
public interface ImageProcessorView {

  /**
   * Render a specific message to the provided data destination.
   *
   * @param message the message to be transmitted
   * @throws IOException if transmission of the board to the provided data destination fails
   */
  void renderMessage(String message) throws IOException;
}
