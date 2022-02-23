package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a Picture of any type that stores its pixels with RGB data values.
 * AllPicture can keep track of a Pictures width, height, and maxValue, as well as all of its
 * RGBPixels. Since this data is no longer linked to a specific filetype, an AllPicture object
 * can be saved to any picture format.
 */
public class AllPicture extends AbstractPicture {
  protected RGBPixel[][] pixelArray;

  /**
   * Creates a Picture with a given width, height, max Color value, and list of all the
   * pixels that make up this image.
   *
   * @param width      Width of this image(in pixels)
   * @param height     Height of this image(in pixels)
   * @param maxValue   The maximum color value of a single pixel field (R, G, B fields)
   * @param pixelArray A 2D array that holds every pixel in this image
   */
  public AllPicture(int width, int height, int maxValue, RGBPixel[][] pixelArray) {
    super(width, height, maxValue, pixelArray);
    this.pixelArray = pixelArray;
  }

  @Override
  public AllPicture clone() {
    int newWidth = this.width;
    int newHeight = this.height;
    int maxValue = this.maxValue;
    RGBPixel[][] newPixelArray = new RGBPixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        RGBPixel clonePixel = this.pixelArray[i][j];
        newPixelArray[i][j] = clonePixel.clone();
      }
    }
    return new AllPicture(newWidth, newHeight, maxValue, newPixelArray);
  }

  @Override
  public IPicture downSize(double heightRatio, double widthRatio) {
    int newHeight = (int) (this.height * heightRatio);
    int newWidth = (int) (this.width * widthRatio);

    RGBPixel[][] scaledArray = new RGBPixel[newHeight][newWidth];

    for (int i = 0; i < newHeight; i++) {
      for (int j = 0; j < newWidth; j++) {
        double heightValue = (i / heightRatio);
        double widthValue = (j / widthRatio);

        RGBPixel currPixel = this.calculatePixel(heightValue, widthValue);
        scaledArray[i][j] = currPixel.clone();
      }
    }

    return new AllPicture(newWidth, newHeight, this.maxValue, scaledArray);
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
  private RGBPixel calculatePixel(double height, double width) {
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
      RGBPixel pixelC = this.pixelArray[ceilHeight][floorWidth];
      RGBPixel pixelD = this.pixelArray[ceilHeight][ceilWidth];
      RGBPixel pixelA = this.pixelArray[floorHeight][floorWidth];
      RGBPixel pixelB = this.pixelArray[floorHeight][ceilWidth];

      for (String c : colors) {
        double m = pixelB.getValue(c) * (width - floorWidth)
                + pixelA.getValue(c) * (ceilWidth - width);
        double n = pixelD.getValue(c) * (width - floorWidth)
                + pixelC.getValue(c) * (ceilWidth - width);
        double cp = n * (height - floorHeight) + m * (ceilHeight - height);
        newValues.add((int) cp);
      }
      return new RGBPixel(newValues.get(0), newValues.get(1), newValues.get(2));
    }
  }
}