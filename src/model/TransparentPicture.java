package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents an image that stores transparent values. Instead of including these images
 * within AllPicture, this class stores the Pixel data as an array of RGBA values, keeping track
 * of alpha transparency.
 */
public class TransparentPicture extends AbstractPicture {
  private RGBAPixel[][] pixelArray;

  /**
   * Creates a Picture with a given width, height, max Color value, and list of all the
   * RGBA pixels that make up this image.
   *
   * @param width      Width of this image(in pixels)
   * @param height     Height of this image(in pixels)
   * @param maxValue   The maximum color value of a single pixel field (R, G, B fields)
   * @param pixelArray A 2D array that holds every RGBA pixel in this image
   */
  public TransparentPicture(int width, int height, int maxValue, RGBAPixel[][] pixelArray) {
    super(width, height, maxValue, pixelArray);
    this.pixelArray = pixelArray;
  }

  @Override
  public TransparentPicture clone() {
    int newWidth = this.width;
    int newHeight = this.height;
    int maxValue = this.maxValue;
    RGBAPixel[][] newPixelArray = new RGBAPixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        RGBAPixel currPixel = this.pixelArray[i][j];
        newPixelArray[i][j] = currPixel.clone();
      }
    }
    return new TransparentPicture(newWidth, newHeight, maxValue, newPixelArray);
  }

  @Override
  public IPicture downSize(double heightRatio, double widthRatio) {
    int newHeight = (int) (this.height * heightRatio);
    int newWidth = (int) (this.width * widthRatio);

    RGBAPixel[][] scaledArray = new RGBAPixel[newHeight][newWidth];

    for (int i = 0; i < newHeight; i++) {
      for (int j = 0; j < newWidth; j++) {
        double heightValue = (i / heightRatio);
        double widthValue = (j / widthRatio);
        RGBAPixel currPixel = this.calculatePixel(heightValue, widthValue);
        scaledArray[i][j] = currPixel.clone();
      }
    }

    return new TransparentPicture(newWidth, newHeight, this.maxValue, scaledArray);
  }

  /**
   * Calculates the correct Pixel values when mapping one pixel to that of a large image.
   * If the pixel location is represented by floating points, this method takes an average of the
   * four surrounding pixels to help retain pixel data. This calculation helps to minimize
   * visual artifacts.
   *
   * @param height The mapped pixel height value
   * @param width  The mapped pixel width value
   * @return The correct pixel the height and width are mapped to. In the case the given
   *     values are not whole numbers, return an average of the four surrounding pixels
   */
  private RGBAPixel calculatePixel(double height, double width) {
    List<String> colors = new ArrayList<>(Arrays.asList("red", "green", "blue"));
    ArrayList<Integer> newValues = new ArrayList<>();

    // If the values are whole numbers, simply return the index
    if ((height % 1 == 0) || (width % 1 == 0)) {
      return this.pixelArray[(int) height][(int) width];
    } else {
      int ceilWidth = (int) Math.ceil(width);
      int ceilHeight = (int) Math.ceil(height);
      int floorWidth = (int) Math.floor(width);
      int floorHeight = (int) Math.floor(height);
      RGBAPixel pixelC = this.pixelArray[ceilHeight][floorWidth];
      RGBAPixel pixelD = this.pixelArray[ceilHeight][ceilWidth];
      RGBAPixel pixelA = this.pixelArray[floorHeight][floorWidth];
      RGBAPixel pixelB = this.pixelArray[floorHeight][ceilWidth];

      for (String c : colors) {
        double m = pixelB.getValue(c) * (width - floorWidth)
                + pixelA.getValue(c) * (ceilWidth - width);
        double n = pixelD.getValue(c) * (width - floorWidth)
                + pixelC.getValue(c) * (ceilWidth - width);
        double cp = n * (height - floorHeight) + m * (ceilHeight - height);
        newValues.add((int) cp);
      }
      return new RGBAPixel(newValues.get(0), newValues.get(1), newValues.get(2), 255);
    }
  }
}