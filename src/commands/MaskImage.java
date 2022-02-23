package commands;

import controllers.ImageUtil;
import model.IPicture;
import model.ImageProcessorModel;

/**
 * Takes in a black and white image of the same dimensions representing a mask. Given a filter to
 * apply, this class applies the transformation only in the black areas of the masked image.
 */
public class MaskImage implements Command {
  private IPicture currImage;
  private String returnName;
  private ImageProcessorModel model;
  private IPicture maskArray;

  /**
   * Creates a new Filter command that takes in the current model,
   * the name of the image to duplicate, and the return name to store the photo with.
   *
   * @param model      represents the current storage model
   * @param currImage  represents the image that the user wants to be flipped
   * @param returnName represents the new name of the image
   * @throws IllegalArgumentException if the model, picture, or return name is null
   */
  public MaskImage(ImageProcessorModel model,
                     IPicture currImage, String maskFileName, String returnName)
          throws IllegalArgumentException {
    if ((model == null) || (currImage == null) || (returnName == null)) {
      throw new IllegalArgumentException("This command cannot have a null model, Picture, or "
              + "return name!");
    }
    this.model = model;
    this.currImage = currImage;
    this.returnName = returnName;

    System.out.println(maskFileName);
    String fileType = maskFileName.substring(maskFileName.length() - 3);
    if (fileType.equals("ppm")) {
      maskArray = ImageUtil.readPPM(maskFileName);
    } else {
      maskArray = ImageUtil.readAll(maskFileName);
    }
  }

  @Override
  public void execute() {
    IPicture newPicture = currImage.clone();
    newPicture.maskImage(maskArray);
    model.addPicture(newPicture, returnName);
  }

  @Override
  public String outputMessage() {
    return "Applied filter to valid regions in mask and saved as " + returnName + "\n";
  }
}
