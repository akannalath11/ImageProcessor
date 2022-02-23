package commands;

import model.IPicture;
import model.ImageProcessorModel;

/**
 * This class represents a Greyscale Command. When executed, this command with call the greyscale
 * method of an IPicture mutating the pixels based on the given greyscale type.
 */
public class GreyScale implements Command {
  private IPicture currImage;
  private String type;
  private String returnName;
  private ImageProcessorModel model;

  /**
   * Creates a new Greyscale command that takes in the current model,
   * the name of the image to duplicate, the name to add the new picture to the model,
   * and the greyscale type.
   *
   * @param model      represents the current storage model
   * @param currImage  represents the image that is being transforming into a black and white form
   * @param type       the type of greyscale desired (whether you want it on the red, blue, green,
   *                   luma, value, or intensity components)
   * @param returnName represents the new name of the image
   * @throws IllegalArgumentException if the model, Picture, greyscale type, or return Name is null
   */
  public GreyScale(ImageProcessorModel model, IPicture currImage,
                   String type, String returnName) throws IllegalArgumentException {
    if ((model == null) || (currImage == null) || (returnName == null) || (type == null)) {
      throw new IllegalArgumentException("This command cannot have a null model, Picture, or "
              + "return name!");
    }
    this.currImage = currImage;
    this.type = type;
    this.returnName = returnName;
    this.model = model;
  }

  @Override
  public void execute() {
    IPicture newPicture = currImage.clone();
    newPicture.greyScale(type);
    model.addPicture(newPicture, returnName);
  }

  @Override
  public String outputMessage() {
    return "Converted to " + type + " greyscale and saved as " + returnName + "\n";
  }
}
