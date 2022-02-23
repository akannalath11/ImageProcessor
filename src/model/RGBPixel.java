package model;

/**
 * Represents a single pixel of an image that has a Red, Green, and Blue color values.
 */
public class RGBPixel extends AbstractPixel {

  /**
   * Creates a single RGB pixel based on its red green and blue values.
   *
   * @param r Red value for this pixel
   * @param g Green value for this pixel
   * @param b Blue value for this pixel
   */
  public RGBPixel(int r, int g, int b) {
    super(r, g, b);
  }

  @Override
  public RGBPixel clone() {
    return new RGBPixel(this.red, this.green, this.blue);
  }

}
