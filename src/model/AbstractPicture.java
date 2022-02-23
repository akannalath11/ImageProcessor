package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import controllers.ImageUtil;

/**
 * This is an abstract class that represents shared code between different IPicture concrete
 * classes. The abstract class stores the width, height, and max value of an IPicture along
 * with its IPixel array.
 */
public abstract class AbstractPicture implements IPicture {
  protected int width;
  protected int height;
  protected int maxValue;
  private IPixel[][] pixelArray;

  /**
   * Creates a Picture with a given width, height, max Color value, and list of all the
   * pixels that make up this image.
   *
   * @param width      Width of this image (in pixels)
   * @param height     Height of this image (in pixels)
   * @param maxValue   The maximum color value of a single pixel field (R, G, B fields)
   * @param pixelArray A 2D array that holds every pixel in this image
   */
  public AbstractPicture(int width, int height, int maxValue, IPixel[][] pixelArray) {
    this.width = width;
    this.height = height;
    this.maxValue = maxValue;
    this.pixelArray = pixelArray;
  }

  @Override
  public String toString() {
    String startMessage = "P3\n" + this.width + " " + this.height + "\n" + this.maxValue + "\n";
    String pixels = "";
    for (int i = 0; i < pixelArray.length; i++) {
      for (int j = 0; j < pixelArray[i].length; j++) {
        IPixel currPixel = pixelArray[i][j];
        pixels = pixels + currPixel.toString();
      }
      pixels = pixels + "\n";
    }
    return startMessage + pixels;
  }

  @Override
  public abstract IPicture clone();

  @Override
  public void save(String filename) {
    String fileType = filename.substring(filename.lastIndexOf(".") + 1);
    if (fileType.equals("ppm")) {
      ImageUtil.savePPM(this.width, this.height, this.maxValue, this.pixelArray, filename);
    } else {
      ImageUtil.saveAll(this.width, this.height, this.pixelArray, filename);
    }
  }

