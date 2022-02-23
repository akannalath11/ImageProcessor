package view;

import java.awt.image.BufferedImage;

import controllers.Features;

/**
 * This interface represents a GUI View for our ImageProcessor application. This interface holds
 * methods that are needed for the controller to call on. Specifically these methods help
 * the view take in new data passed from the controller to update the displayed view.
 */
public interface ImageProcessorGUIView {

  /**
   * Takes in a Features object, that represents the abilities of the controller. Initializes
   * methods that run in the controller based on user input from the graphical interface.
   * @param features Controllers abilities this view can refer to
   */
  void addFeatures(Features features);

  /**
   * Takes in a buffered image and sets it to the current image being displayed in the center
   * of the JPanel. This image represents the current state of the edited imge
   * @param image The current image to display
   */
  void setImage(BufferedImage image);

  /**
   * Updates the fields of the Histogram class based on the color values passed to the view from
   * the controller. When called, the current histogram is redrawn with new axis labels
   * based on the new frequencies.
   * @param redValues The frequencies for each red color value 0-255
   * @param greenValues The frequencies for each green color value 0-255
   * @param blueValues The frequencies for each blue color value 0-255
   */
  void drawHistogram(int[] redValues, int[] greenValues, int[] blueValues);

  /**
   * Updates the GUI to show a separate pop up window that displays an error message.
   */
  void pictureError();

}
