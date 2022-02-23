package controllers;

import commands.BrightenImage;
import commands.ColorTransform;
import commands.Command;
import commands.FilterImage;
import commands.GreyScale;
import commands.HorizontalFlip;
import commands.ScaleImage;
import commands.VerticalFlip;
import model.IPicture;
import model.ImageProcessorModel;
import view.ImageProcessorGUIView;

/**
 * A BaseProcessorFeatures object performs all the actions given by the Features interface. This
 * base implementation takes in a model and GUI view and communicated data between them. When the
 * view calls on this class, it will tell the model how to update, and transmit the updated model
 * data back to the view to draw.
 */
public class BaseProcessorFeatures implements Features {
  private ImageProcessorModel model;
  private ImageProcessorGUIView view;

  /**
   * Creates a new base features object with a given model and view. The view will open to
   * its starting state and wait for user input. The controller will delegate necessary
   * information to the model and tell the view ot update when needed.
   * @param model The model to use with this controller
   * @param view The gui view the user will interact with
   */
  public BaseProcessorFeatures(ImageProcessorModel model, ImageProcessorGUIView view) {
    this.model = model;
    this.view = view;
  }

  @Override
  public void runProcessor() {
    view.addFeatures(this);
  }

  /**
   * Returns the most recent IPicture stored in the model based on the models count. This IPicture
   * represents the view the GUI is currently displaying.
   * @return The IPicture on the top of the models stack
   */
  private IPicture currImage() {
    return model.getPicture(model.getLastCount());
  }

  /**
   * Notifies the view to refresh the histogram currently being displayed. Passes the current
   * color values of the image from the model, to the view so that it is able
   * to accurately draw the updated histogram.
   */
  private void refreshHistogram() {
    view.drawHistogram(this.model.colorValues("red"), this.model.colorValues("green"),
            this.model.colorValues("blue"));
  }

  @Override
  public void loadPicture(String fileLocation) {
    // Check that the filetype is not empty, otherwise load image based on its type
    if (!fileLocation.equals("")) {
      String fileType = fileLocation.substring(fileLocation.lastIndexOf(".") + 1);

      if (fileType.equals("ppm")) {
        model.addPicture(ImageUtil.readPPM(fileLocation), model.getCurrCount());
      } else {
        model.addPicture(ImageUtil.readAll(fileLocation), model.getCurrCount());
      }

      // Get the buffered image of the newly added to draw
      view.setImage(this.currImage().getBufferedImage());
      this.refreshHistogram();
    }
  }

  @Override
  public void savePicture(String fileLocation) {
    if (!fileLocation.equals("")) {
      this.currImage().save(fileLocation);
    }
  }

  @Override
  public void flipPicture(String type) {
    // Checks there is an image loaded, otherwise have view display pop up to notify user
    if (!(model.getCurrCount().equals("0"))) {
      Command c = new VerticalFlip(model, this.currImage(), model.getCurrCount());
      if (type.equals("horizontal")) {
        c = new HorizontalFlip(model, this.currImage(), model.getCurrCount());
      }
      c.execute();
      view.setImage(model.getPicture(model.getLastCount()).getBufferedImage());
    } else {
      view.pictureError();
    }
  }

  @Override
  public void brighten(int value) {
    // Checks there is an image loaded, otherwise have view display pop up to notify user
    if (!(model.getCurrCount().equals("0"))) {
      String type = "b";

      String amount = String.valueOf(value);
      Command c = new BrightenImage(model, amount, type, this.currImage(), model.getCurrCount());
      c.execute();
      view.setImage(this.currImage().getBufferedImage());
      this.refreshHistogram();
    } else {
      view.pictureError();
    }
  }

  @Override
  public void filter(String type) {
    // Checks there is an image loaded, otherwise have view display pop up to notify user
    if (!(model.getCurrCount().equals("0"))) {
      Command c = new FilterImage(model, this.currImage(), model.getCurrCount(), type);
      c.execute();
      view.setImage(this.currImage().getBufferedImage());
      this.refreshHistogram();
    } else {
      view.pictureError();
    }
  }

  @Override
  public void greyscale(String type) {
    // Checks there is an image loaded, otherwise have view display pop up to notify user
    if (!(model.getCurrCount().equals("0"))) {
      Command c = new GreyScale(model, this.currImage(), type, model.getCurrCount());
      c.execute();
      view.setImage(this.currImage().getBufferedImage());
      this.refreshHistogram();
    } else {
      view.pictureError();
    }
  }

  @Override
  public void colorTransform(String type) {
    // Checks there is an image loaded, otherwise have view display pop up to notify user
    if (!(model.getCurrCount().equals("0"))) {
      Command c = new ColorTransform(model, this.currImage(), type, model.getCurrCount());
      c.execute();
      view.setImage(this.currImage().getBufferedImage());
      this.refreshHistogram();
    } else {
      view.pictureError();
    }
  }

  @Override
  public void undo() {
    if (!(model.getCurrCount().equals("1"))) {
      model.undo();
      view.setImage(this.currImage().getBufferedImage());
      this.refreshHistogram();
    }
  }

  @Override
  public void downscaleImage(String heightRatio, String widthRatio)  {
    if (!(model.getCurrCount().equals("0"))) {
      Command c = new ScaleImage(model, heightRatio, widthRatio,
              this.currImage(), model.getCurrCount());
      c.execute();
      view.setImage(this.currImage().getBufferedImage());
      this.refreshHistogram();
    } else {
      view.pictureError();
    }
  }
}
