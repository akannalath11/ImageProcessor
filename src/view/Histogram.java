package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * This class draws a Histogram of the current model's data passed to the view by the controller.
 * The graphics draw a histogram that is 255x255 pixels, drawing the frequencies of each
 * pixel color value in the current image.
 */
public class Histogram extends JPanel {
  private int[] redValues;
  private int[] greenValues;
  private int[] blueValues;

  /**
   * Creates a new Histogram will all the array data empty to begin.
   */
  public Histogram() {
    this.redValues = new int[0];
    this.greenValues = new int[0];
    this.blueValues = new int[0];
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g); //take care of paint functionality in super class

    g.setColor(Color.LIGHT_GRAY);
    g.fillRect(40, 0, 255, 255); // Background setup

    // Drawing histogram x-axis title and unit labels
    g.setColor(Color.BLACK);
    g.drawString("Color Values", 140, 275);
    g.drawString("0", 40, 265);
    g.drawString("50", 75, 265);
    g.drawString("100", 125, 265);
    g.drawString("150", 175, 265);
    g.drawString("200", 225, 265);
    g.drawString("250", 275, 265);

    // Colors to be drawn
    Color[] colors = new Color[4];
    colors[0] = Color.RED;
    colors[1] = Color.BLUE;
    colors[2] = Color.GREEN;
    colors[3] = Color.BLACK;
    double max = 1;

    // Calculate Intensity
    int[] intensityValues = new int[256];
    for (int i = 0; i < redValues.length - 1; i++) {
      intensityValues[i] = (redValues[i] + greenValues[i] + blueValues[i]) / 3;
    }

    // Array normalization and setup, get max frequency to normalize all values with
    for (int i = 0; i < redValues.length - 1; i++) {
      int currMax = Math.max(Math.max(redValues[i], greenValues[i]), blueValues[i]);
      if (max < currMax) {
        max = currMax;
      }
    }

    // Drawing histogram y-axis unit labels using max value
    g.setColor(Color.BLACK);
    g.drawString(String.valueOf((int) max), 10, 10);
    g.drawString(String.valueOf((int) max / 2), 10, 120);
    g.drawString("0", 20, 255);


    max = 255 / max; // calculated value to normalize arrays for frequency display

    // Array setup
    ArrayList<int[]> values = new ArrayList();
    values.add(redValues);
    values.add(greenValues);
    values.add(blueValues);
    values.add(intensityValues);

    // For each of the four colors, set the line color and draw the line histogram
    // connecting each point from color value 0 - 255
    for (int j = 0; j < 4; j++) {
      g.setColor(colors[j]);
      int currX = 40;
      int[] currValues = values.get(j);

      for (int i = 0; i < redValues.length - 1; i++) {
        g.drawLine(currX, (int) (255 - (currValues[i] * max)),
                currX + 1, (int) (255 - (currValues[i + 1] * max)));
        currX++;
      }
    }
  }

  /**
   * Updates the values of this Histogram to those the controller has passed to the view.
   * @param redValues The red values of the current models pixel data.
   * @param greenValues The green values of the current models pixel data.
   * @param blueValues The blue values of the current models pixel data.
   */
  public void setValues(int[] redValues, int[] greenValues, int[] blueValues) {
    this.redValues = redValues;
    this.greenValues = greenValues;
    this.blueValues = blueValues;
  }
}
