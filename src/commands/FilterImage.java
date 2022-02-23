package commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.IPicture;
import model.ImageProcessorModel;

/**
 * This class represents a Filter Command. When executed, this command with call
 * a filter method of an IPicture mutating the pixels in the array to represent the filter.
 */
public class FilterImage implements Command {
  private IPicture currImage;
  private String returnName;
  private ImageProcessorModel model;
  private String type;

  /**
   * Creates a new Filter command that takes in the current model,
   * the name of the image to duplicate, and the return name to store the photo with.
   *
   * @param model      represents the current storage model
   * @param currImage  represents the image that the user wants to be flipped
   * @param returnName represents the new name of the image
   * @param type represents the type of this filter
   * @throws IllegalArgumentException if the model, picture, or return name is null
   */
  public FilterImage(ImageProcessorModel model,
                     IPicture currImage, String returnName, String type)
          throws IllegalArgumentException {
    if ((model == null) || (currImage == null) || (returnName == null)) {
      throw new IllegalArgumentException("This command cannot have a null model, Picture, or "
              + "return name!");
    }
    this.model = model;
    this.currImage = currImage;
    this.returnName = returnName;
    this.type = type;
  }

  @Override
  public void execute() {
    IPicture newPicture = currImage.clone();
    List<Double> kernelValues = new ArrayList<>();
    int bounds = 0;
    if (this.type.equals("blur")) {
      kernelValues = new ArrayList<>(Arrays.asList(
              .0625, .125, .0625,
              .125, .25, .125,
              .0625, .125, .0625));
      bounds = 1;
    }
    if (this.type.equals("sharpen")) {
      kernelValues = new ArrayList<>(Arrays.asList(
              -.125, -.125, -.125, -.125, -.125,
              -.125, .25,  .25,  .25, -.125,
              -.125, .25,  1.0,  .25, -.125,
              -.125, .25,  .25,  .25, -.125,
              -.125, -.125, -.125, -.125, -.125));
      bounds = 2;
    }
    newPicture.filter(kernelValues, bounds);
    model.addPicture(newPicture, returnName);
  }

  @Override
  public String outputMessage() {
    return "Filtered picture and saved as " + returnName + "\n";
  }
}
