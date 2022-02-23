package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Function;

import commands.ColorTransform;
import commands.FilterImage;
import commands.BrightenImage;
import commands.Command;
import commands.GreyScale;
import commands.HorizontalFlip;
import commands.LoadImage;
import commands.MaskImage;
import commands.SaveImage;
import commands.ScaleImage;
import commands.VerticalFlip;
import model.ImageProcessorModel;
import view.ImageProcessorView;

/**
 * This class represents the controller which sets certain inputs as commands to edit images
 * in a certain way.
 */
public class TextController implements ImageProcessorController {
  private ImageProcessorView textView;
  private Readable rd;
  private ImageProcessorModel model;
  private ArrayList<String> outputMessage;
  private final ArrayList<String> startMessage;

  /**
   * This represents the controller which runs the commands on a certain picture in the list of
   * images.
   */
  public TextController(ImageProcessorView textView, Readable rd, ImageProcessorModel model)
          throws IllegalArgumentException {
    if ((textView == null) || (rd == null) || (model == null)) {
      throw new IllegalArgumentException("The given Readable and Textview cannot be null!");
    }
    this.rd = rd;
    this.textView = textView;
    this.model = model;
    this.outputMessage = new ArrayList<String>();
    this.startMessage = new ArrayList<>();
    startMessage.add("Welcome to the custom Image Processor. \n");
    startMessage.add("The available commands are: \n");
    startMessage.add("save 'imageName' 'outputFile.ppm' "
            + " |  load 'inputFile.ppm' 'imageName'\n");
    startMessage.add("vertical-flip 'imageName' 'newName'"
            + " |  horizontal-flip 'imageName' 'newName'\n");
    startMessage.add("value-component 'imageName' 'newName' "
            + " |  luma-component 'imageName' 'newName'\n");
    startMessage.add("intensity-component 'imageName' 'newName' "
            + "|  red-component 'imageName' 'newName'\n");
    startMessage.add("green-component 'imageName' 'newName'  "
            + " |  blue-component 'imageName' 'newName'\n");
    startMessage.add("brighten 'integerValue' 'imageName' 'newName'\n"
            + "darken 'integerValue 'imageName' 'newName'\n");
    startMessage.add("blur 'imageName' 'newName' | "
            + "sharpen 'imageName' 'newName'\n");
    startMessage.add("sepia 'imageName' 'newName'\n");
    startMessage.add("q or quit to exit program \n\n");
  }

  @Override
  public void runProcessor() throws IllegalStateException {
    renderHelp(this.startMessage);

    Scanner scan = new Scanner(this.rd);
    Stack<Command> commands = new Stack<>();

    Map<String, Function<Scanner, Command>> knownCommands = new HashMap<>();
    knownCommands.put("save", (Scanner s) -> new SaveImage(model.getPicture(s.next()), s.next()));
    knownCommands.put("horizontal-flip",
        (Scanner s) -> new HorizontalFlip(model, model.getPicture(s.next()), s.next()));
    knownCommands.put("vertical-flip",
        (Scanner s) -> new VerticalFlip(model, model.getPicture(s.next()), s.next()));
    knownCommands.put("darken",
        (Scanner s) -> new BrightenImage(model, s.next(), "d",
                    model.getPicture(s.next()), s.next()));
    knownCommands.put("brighten",
        (Scanner s) -> new BrightenImage(model, s.next(), "b",
                    model.getPicture(s.next()), s.next()));
    knownCommands.put("value-component",
        (Scanner s) -> new GreyScale(model, model.getPicture(s.next()), "value", s.next()));
    knownCommands.put("luma-component",
        (Scanner s) -> new GreyScale(model, model.getPicture(s.next()), "luma", s.next()));
    knownCommands.put("intensity-component",
        (Scanner s) -> new GreyScale(model,
                    model.getPicture(s.next()), "intensity", s.next()));
    knownCommands.put("red-component",
        (Scanner s) -> new GreyScale(model, model.getPicture(s.next()), "red",
                    s.next()));
    knownCommands.put("green-component",
        (Scanner s) -> new GreyScale(model, model.getPicture(s.next()), "green",
                    s.next()));
    knownCommands.put("blue-component",
        (Scanner s) -> new GreyScale(model, model.getPicture(s.next()), "blue",
                    s.next()));
    knownCommands.put("sepia",
        (Scanner s) -> new ColorTransform(model, model.getPicture(s.next()),
                    "sepia", s.next()));
    knownCommands.put("blur",
        (Scanner s) -> new FilterImage(model, model.getPicture(s.next()), s.next(),
                    "blur"));
    knownCommands.put("sharpen",
        (Scanner s) -> new FilterImage(model, model.getPicture(s.next()), s.next(),
                    "sharpen"));
    knownCommands.put("downscale",
        (Scanner s) -> new ScaleImage(model, s.next(), s.next(),
                    model.getPicture(s.next()), s.next()));
    knownCommands.put("mask",
        (Scanner s) -> new MaskImage(model, model.getPicture(s.next()),
                    s.next(), s.next()));
    knownCommands.put("load", (Scanner s) -> new LoadImage(model, s.next(), s.next()));

    while (scan.hasNext()) {
      Command c;
      String in = scan.next();
      if (in.equalsIgnoreCase("q") || in.equalsIgnoreCase("quit")) {
        return;
      }
      Function<Scanner, Command> cmd = knownCommands.getOrDefault(in, null);
      if (cmd == null) {
        outputMessage.add("Invalid command given, please refer to the command list above!\n");
        renderHelp(outputMessage);
      } else {
        try {
          c = cmd.apply(scan);
          commands.add(c);
          c.execute();
          ArrayList<String> commandMessage = new ArrayList<>();
          commandMessage.add(c.outputMessage());
          renderHelp(commandMessage);
        } catch (IllegalArgumentException e) {
          outputMessage.add("Invalid input to command, please try again!\n");
          renderHelp(outputMessage);
        } catch (InputMismatchException i) {
          outputMessage.add("Invalid integer value, re-enter the whole command with an integer!\n");
          renderHelp(outputMessage);
        } catch (NoSuchElementException n) {
          outputMessage.add("Missing a command input, please re-enter the command!\n");
          renderHelp(outputMessage);
        }
      }
      outputMessage = new ArrayList<String>();
    }
  }


  /**
   * Appends a string to the current Appendable based on the values of each given string in a list.
   *
   * @param printQueue The list of strings to be added
   * @throws IllegalStateException If transmission to the appendable fails
   */
  private void renderHelp(ArrayList<String> printQueue) throws IllegalStateException {
    for (String s : printQueue) {
      try {
        this.textView.renderMessage(s);
      } catch (IOException io2) {
        throw new IllegalStateException("Unable to transmit the message to the appendable!");
      }
    }
  }
}
