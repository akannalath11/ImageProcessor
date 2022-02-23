package commands;

import java.util.InputMismatchException;

import model.IPicture;
import model.ImageProcessorModel;

/**
 * This class represents a Brighten Command. When executed, this command adds a new mutated
 * image to the current ImageProcessorModel.
 */
public class BrightenImage implements Command {
  private IPicture currImage;
  private int value;
  private String returnName;
  private String type;
  private ImageProcessorModel model;

  /**
   * Creates a new Brighten command that takes in the current model, the
   * value to adjust the pixels by, the name of the image to duplicate, and the
   * name to add the new picture to the model with. This command is used for both
   * brightening or darkening the image.
   *
   * @param model      represents the current storage model
   * @param value      represents the value of the increased or decreased brightness
   * @param currImage  represents the image that is being brightened/darkened
   * @param returnName represents the new name of the image
   * @throws IllegalArgumentException if the model, Picture, or return name are null
   * @throws InputMismatchException   if the brightness value cannot be converted to an int
   */
  public BrightenImage(ImageProcessorModel model, String value, String type, IPicture currImage,
                       String returnName) throws IllegalArgumentException, InputMismatchException {
    if ((model == null) || (currImage == null) || (returnName == null)) {
      throw new IllegalArgumentException("This command cannot have a null model, Picture, or "
              + "return name!");
    }
    this.currImage = currImage;
    this.type = type;
    try {
      this.value = Integer.parseInt(value);
    } catch (NumberFormatException ex) {
      throw new InputMismatchException("Given value must be an integer!");
    }
    if (type.equals("d")) {
      this.value = this.value * -1;
    }
    this.returnName = returnName;
    this.model = model;
  }

  @Override
  public void execute() {
    IPicture newPicture = currImage.clone();
    newPicture.brightenImage(value);
    model.addPicture(newPicture, returnName);
  }

  @Override
  public String outputMessage() {
    if (type.equals("b")) {
      return "Brightened image by " + value + " and saved as " + returnName + "\n";
    } else {
      return "Darkened image by " + value * -1 + " and saved as " + returnName + "\n";
    }

  }
}
