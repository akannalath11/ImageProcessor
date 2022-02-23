package model;

/**
 * Represents a single pixel of an image that has a Red, Green, Blue, and Alpha color values.
 */
public class RGBAPixel extends AbstractPixel {
  private int alpha;

  /**
   * Creates a single RGBA pixel based on its red green blue and alpha values.
   *
   * @param r Red value for this pixel
   * @param g Green value for this pixel
   * @param b Blue value for this pixel
   * @param a Alpha value for this pixel
   */
  public RGBAPixel(int r, int g, int b, int a) {
    super(r, g, b);
    this.alpha = a;
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
      return this.alpha;
    } else {
      throw new IllegalArgumentException("A RGBA pixel does not store data of the given type!");
    }
  }

  @Override
  public RGBAPixel clone() {
    return new RGBAPixel(this.red, this.green, this.blue, this.alpha);
  }
}

