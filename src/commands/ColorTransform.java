package commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.IPicture;
import model.ImageProcessorModel;

/**
 * This class represents a ColorTransform Command. When executed, this command with call the color
 * transform method of an IPicture, mutating the pixels based on the color transformation matrix.
 */
public class ColorTransform implements Command {
  private IPicture currImage;
  private String type;
  private String returnName;
  private ImageProcessorModel model;

  /**
   * Creates a new ColorTransform command that takes in the current model,
   * the name of the image to duplicate, the name to add the new picture to the model,
   * and the greyscale type.
   *
   * @param model      represents the current storage model
   * @param currImage  represents the image that is being transforming
   * @param type       the type of color transformation
   * @param returnName represents the new name of the image
   * @throws IllegalArgumentException if the model, Picture, colorTransform type,
   *     or return Name is null
   */
  public ColorTransform(ImageProcessorModel model, IPicture currImage,
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
    List<Double> transformList = new ArrayList<Double>();

    if (type.equals("sepia")) {
      transformList = new ArrayList<>(Arrays.asList(0.3930, .3490, .2720, .7690, .6860,
              .5340, .1890, .1680, .131));
    }

    IPicture newPicture = currImage.clone();
    newPicture.colorTransform(transformList);
    model.addPicture(newPicture, returnName);
  }

  @Override
  public String outputMessage() {
    return "Applied color transform " + type + " and saved as " + returnName + "\n";
  }
}
