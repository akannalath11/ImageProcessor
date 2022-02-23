package model;

import java.util.Objects;

/**
 * Represents a single pixel of an image that has a Red, Green, and Blue color values.
 */
public abstract class AbstractPixel implements IPixel {
  protected int red;
  protected int blue;
  protected int green;

  /**
   * Creates a single pixel based on its red green and blue values.
   *
   * @param r Red value for this pixel
   * @param g Green value for this pixel
   * @param b Blue value for this pixel
   */
  public AbstractPixel(int r, int g, int b) {
    this.red = r;
    this.green = g;
    this.blue = b;
  }

  @Override
  public abstract IPixel clone();

  @Override
  public String toString() {
    return this.red + " " + this.green + " " + this.blue + " ";
  }

  /**
   * Checks if the red, green, and blue values of this pixel are the same as another RGB pixel.
   *
   * @param other The object to check equality with
   * @return True if all the RGB fields are identical, false otherwise
   */
  @Override
  public boolean equals(Object other) {
    if (!((other instanceof RGBPixel) || (other instanceof RGBAPixel))) {
      return false;
    }
    AbstractPixel that = (AbstractPixel) other;

    int otherRed = (int) that.getValue("red");
    int otherGreen = (int) that.getValue("green");
    int otherBlue = (int) that.getValue("blue");
    int otherAlpha = (int) that.getValue("alpha");
    int thisAlpha = (int) this.getValue("alpha");
    return ((this.red == otherRed) && (this.blue == otherBlue) && (this.green == otherGreen)
            && (thisAlpha == otherAlpha));
  }

  @Override
  public int hashCode() {
    int thisAlpha = (int) this.getValue("alpha");
    return (Objects.hash(this.red, this.green, this.blue, thisAlpha));
  }

  @Override
  public void set(int r, int g, int b, int maxValue) {
    if (r > maxValue) {
      r = maxValue;
    }
    if (g > maxValue) {
      g = maxValue;
    }
    if (b > maxValue) {
      b = maxValue;
    }
    if (r < 0) {
      r = 0;
    }
    if (g < 0) {
      g = 0;
    }
    if (b < 0) {
      b = 0;
    }
    this.red = r;
    this.green = g;
    this.blue = b;
  }

  /**
   * Updates the fields each of the R,G,B values by adding a given integer value to the current
   * color value. If the summed number is below 0 the field is set to 0. If the summed value
   * is greater than the given maxValue the field is set to the max value.
   *
   * @param value    The value to change each field by
   * @param maxValue The maximum value this field can be
   */
  @Override
  public void brighten(int value, int maxValue) {
    int redValue = this.red + value;
    int greenValue = this.green + value;
    int blueValue = this.blue + value;

    this.set(redValue, greenValue, blueValue, maxValue);
  }

  /**
   * Updates the fields each of the RGB values by performing a greyscale image conversion
   * of the type: intensity or value.
   *
   * @param type The type of greyscale conversion
   */
  @Override
  public void greyScale(String type) {
    int currValue;

    if (type.equals("value")) {
      currValue = Math.max(Math.max(this.red, this.green), this.blue);
    } else if (type.equals("intensity")) {
      currValue = (this.red + this.blue + this.green) / 3;
    }
    /*else if (type.equals("luma")) {
      currValue = (int) (0.2126 * this.red + 0.7152 * this.green + 0.0722 * this.blue);
    } else if (type.equals("red")) {
      currValue = this.red;
    } else if (type.equals("blue")) {
      currValue = this.blue;
    } else if (type.equals("green")) {
      currValue = this.green;
    } */
    else {
      throw new IllegalArgumentException("Invalid greyscale type!");
    }

    this.red = currValue;
    this.green = currValue;
    this.blue = currValue;
  }

  @Override
  public double getValue(String color) throws IllegalArgumentException {
    if (color.equals("red")) {
      return this.red;
    } else if (color.equals("green")) {
      return this.green;
    } else if (color.equals("blue")) {
      return this.blue;
    } else if (color.equals("alpha")) {
      return 255;
    } else {
      throw new IllegalArgumentException("A RGB pixel does not store data of the given type!");
    }
  }
}

