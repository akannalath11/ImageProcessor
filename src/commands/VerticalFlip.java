package commands;

import model.IPicture;
import model.ImageProcessorModel;

/**
 * This class represents a Vertical Flip Command. When executed, this command with call
 * the vertical flip method of an IPicture mutating the pixels in the array.
 */
public class VerticalFlip implements Command {
  private IPicture currImage;
  private String returnName;
  private ImageProcessorModel model;

  /**
   * Creates a new VerticalFlip command that takes in the current model,
   * the name of the image to duplicate, and the return name to store the photo with.
   *
   * @param model      represents the current storage model
   * @param currImage  represents the image that the user wants to be flipped
   * @param returnName represents the new name of the image
   * @throws IllegalArgumentException if the model, picture, or return name is null
   */
  public VerticalFlip(ImageProcessorModel model, IPicture currImage, String returnName)
          throws IllegalArgumentException {
    if ((model == null) || (currImage == null) || (returnName == null)) {
      throw new IllegalArgumentException("This command cannot have a null model, Picture, or "
              + "return name!");
    }
    this.model = model;
    this.currImage = currImage;
    this.returnName = returnName;
  }

  @Override
  public void execute() {
    IPicture newPicture = currImage.clone();
    newPicture.verticalFlip();
    model.addPicture(newPicture, returnName);
  }

  @Override
  public String outputMessage() {
    return "Flipped vertically and saved as " + returnName + "\n";
  }
}
