package model;

/**
 * Interface that represents the model for the Image Processor. Allows a model to add pictures
 * to itself and check if a current picture is already stored.
 */
public interface ImageProcessorModel {

  /**
   * This method saves a IPicture by adding it to the model. If a Picture is saved with the given
   * name, it will override the Picture data to the new Picture.
   *
   * @param picture     represents the Picture that is to be added
   * @param pictureName represents the name mapped to the Picture
   * @throws IllegalArgumentException if the Picture or storage name is null
   */
  void addPicture(IPicture picture, String pictureName) throws IllegalArgumentException;

  /**
   * Gets the specific image from the list of pictures based on the name of the image.
   *
   * @param pictureName represents the name of the specific image
   * @return the actual image that is associated with the pictureName
   * @throws IllegalArgumentException if the Picture is not stored in this current Controller
   */
  IPicture getPicture(String pictureName) throws IllegalArgumentException;

  /**
   * Outputs the next number to store an IPicture at using the count system. The current count
   * represents the next number to store a photo with.
   * @return The next Integer value to store a photo with
   */
  String getCurrCount();

  /**
   * Returns the last number added to the Stack. Keeps track of how many photos have been added
   * to this model.
   * @return The current number of photos added to this model
   */
  String getLastCount();

  /**
   * Removes the most recent IPicture from this model to return to the
   * previous IPicture.
   */
  void undo();

  /**
   * Gets the current IPicture and delegates to the colorValues method in the IPicture interface.
   * Returns the frequency for each pixel value based on the given type.
   * @param type The color value type to calculate frequencies for
   * @return the frequency for each pixel value based on the given type
   */
  int[] colorValues(String type);
}
