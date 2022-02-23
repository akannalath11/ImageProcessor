package model;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Represents a Picture that a ImageProcessor model can store. This interface represents all the
 * operations the Image Processor can perform on a single Image.
 */
public interface IPicture {

  /**
   * Returns all the fields of this picture appended to each other. For each pixel in this Picture,
   * all the color values are appended with spaces between them.
   *
   * @return a String representation of this IPicture
   */
  @Override
  String toString();

  /**
   * Outputs a Picture to a given filename. The file type is determined from the last
   * three digits of the filename (.png, .ppm, etc.).
   *
   * @param filename the Filename to output this Picture to.
   */
  void save(String filename);

  /**
   * Clones a current IPicture and returns a copy with the exact same data including all the
   * pixels in the IPixel array.
   *
   * @return A clone of this IPicture
   */
  IPicture clone();

  /**
   * Determines if a IPicture is equal to another IPicture. All the fields of a Picture must be
   * equal to another, including all the IPixels in a Pictures 2D array. For an IPixel to be the
   * same, equals checks if the red, green, blue, and alpha values are the same for all IPixels.
   * If there is no alpha value stored, it will compare it to the default of 255.
   *
   * @param other Object to compare equality to
   * @return True if this object is the same as the given object, false otherwise
   */
  @Override
  boolean equals(Object other);

  /**
   * Outputs an integer representation of all the hashes summed for each field of a Picture.
   *
   * @return an integer hash value for a Picture
   */
  @Override
  int hashCode();

  /**
   * Flips a Picture Vertically by mutating its 2D Array of Pixels.
   */
  void verticalFlip();

  /**
   * Flips and image horizontally by mutating its 2D Array of Pixels.
   */
  void horizontalFlip();

  /**
   * Changes the color values for the for all the SinglePixels in this
   * Picture by a given value.
   *
   * @param value The value to change each field by
   */
  void brightenImage(int value);

  /**
   * Updates the color values of this image to be changed to a greyscale based on each pixel's
   * value and the type that is given (the maximum value of the components for each pixel).
   */
  void greyScale(String type);

  /**
   * Performs a color transformation based on the values of an array list representing a 3x3 matrix.
   *
   * @param arrayValues The values that make the 3x3 transformation matrix
   * @throws IllegalArgumentException if the list is not size 9
   */
  void colorTransform(List<Double> arrayValues) throws IllegalArgumentException;

  /**
   * Filters an image by calculating a new value for each of the RGB colors by applying the values
   * of a kernel with a given radius to each pixel. If the bounds is 1, the kernel will span
   * one up, one down, one left, and one right from the center pixel making the kernel size
   * a 3x3, essentially making a border around the center pixel (2 bounds is 5x5 etc.). The given
   * list should order the kernel values by rows, from left to right, listing the second row
   * after the first.
   *
   * @param filterValues The values of the kernel to apply
   * @param bounds       The size to expand the kernel relative to the starting point
   */
  void filter(List<Double> filterValues, int bounds);

  /**
   * Produces a buffered Image with the current IPicture data. The buffered image is based on the
   * size of the IPixel array, and each pixel is created with the current RGBA color values.
   * @return A buffered Image represents the current IPicture data
   */
  BufferedImage getBufferedImage();

  /**
   * Produces a list of integers that is composed of the frequency of each color value 0-255. The
   * type can be red, green, or blue, and this method will return how many pixels in this IPicture
   * have each color value from the range 0-255.
   * @param type The RGB color type to calculate
   * @return A list of integers based on the frequency of each color value
   */
  int[] colorValues(String type);

  /**
   * Downsizes this Image given a height and width ratio. Multiples the current height and width
   * by the given ratios to determine the size. Each pixel of the smaller image gets mapped
   * to a representative pixel in the current image determining the color value.
   * @param heightRatio The ratio of the current height for the outputted image height
   * @param widthRatio The ratio of the current width for the outputted image width
   * @return A downsized version of this image in the given ratio aspect
   */
  IPicture downSize(double heightRatio, double widthRatio);

  void maskImage(IPicture maskedImage);

  boolean[][] validPixels();
}
