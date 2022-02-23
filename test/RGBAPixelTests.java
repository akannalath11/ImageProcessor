import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import model.RGBAPixel;

/**
 * Tests the methods in the RGBA pixel class.
 */
public class RGBAPixelTests {
  private RGBAPixel pixel1;
  private RGBAPixel pixel2;
  private RGBAPixel pixel3;
  private RGBAPixel pixel4;
  private RGBAPixel pixel5;
  private RGBAPixel pixel6;

  @Before
  public void initData() {
    pixel1 = new RGBAPixel(0, 0, 0, 255);
    pixel2 = new RGBAPixel(50,50,50, 255);
    pixel3 = new RGBAPixel(255, 255, 255, 255);
    pixel4 = new RGBAPixel(40, 80, 120, 0);
    pixel5 = new RGBAPixel(10, 20, 11, 255);
    pixel6 = new RGBAPixel(210, 198, 65, 255);
  }

  @Test
  public void testPixelToString() {
    String stringOutput = "210 198 65 ";
    assertEquals(stringOutput, pixel6.toString());

    String stringOutput2 = "10 20 11 ";
    assertEquals(stringOutput2, pixel5.toString());
  }

  @Test
  public void testRGBAEquals() {
    RGBAPixel clone = pixel1.clone();
    assertEquals(true, pixel1.equals(clone));

    pixel1.brighten(50, 255);
    assertEquals(false, pixel1.equals(clone));

    // Same RGB values different alpha tests
    pixel1 = new RGBAPixel(0, 0, 0, 255);
    pixel1 = new RGBAPixel(0, 0, 0, 200);
    assertEquals(false, pixel1.equals(pixel2));
  }

  @Test
  public void testClone() {
    pixel1.brighten(50, 255);
    RGBAPixel clone = pixel1.clone();
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
    // Checks amn individual pixel doesn't exceed max
    RGBAPixel newPixel = new RGBAPixel(255, 248, 115, 255);
    assertEquals(true, pixel6.equals(newPixel));
  }

  // Tests that the image darkens
  @Test
  public void testDarken() {
    pixel2.brighten(-50, 255);
    assertEquals(true, pixel2.equals(pixel1));
  }

  // Tests that the color values resort to the min value if the brightness goes below it
  @Test
  public void testDarkenMax() {
    pixel4.brighten(-50, 255);
    // Checks a pixel doesn't exceed min
    RGBAPixel newPixel = pixel4 = new RGBAPixel(0, 30, 70, 255);
    assertEquals(true, pixel4.equals(newPixel));
  }

  // Tests the greyscale method with the value type
  @Test
  public void testGreyscale() {
    pixel4.greyScale("value");
    assertEquals(true, pixel4.equals(new RGBAPixel(120, 120, 120, 0)));
  }

  // Tests the greyscale method with the intensity type
  @Test
  public void testGreyscale2() {
    pixel4.greyScale("intensity");
    assertEquals(true, pixel4.equals(new RGBAPixel(80, 80, 80, 0)));
  }

  // Tests the greyscale method with the intensity type checking to make sure the values are rounded
  // properly
  @Test
  public void testGreyscale7() {
    pixel5.greyScale("intensity");
    assertEquals(true, pixel5.equals(new RGBAPixel(13, 13, 13, 255)));
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
    assertEquals(255.0, pixel6.getValue("alpha"), 0.1);
    assertEquals(198.0, pixel6.getValue("green"), 0.1);
    assertEquals(210.0, pixel6.getValue("red"), 0.1);
    assertEquals(65.0, pixel6.getValue("blue"), 0.1);
  }

  @Test
  public void testAlphaValue() {
    assertEquals(255.0, pixel2.getValue("alpha"), 0.1);
    pixel2.brighten(45, 255);
    assertEquals(255.0, pixel2.getValue("alpha"), 0.1);
    pixel2.brighten(45, 50);
    assertEquals(255.0, pixel2.getValue("alpha"), 0.1);
  }

  @Test
  public void testSet() {
    assertEquals(255.0, pixel2.getValue("alpha"), 0.1);
    pixel2.set(0, 0,0, 255);
    assertEquals(255, pixel2.getValue("alpha"), 0.1);
    assertEquals(0.0, pixel2.getValue("red"), 0.1);
    assertEquals(0.0, pixel2.getValue("green"), 0.1);
    assertEquals(0.0, pixel2.getValue("blue"), 0.1);

    // Tests that set clamps values correctly
    pixel6.set(-50, 0, 260, 255);
    assertEquals(255, pixel6.getValue("alpha"), 0.1);
    assertEquals(0.0, pixel6.getValue("red"), 0.1);
    assertEquals(0.0, pixel6.getValue("green"), 0.1);
    assertEquals(255, pixel6.getValue("blue"), 0.1);
  }
}
