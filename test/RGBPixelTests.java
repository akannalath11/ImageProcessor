import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import model.RGBPixel;

/**
 * Tests the methods in the RGB pixel class.
 */
public class RGBPixelTests {
  private RGBPixel pixel1;
  private RGBPixel pixel2;
  private RGBPixel pixel3;
  private RGBPixel pixel4;
  private RGBPixel pixel5;
  private RGBPixel pixel6;

  @Before
  public void initData() {
    pixel1 = new RGBPixel(0, 0, 0);
    pixel2 = new RGBPixel(50,50,50);
    pixel3 = new RGBPixel(255, 255, 255);
    pixel4 = new RGBPixel(40, 80, 120);
    pixel5 = new RGBPixel(10, 20, 11);
    pixel6 = new RGBPixel(210, 198, 65);
  }

  @Test
  public void testRGBAEquals() {
    RGBPixel clone = pixel1.clone();
    assertEquals(true, pixel1.equals(clone));

    pixel1.brighten(50, 255);
    assertEquals(false, pixel1.equals(clone));

    // Similar RGB values but still different
    pixel1 = new RGBPixel(0, 0, 1);
    pixel1 = new RGBPixel(0, 0, 0);
    assertEquals(false, pixel1.equals(pixel2));
  }

  @Test
  public void testPixelToString() {
    String stringOutput = "210 198 65 ";
    assertEquals(stringOutput, pixel6.toString());

    String stringOutput2 = "10 20 11 ";
    assertEquals(stringOutput2, pixel5.toString());
  }

  @Test
  public void testClone() {
    pixel1.brighten(50, 255);
    RGBPixel clone = pixel1.clone();
    assertEquals(true, pixel1.equals(clone));

    pixel2.brighten(100, 255);
    clone = pixel2.clone();
    assertEquals(true, pixel2.equals(clone));
  }

  // Tests the brightness function in the SimplePixel class
  @Test
  public void testBrighten() {
    pixel1.brighten(50, 255);
    assertEquals(true, pixel1.equals(pixel2));
  }

  // Tests that the color values resort to the max value if the brightness goes above it
  @Test
  public void testBrightenMax() {
    pixel2.brighten(250, 255);
    assertEquals(true, pixel2.equals(pixel3));


    pixel6.brighten(50, 255);
    // Checks a pixel doesn't exceed max
    RGBPixel newPixel = new RGBPixel(255, 248, 115);
    assertEquals(true, pixel6.equals(newPixel));
  }

  // Tests that the color values resort to the min value if the brightness goes below it
  @Test
  public void testDarkenMax() {
    pixel2.brighten(-50, 255);
    assertEquals(true, pixel2.equals(pixel1));

    pixel4.brighten(-50, 255);
    // Checks a pixel doesn't exceed min
    RGBPixel newPixel = pixel4 = new RGBPixel(0, 30, 70);
    assertEquals(true, pixel4.equals(newPixel));
  }

  // Tests the greyscale method with the value type
  @Test
  public void testGreyscale() {
    pixel4.greyScale("value");
    assertEquals(true, pixel4.equals(new RGBPixel(120, 120, 120)));
  }

  // Tests the greyscale method with the intensity type
  @Test
  public void testGreyscale2() {
    pixel4.greyScale("intensity");
    assertEquals(true, pixel4.equals(new RGBPixel(80, 80, 80)));
  }

  // Tests the greyscale method with the intensity type checking to make sure the values are rounded
  // properly
  @Test
  public void testGreyscale7() {
    pixel5.greyScale("intensity");
    assertEquals(true, pixel5.equals(new RGBPixel(13, 13, 13)));
  }

  // Tests the toString method
  @Test
  public void testToString() {
    assertEquals("0 0 0 ", pixel1.toString());
    assertEquals("50 50 50 ", pixel2.toString());
    assertEquals("255 255 255 ", pixel3.toString());
    assertEquals("40 80 120 ", pixel4.toString());
  }

  @Test
  public void testGetValue() {
    assertEquals(198.0, pixel6.getValue("green"), 0.1);
    assertEquals(210.0, pixel6.getValue("red"), 0.1);
    assertEquals(65.0, pixel6.getValue("blue"), 0.1);
  }

  @Test
  public void testSet() {
    pixel2.set(0, 0,0, 255);
    assertEquals(255, pixel2.getValue("alpha"), 0.1);
    assertEquals(0.0, pixel2.getValue("red"), 0.1);
    assertEquals(0.0, pixel2.getValue("green"), 0.1);
    assertEquals(0.0, pixel2.getValue("blue"), 0.1);

    // Tests that set clamps values correctly
    pixel6.set(-50, 0, 260, 255);
    assertEquals(0.0, pixel6.getValue("red"), 0.1);
    assertEquals(0.0, pixel6.getValue("green"), 0.1);
    assertEquals(255, pixel6.getValue("blue"), 0.1);
  }
}