package controllers;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import model.IPicture;
import model.IPixel;
import model.TransparentPicture;
import model.AllPicture;
import model.RGBAPixel;
import model.RGBPixel;

/**
 * This class contains utility methods to read an image from file and store its data as
 * an IPicture object.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and output data as a single PPMPicture.
   *
   * @param filename the path of the file.
   */
  public static AllPicture readPPM(String filename) throws IllegalArgumentException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Given file is not formatted properly!");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());
    String token;
    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }

    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();
    RGBPixel[][] pictArray = new RGBPixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        pictArray[i][j] = new RGBPixel(r, g, b);
      }
    }
    return new AllPicture(width, height, maxValue, pictArray);
  }

  /**
   * Saves a picture by outputting all the data of a IPicture object into PPM format. If the
   * data includes alpha values, the data is first converted to RGB values then saved.
   *
   * @param width      The width of the saved picture
   * @param height     The height of the saved picture
   * @param max        The max color value for the picture
   * @param pixelArray All the pixel data for this IPicture
   * @param fileName   the file name to save the file to
   */
  public static void savePPM(int width, int height, int max,
                             IPixel[][] pixelArray, String fileName) {
    try {
      FileWriter myWriter = new FileWriter(fileName);
      myWriter.write("P3" + "\n");
      myWriter.write(width + " ");
      myWriter.write(height + " ");
      myWriter.write(max + " " + "\n");
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          myWriter.write(pixelArray[i][j].toString());
        }
      }
      myWriter.close();
    } catch (IOException e) {
      System.out.println("An error occurred outputting to the file!.");
      e.printStackTrace();
    }
  }

  /**
   * Reads an image in any format and saves the data as a IPicture type. If the file is a png,
   * it will store it as a TransparentPicture object including the alpha values, otherwise it will
   * store it as an AllPicture object.
   *
   * @param filename the filename and location of the given file
   * @return a IPicture that holds all of this images data
   * @throws IllegalArgumentException if the given Picture is in an invalid format
   */
  public static IPicture readAll(String filename) throws IllegalArgumentException {
    String fileType = filename.substring(filename.lastIndexOf(".") + 1);
    try {
      // Try to read the image file into a bufferedImage
      BufferedImage image = ImageIO.read(new File(filename));
      int pictureWidth = image.getWidth();
      int pictureHeight = image.getHeight();
      int pictureMax = 255;
      IPixel[][] pictArray;
      // If it's a png image, include alpha values otherwise don't
      if (fileType.equals("png")) {
        pictArray = new RGBAPixel[pictureHeight][pictureWidth];
      } else {
        pictArray = new RGBPixel[pictureHeight][pictureWidth];
      }

      for (int i = 0; i < pictureHeight; i++) {
        for (int j = 0; j < pictureWidth; j++) {
          int r = new Color(image.getRGB(j, i)).getRed();
          int g = new Color(image.getRGB(j, i)).getGreen();
          int b = new Color(image.getRGB(j, i)).getBlue();
          if (fileType.equals("png")) {
            int a = new Color(image.getRGB(j, i)).getAlpha();
            pictArray[i][j] = new RGBAPixel(r, g, b, a);
          } else {
            pictArray[i][j] = new RGBPixel(r, g, b);
          }
        }
      }

      if (fileType.equals("png")) {
        return new TransparentPicture(pictureWidth, pictureHeight,
                pictureMax, (RGBAPixel[][]) pictArray);
      } else {
        return new AllPicture(pictureWidth, pictureHeight, pictureMax, (RGBPixel[][]) pictArray);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("The given file is not in the correct format");
    }
  }

  /**
   * Saves a picture by outputting all the data of a IPicture object into the given file name.
   * The file type is determined off the ending of the file name. If the type is .png, it will
   * include alpha values of this IPicture. The default alpha value for AllPictures is 255 to
   * represent no transparency.
   *
   * @param width      The width of the saved picture
   * @param height     The height of the saved picture
   * @param pixelArray All the pixel data for this IPicture
   * @param fileName   the file name to save the file to
   */
  public static void saveAll(int width, int height, IPixel[][] pixelArray, String fileName) {
    String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
    BufferedImage bufferedImage;
    if (!(fileType.equals("png"))) {
      bufferedImage = new BufferedImage(width, height,
              BufferedImage.TYPE_INT_RGB);
    } else {
      bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    }
    saveHelper(bufferedImage, fileName, fileType, pixelArray);
  }

  /**
   * Sets colors for a buffered Image based on the file type, and writes the data to a file
   * with the given file name.
   *
   * @param image    The buffered image to add pixel data to
   * @param fileName The filename to write this image to
   * @param fileType The type of file to save as
   * @param pixelArray The 2D array of pixels that represent the saved image
   */
  private static void saveHelper(BufferedImage image, String fileName,
                                 String fileType, IPixel[][] pixelArray) {
    int height = image.getHeight();
    int width = image.getWidth();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        IPixel currPixel = pixelArray[i][j];
        int red = (int) currPixel.getValue("red");
        int green = (int) currPixel.getValue("green");
        int blue = (int) currPixel.getValue("blue");
        if (fileType.equals("png")) {
          int alpha = (int) currPixel.getValue("alpha");
          Color currColor = new Color(red, green, blue, alpha);
          image.setRGB(j, i, currColor.getRGB());
        } else {
          Color currColor = new Color(red, green, blue);
          image.setRGB(j, i, currColor.getRGB());
        }
      }
    }
    try {
      File outputFile = new File(fileName);
      ImageIO.write(image, fileType, outputFile);
    } catch (IOException i) {
      throw new IllegalArgumentException("Unable to write to the file name");
    }
  }
}

