package model;

/**
 * This interface represents a single Pixel of an Image and its respective data values.
 */
public interface IPixel {

  /**
   * Produces a String representation of this pixel displaying any stored data values. Each
   * pixel data point will be produced individually seperated by a single space.
   * @return the string version of the RGB vales
   */
  @Override
  String toString();

  /**
   * Clones a given IPixel by created a new object with the same exact data.
   * @return A deep copy of the current Pixel
   */
  IPixel clone();

  /**
   * Checks if the current Pixel is equal to another Object. Returns true if all the Pixels fields
   * are the same as the given Pixel. Compares the red, green, blue, and alpha color values.
   * If the IPixel does not have alpha values stored, it will default to 255.
   * @param other The object to check equality with
   * @return Return true if the objects are the same, false otherwise
   */
  @Override
  boolean equals(Object other);

  /**
   * Overrides the default hashcode value to produce a new object hash based on the components
   * of this Pixel all summed together.
   * @return Return the summed hash of all of a pixels values
   */
  @Override
  int hashCode();

  /**
   * Changes a pixel's data values by adding the current value plus an integer number. If a
   * field goes over the max, it will be set to the max pixel value for the current Picture.
   * If a field summed with the given number goes under 0 it will be set to 0.
   *
   * @param value    The value to change each field by
   * @param maxValue The maximum value this field can be
   */
  void brighten(int value, int maxValue);

  /**
   * Updates the fields each of the pixel values by performing a greyscale image conversion
   * of a given type.
   *
   * @param type The type of greyscale conversion
   */
  void greyScale(String type);

  /**
   * Returns the value of a color component based on the color type given.
   * @param color the color component to return
   * @return a double of the color components value
   * @throws IllegalArgumentException if this pixel does not have the given color value
   */
  double getValue(String color) throws IllegalArgumentException;

  /**
   * Sets an IPixel's RGB values to the given values. If the values are out of the bounds, it
   * will clamp them to 0 for the min or to the given max color value.
   * @param r the red value to set this pixel to
   * @param g the green value to set this pixel to
   * @param b the blue value to set this pixel to
   * @param maxValue the max color value for this pixel
   */
  void set(int r, int g, int b, int maxValue);

}
