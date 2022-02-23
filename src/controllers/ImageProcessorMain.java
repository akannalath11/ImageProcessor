package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;

import model.PictureStorageModel;
import model.ImageProcessorModel;
import view.ImageProcessorGUIView;
import view.ImageProcessorView;
import view.GUIView;
import view.TextView;

/**
 * This class represents the main class that runs the Image Processor. This
 * includes the ability to input a file to run, have a text scripting application, or
 * use the basic GUI view for this Image Processor.
 */
public class ImageProcessorMain {

  /**
   * This is the main class where the user can run commands relating to image processing. If
   * -file is given, the program runs the contents inside the file and quits. If -text is given,
   * the user can input commands in the command line. If no arguments are given, the GUI view
   * will open and run based on user input.
   *
   * @param args represents the list of commands that are inputted by the user. If the user
   *             inputs -file, the next String will be the filename for the command script file.
   *             If the user enters -text, the text scripting Image processor will run. If
   *             an invalid command is given it will print an error message and end the program.
   */
  public static void main(String[] args) throws IOException {
    ImageProcessorModel model = new PictureStorageModel();
    ImageProcessorView textView = new TextView(System.out);
    Readable rd = new BufferedReader(new InputStreamReader(System.in));

    if (args.length > 0) {
      for (int i = 0; i < args.length; i++) {
        // If file is given try to get the next, if there is no next output message and quit
        if (args[i].equals("-file")) {
          try {
            Path filePath = Path.of(args[i + 1]);
            String fileContents = Files.readString(filePath);
            // Read in the string, add a quit to the end in case the file does not include it
            rd = new BufferedReader(new StringReader(fileContents + " q"));
            ImageProcessorController controller = new TextController(textView, rd, model);
            controller.runProcessor();
          } catch (IndexOutOfBoundsException e) {
            System.out.println("ERROR: Must input a valid filename after -file");
            return;
          }
        } else if (args[i].equals("-text")) {
          // Run normal textView with System.in and allow user to input commands
          ImageProcessorController controller = new TextController(textView, rd, model);
          controller.runProcessor();
        } else {
          // Quit program if a different invalid input is given
          System.out.println("ERROR: Invalid command given!");
          return;
        }
      }
    } else {
      // If commands are not text file or input, run gui view instead
      ImageProcessorGUIView guiView = new GUIView();
      Features baseFeatures = new BaseProcessorFeatures(model, guiView);
      GUIController controller = new GUIController(baseFeatures);
      controller.runProcessor();
    }
  }
}
