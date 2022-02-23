package commands;

import java.util.InputMismatchException;

import model.IPicture;
import model.ImageProcessorModel;

/**
 * This class represents a Scale Command. When executed, this command adds a downscaled version
 * of the current image given a height and width ratio.
 */
public class ScaleImage implements Command {
  private IPicture currImage;
  private double widthRatio;
  private double heightRatio;
  private String returnName;
  private ImageProcessorModel model;

  /**
   * Creates a new ScaleImage command that takes in the current model, the
   * ratio of the new image, the name of the image to duplicate, and the
   * name to add the new picture to the model with.
   *
   * @param model      represents the current storage model
   * @param height     represents the height ratio of the scaled image
   * @param width      represents the width ratio of the scaled image
   * @param currImage  represents the image that is being brightened/darkened
   * @param returnName represents the new name of the image
   * @throws IllegalArgumentException if the model, Picture, or return name are null
   * @throws InputMismatchException   if the width and height ratio are not valid decimal values
   */
  public ScaleImage(ImageProcessorModel model, String width, String height,
                    IPicture currImage, String returnName)
          throws IllegalArgumentException, InputMismatchException {
    if ((model == null) || (currImage == null) || (returnName == null) || (width == null) ||
            (height == null)) {
      throw new IllegalArgumentException("This command cannot have a null model, Picture, "
              + "ratios, or return name!");
    }
    this.currImage = currImage;
    try {
      this.widthRatio = Double.parseDouble(width);
      this.heightRatio = Double.parseDouble(height);
    } catch (NumberFormatException ex) {
      throw new InputMismatchException("Given ratios must be a valid double");
    }
    this.returnName = returnName;
    this.model = model;
  }

  @Override
  public void execute() {
    IPicture newPicture = currImage.clone();
    newPicture = newPicture.downSize(this.heightRatio, this.widthRatio);
    model.addPicture(newPicture, returnName);
  }

  @Override
  public String outputMessage() {
    return "Scaled image by w: " + this.widthRatio + " height: "
            + this.heightRatio + " and saved as " + returnName + "\n";
  }
}
