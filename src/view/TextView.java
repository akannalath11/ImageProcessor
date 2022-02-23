package view;

import java.io.IOException;

/**
 * This PictureView class renders a message and send it to the view.
 */
public class TextView implements ImageProcessorView {
  private Appendable output;

  /**
   * Initializes the current text model into a text state.
   *
   * @param output The Object the view uses as its destination
   * @throws IllegalArgumentException If the Appendable is null
   */
  public TextView(Appendable output) {
    if (output == null) {
      throw new IllegalArgumentException("Given Appendable cannot be null!");
    }
    this.output = output;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.output.append(message);
  }
}