  @Override
  public BufferedImage getBufferedImage() {
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        IPixel currPixel = pixelArray[i][j];
        int red = (int) currPixel.getValue("red");
        int green = (int) currPixel.getValue("green");
        int blue = (int) currPixel.getValue("blue");
        int alpha = (int) currPixel.getValue("alpha");
        Color currColor = new Color(red, green, blue, alpha);
        image.setRGB(j, i, currColor.getRGB());
      }
    }
    return image;
  }

  @Override
  public boolean equals(Object other) {
    if (!((other instanceof AllPicture) || (other instanceof TransparentPicture))) {
      return false;
    }
    AbstractPicture that = (AbstractPicture) other;
    return equalsHelper(that);
  }

  @Override
  public int hashCode() {
    return (Objects.hash(this.maxValue, this.width, this.height, this.pixelArray));
  }

  /**
   * Checks if all the fields of an IPicture are equal including all the values of every pixel
   * in the IPixel array.
   *
   * @param that The Picture to compare equality to
   * @return if this picture is equal to the given
   */
  protected boolean equalsHelper(AbstractPicture that) {
    int isEqual = 0;
    if (this.height == that.height && this.width == that.width && this.maxValue == that.maxValue) {
      for (int i = 0; i < this.height; i++) {
        for (int j = 0; j < this.width; j++) {
          IPixel currPixel = this.pixelArray[i][j];
          IPixel otherPixel = that.pixelArray[i][j];
          if (!(currPixel.equals(otherPixel))) {
            isEqual = isEqual + 1;
          }
        }
      }
    }
    return (isEqual == 0);
  }

  @Override
  public void verticalFlip() {
    for (int i = 0; i < height / 2; i++) {
      for (int j = 0; j < pixelArray[i].length; j++) {
        IPixel currPixel = pixelArray[i][j];
        pixelArray[i][j] = pixelArray[pixelArray.length - 1 - i][j];
        pixelArray[pixelArray.length - 1 - i][j] = currPixel;
      }
    }
  }

  @Override
  public void horizontalFlip() {
    for (int i = 0; i < pixelArray.length; i++) {
      for (int j = 0; j < pixelArray[i].length / 2; j++) {
        IPixel currPixel = pixelArray[i][j];
        pixelArray[i][j] = pixelArray[i][pixelArray[i].length - 1 - j];
        pixelArray[i][pixelArray[i].length - 1 - j] = currPixel;
      }
    }
  }

  @Override
  public void brightenImage(int value) {
    for (int i = 0; i < pixelArray.length; i++) {
      for (int j = 0; j < pixelArray[i].length; j++) {
        IPixel currPixel = pixelArray[i][j];
        currPixel.brighten(value, maxValue);
      }
    }
  }

  @Override
  public void greyScale(String type) {
    List<Double> transformList = new ArrayList<>();
    if (type.equals("luma")) {
      transformList = new ArrayList<>(Arrays.asList(0.2126, 0.2126, 0.2126,
              0.7152, 0.7152, 0.7152, 0.0722, 0.0722, 0.0722));
    } else if (type.equals("red")) {
      transformList = new ArrayList<>(Arrays.asList(1.0, 1.0, 1.0, 0.0, 0.0,
              0.0, 0.0, 0.0, 0.0));
    } else if (type.equals("green")) {
      transformList = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 1.0, 1.0,
              1.0, 0.0, 0.0, 0.0));
    } else if (type.equals("blue")) {
      transformList = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0,
              0.0, 1.0, 1.0, 1.0));
    } else {
      for (int i = 0; i < pixelArray.length; i++) {
        for (int j = 0; j < pixelArray[i].length; j++) {
          IPixel currPixel = pixelArray[i][j];
          currPixel.greyScale(type);
        }
      }
      return;
    }
    this.colorTransform(transformList);
  }

  @Override
  public void colorTransform(List<Double> arrayValues) throws IllegalArgumentException {
    if (arrayValues.size() != 9) {
      throw new IllegalArgumentException("Invalid values for the color transformation matrix");
    }

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        IPixel currPixel = pixelArray[i][j];
        double currRed = currPixel.getValue("red");
        double currGreen = currPixel.getValue("green");
        double currBlue = currPixel.getValue("blue");

        int r = (int) (arrayValues.get(0) * currRed + arrayValues.get(3) * currGreen
                + arrayValues.get(6) * currBlue);
        int g = (int) (arrayValues.get(1) * currRed + arrayValues.get(4) * currGreen
                + arrayValues.get(7) * currBlue);
        int b = (int) (arrayValues.get(2) * currRed + arrayValues.get(5) * currGreen
                + arrayValues.get(8) * currBlue);
        this.pixelArray[i][j].set(r, g, b, this.maxValue);
      }
    }
  }

  @Override
  public void filter(List<Double> values, int bounds) {
    // For each of the pixels, apply Kernel to find avg for each RGB value, set value
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        // We want a radius of [i - 1][j - 1] --> [i + 1][j + 1]
        int r = filterHelp(j, i, bounds, "red", values);
        int g = filterHelp(j, i, bounds, "green", values);
        int b = filterHelp(j, i, bounds, "blue", values);
        this.pixelArray[i][j].set(r, g, b, this.maxValue);
      }
    }
  }

  /**
   * Takes in a list of kernel values inputting from left to right, with the top row first,
   * followed by the rest of the rows. The given col and row represent the middle pixel of the
   * kernel during calculations. The grid bounds determine the size of the kernel with the
   * equation 1 + gridBounds * 2 (gridBounds 1 is 3x3, 2 is 5x5);
   *
   * @param col          The column of the current pixel located at the middle of kernel
   * @param row          The row of the current pixel located at the middle of kernel
   * @param gridBounds   The size of the kernel
   * @param color        The color value to calculate for the current pixel
   * @param kernelValues The list of values that make up the kernel
   * @return The new integer value for this color of a given pixel after the kernel is applied
   */
  private int filterHelp(int col, int row, int gridBounds, String color,
                         List<Double> kernelValues) {
    double total = 0;
    int count = 0;
    for (int i = row - gridBounds; i <= row + gridBounds; i++) {
      for (int j = col - gridBounds; j <= col + gridBounds; j++) {
        // If the pixel in the kernel is a valid pixel (within 0,0 to Picture dimensions)
        if ((i >= 0) && (j >= 0) && (i < this.height) && (j < this.width)) {
          IPixel currPixel = this.pixelArray[i][j];
          total = total + kernelValues.get(count) * currPixel.getValue(color);
        }
        count = count + 1;
      }
    }
    return (int) total;
  }

  @Override
  public int[] colorValues(String type) {
    int[] colorValueList = new int[256];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int colorValue = (int) this.pixelArray[i][j].getValue(type);
        colorValueList[colorValue] = colorValueList[colorValue] + 1;
      }
    }

    return colorValueList;
  }

  /**
   * Calculates a 2D array of all the pixels that should be including in the masking effect.
   * For a pixel to be included its RGB values must all be black.
   * @return A 2D array of booleans, true represents a valid pixel
   */
  public boolean[][] validPixels() {
    boolean[][] validPixelArray = new boolean[this.height][this.width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        IPixel currPixel = this.pixelArray[i][j];
        if ((currPixel.getValue("red") == 255)
                && (currPixel.getValue("green") == 255)
                && (currPixel.getValue("blue") == 255)) {
          validPixelArray[i][j] = true;
        } else {
          validPixelArray[i][j] = false;
        }
      }
    }
    return validPixelArray;
  }

  @Override
  public void maskImage(IPicture maskedImage) {
    boolean[][] validPixels = maskedImage.validPixels();
    //IPicture validPicture;
    //IPixel[][] validPixel = new IPixel[][];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        IPixel currPixel = this.pixelArray[i][j];
        if (validPixels[i][j]) {
          currPixel.brighten(50, 255);
        }
      }
    }
  }
}