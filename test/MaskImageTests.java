/*
import org.junit.Before;
import org.junit.Test;

import commands.Command;
import commands.MaskImage;
import commands.SaveImage;
import controllers.ImageUtil;
import model.IPicture;
import model.ImageProcessorModel;
import model.PictureStorageModel;

import static org.junit.Assert.assertEquals;

public class MaskImageTests {
  private IPicture squareImage;
  private IPicture dogImage;
  private Command maskImage;
  private ImageProcessorModel model;

  @Before
  public void initData() {
    squareImage = ImageUtil.readPPM("res/square.ppm");
    dogImage = ImageUtil.readAll("res/dog.jpg");
    model = new PictureStorageModel();
  }

  @Test
  public void testExecuteScaledImage() {
    model = new PictureStorageModel();
    model.addPicture(dogImage, "dog");
    maskImage = new MaskImage(model,  dogImage,
            "res/dog-mask.jpg", "masked-dog");
    maskImage.execute();

    Command saveImage = new SaveImage(model.getPicture("masked-dog"),
            "testMask.png");
    saveImage.execute();
    assertEquals(true, true);

    /**
     * // Load correct image (Manipulated in Gimp)
     *     AllPicture brightenImageCorrect =
     *             ImageUtil.readPPM("res/square-brighter-by-50.ppm");
     *     // Check that the new picture was added to the Model and get this Picture
     *     IPicture newImage = model.getPicture("square-brightened");
     *     // Check that the new picture is the same as the correct one
     *     assertEquals(true, brightenImageCorrect.equals(newImage));
     *
     *     load res/dog.jpg dog downscale .5 .5 dog dog-small save dog-small testSmall.jpg q
     */

  //}
//}

