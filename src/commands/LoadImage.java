package commands;

import controllers.ImageUtil;
import model.ImageProcessorModel;

/**
 * This class represents a LoadImage Command. When executed, this command reads a file
 * based on a given file name and adds it to the current model.
 *
 */
public class LoadImage implements Command {
  private String name;
  private String returnName;
  private ImageProcessorModel model;

  /**
   * Creates a new LoadImage command that takes in the current model,
   * the name of the image to load, and the return name to store the photo with.
   *
   * @param model      represents the current storage model
   * @param name       represents the name of the original image
   * @param returnName represents the name of the new image
   * @throws IllegalArgumentException if the given model, return name, or storage name is null
   */
  public LoadImage(ImageProcessorModel model, String name, String returnName)
          throws IllegalArgumentException {
    if ((model == null) || (returnName == null) || (name == null)) {
      throw new IllegalArgumentException("This command cannot have a null model, picture name or "
              + "return name!");
    }
    this.name = name;
    this.returnName = returnName;
    this.model = model;
  }

  @Override
  public void execute() {
    String fileType = name.substring(name.length() - 3);

    if (fileType.equals("ppm")) {
      model.addPicture(ImageUtil.readPPM(name), returnName);
    } else {
      model.addPicture(ImageUtil.readAll(name), returnName);
    }
  }

  @Override
  public String outputMessage() {
    return "Loaded Image named " + returnName + "\n";
  }

}
