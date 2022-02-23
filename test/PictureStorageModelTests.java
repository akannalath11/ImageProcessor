import org.junit.Before;
import org.junit.Test;

import controllers.ImageUtil;
import model.AllPicture;
import model.IPicture;
import model.PictureStorageModel;
import static org.junit.Assert.assertEquals;

/**
 * Checks that the model for the ImageProcessor works correctly.
 */
public class PictureStorageModelTests {
  private AllPicture squareImage;
  private AllPicture squareImage2;
  private PictureStorageModel storage1;

  @Before
  public void initData() {
    squareImage = ImageUtil.readPPM("res/square.ppm");
    squareImage2 = ImageUtil.readPPM("res/square-brighter-by-50.ppm");
    storage1 = new PictureStorageModel();
  }

  // tests the add and get picture methods
  @Test
  public void testAddPicture() {
    storage1.addPicture(squareImage, "square1");
    storage1.addPicture(squareImage2, "square2 - Brighter");
    assertEquals(squareImage, storage1.getPicture("square1"));
    assertEquals(squareImage2, storage1.getPicture("square2 - Brighter"));
  }

  @Test
  public void testGetPicture() {
    storage1.addPicture(squareImage, "square1");
    IPicture retrieved = storage1.getPicture("square1");
    assertEquals(retrieved, storage1.getPicture("square1"));
  }


  // Tests that the getPicture will fail to get a picture that is not stores in the Hashmap of
  // pictures
  @Test(expected = IllegalArgumentException.class)
  public void testGetPictureFail() {
    storage1.addPicture(squareImage, "square1");
    assertEquals(squareImage, storage1.getPicture("square2 - Brighter"));
  }

  @Test(expected = IllegalArgumentException.class)
  // checks exception when the picture is added without name
  public void testAddPictureNullName() {
    storage1.addPicture(squareImage, null);
  }

  @Test(expected = IllegalArgumentException.class)
  // checks exception when the picture is added without name
  public void testAddNullPicture() {
    storage1.addPicture(null, "square");
  }

  @Test
  public void testGetCurrCount() {
    storage1.addPicture(squareImage, "0");
    storage1.addPicture(squareImage2, "1");
    storage1.addPicture(squareImage, "2");
    assertEquals("3", storage1.getCurrCount());
  }

  @Test
  public void testGetLastCount() {
    storage1.addPicture(squareImage, "0");
    storage1.addPicture(squareImage2, "1");
    storage1.addPicture(squareImage, "2");
    assertEquals("2", storage1.getLastCount());
  }

  @Test
  public void testUndo() {
    storage1.addPicture(squareImage, "0");
    // Simulates adding a new picture that has been edited
    storage1.addPicture(squareImage2, "1");
    assertEquals("2", storage1.getCurrCount());
    storage1.undo();

    // Check that the current count has reset to back to 1
    assertEquals("1", storage1.getCurrCount());
  }

  @Test
  public void colorValues() {
    storage1.addPicture(squareImage, "0");
    int[] redResults = storage1.colorValues("red");

    // For results see text above
    assertEquals(256, redResults.length); // check that the length is correct
    assertEquals(0, redResults[0]);
    assertEquals(2, redResults[204]);
    assertEquals(2, redResults[102]);

  }
}
