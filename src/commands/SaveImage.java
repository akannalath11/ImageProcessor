package commands;

import model.IPicture;

/**
 * This class represents a SaveImage Command. When executed, this command exports a Picture
 * from the current model and saves it into a file relative to the IntelliJ directory.
 *
 */
public class SaveImage implements Command {
  private IPicture currImage;
  private String fileName;

  /**
   * Creates a new SaveImage command that takes in the current model,
   * the name of the image to save, and the file name to store the photo with.
   * @param currImage      represents the current storage model
   * @param fileName       represents the name of the original image
   * @throws IllegalArgumentException if the picture of return name is null
   */
  public SaveImage(IPicture currImage, String fileName) throws IllegalArgumentException {
    if ((currImage == null) || (fileName == null)) {
      throw new IllegalArgumentException("This command cannot have a null Picture or "
              + "file name!");
    }
    this.currImage = currImage;
    this.fileName = fileName;
  }

  @Override
  public void execute() {
    currImage.save(fileName);
  }

  @Override
  public String outputMessage() {
    return "Saved to " + fileName + "\n";
  }

}
