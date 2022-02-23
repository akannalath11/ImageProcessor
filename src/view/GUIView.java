package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.util.Hashtable;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileNameExtensionFilter;

import controllers.Features;

/**
 * Represents the GUI view for the ImageProcessor. Opens with a blank Picture in the center,
 * surrounded by the available controls. After displaying, waits for user input to determine
 * the next action.
 */
public class GUIView extends JFrame implements ImageProcessorGUIView {

  private JLabel fileOpenDisplay;
  private JButton fileOpenButton;
  private JButton fileExportButton;
  private JButton undoButton;

  private JLabel imageLabel;

  private JButton flipVerticalButton;
  private JButton flipHorizontalButton;
  private JButton sharpenButton;
  private JButton blurButton;

  private JButton brightenApplyButton;
  private JSlider brightnessSlider;

  private JButton greyscaleApplyButton;
  private JComboBox greyScaleBoxOptions;

  private JButton colorApplyButton;
  private JComboBox colorTransBoxOptions;

  private Histogram histogram;

  private JButton downScaleButton;

  /**
   * Constructs the default GUI view for the Image Processor. Opens with Border layout, with
   * an empty center where the picture is displayed. The left panel has an empty histogram and the
   * right panel holds the image transformation control. The top panel has control buttons
   * like import, export and undo.
   */
  public GUIView() {
    super("Custom Image Processor");

    JPanel mainPanel;
    JScrollPane imageScroll;
    JPanel editButtons;
    JPanel brightenSection;
    JPanel greyscaleSection;
    JPanel colorTransSection;
    JPanel downScaleSection;
    JScrollPane mainScrollPane;

    setSize(1000, 1000);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Set main to JPanel with borderLayout
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());

    this.setMinimumSize(new Dimension(500,500));
    // Add scrollbars to main Panel
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    // Add option boxes to top of Layout
    JPanel optionBoxes = new JPanel();
    optionBoxes.setBorder(BorderFactory.createTitledBorder("Option boxes"));
    optionBoxes.setLayout(new FlowLayout());
    add(optionBoxes, BorderLayout.PAGE_START);

    // File import button set up
    JPanel fileopenPanel = new JPanel();
    fileopenPanel.setLayout(new FlowLayout());
    optionBoxes.add(fileopenPanel);
    fileOpenButton = new JButton("Import Image");
    fileOpenButton.setActionCommand("Open file");
    //fileOpenButton.addActionListener(this);
    fileopenPanel.add(fileOpenButton);
    fileOpenDisplay = new JLabel("File path will appear here");
    fileopenPanel.add(fileOpenDisplay);

    // File export button set up
    fileExportButton = new JButton("Export Image");
    fileExportButton.setActionCommand("Export Picture");
    optionBoxes.add(fileExportButton);

    // Undo button set up
    undoButton = new JButton("Undo");
    undoButton.setActionCommand("Undo Last");
    optionBoxes.add(undoButton);

    // Set up JLabel to store current image, add scroll Pane with it
    imageLabel = new JLabel();
    imageLabel.setMinimumSize(new Dimension(500, 500));
    imageScroll = new JScrollPane(imageLabel);
    imageScroll.setPreferredSize(new Dimension(600, 600));
    this.add(imageScroll);

    // Set up filters, edits, and manipulation buttons on the right panel
    editButtons = new JPanel();
    editButtons.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.fill = GridBagConstraints.HORIZONTAL;

    // Adding Flip buttons to Right Panel
    flipVerticalButton = new JButton("Vertical Flip");
    flipVerticalButton.setActionCommand("Flip Picture Vertical");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.gridy = 0;
    c.weightx = .5;
    editButtons.add(flipVerticalButton, c);
    flipHorizontalButton = new JButton("Horizontal Flip");
    flipHorizontalButton.setActionCommand("Flip Picture Horizontal");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 1;
    c.gridy = 0;
    c.weightx = .5;
    editButtons.add(flipHorizontalButton, c);

    // Adding Sharpen and Blur to right panel
    blurButton = new JButton("Blur Button");
    blurButton.setActionCommand("Blur Picture");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.gridy = 1;
    c.weightx = .5;
    editButtons.add(blurButton, c);
    sharpenButton = new JButton("Sharpen Button");
    sharpenButton.setActionCommand("Sharpen Picture");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 1;
    c.gridy = 1;
    c.weightx = .5;
    editButtons.add(sharpenButton, c);

    // Adding Brighten/Darken with its own panel section, add entire panel to Right Panel
    brightenSection = new JPanel();
    brightenSection.setBorder(BorderFactory.createTitledBorder("Brighten/Darken"));
    // Add slider to brighten/darken panel
    brightnessSlider = new JSlider(JSlider.HORIZONTAL, -100, 100, 0);
    brightnessSlider.setMajorTickSpacing(10);
    brightnessSlider.setPaintTicks(true);
    // Create the label table
    Hashtable labelTable = new Hashtable();
    labelTable.put(-100, new JLabel("-100"));
    labelTable.put(100, new JLabel("100"));
    labelTable.put(0, new JLabel("0"));
    brightnessSlider.setLabelTable(labelTable);
    brightnessSlider.setPaintLabels(true);
    brightenSection.add(brightnessSlider);
    // Add button under slider
    brightenApplyButton = new JButton("Apply Brighten");
    brightenApplyButton.setActionCommand("Apply Brighten");
    brightenSection.add(brightenApplyButton);
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridwidth = 2;
    c.gridx = 0;
    c.gridy = 2;
    editButtons.add(brightenSection, c);

    // Adding Greyscale section with its own panel to the Right panel
    greyscaleSection = new JPanel();
    greyscaleSection.setBorder(BorderFactory.createTitledBorder("Greyscale"));
    String[] greyscaleTypes = {"intensity", "value", "luma", "red", "green", "blue"};
    greyScaleBoxOptions = new JComboBox(greyscaleTypes);
    greyscaleSection.add(greyScaleBoxOptions);
    // Add button under box selection
    greyscaleApplyButton = new JButton("Apply Greyscale");
    greyscaleApplyButton.setActionCommand("Apply Greyscale Type");
    greyscaleSection.add(greyscaleApplyButton);
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridwidth = 2;
    c.gridx = 0;
    c.gridy = 3;
    editButtons.add(greyscaleSection, c);

    // Adding Color Transformation section
    colorTransSection = new JPanel();
    colorTransSection.setBorder(BorderFactory.createTitledBorder("Color Transformations"));
    String[] colorTypes = {"sepia"};
    colorTransBoxOptions = new JComboBox(colorTypes);
    colorTransSection.add(colorTransBoxOptions);
    // Add button under box selection
    colorApplyButton = new JButton("Apply Transformation");
    colorApplyButton.setActionCommand("Apply Color Transform");
    colorTransSection.add(colorApplyButton);
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridwidth = 2;
    c.gridx = 0;
    c.gridy = 4;
    editButtons.add(colorTransSection, c);

    // Adding downscale button to right panel
    // Adding Brighten/Darken with its own panel section, add entire panel to Right Panel
    downScaleSection = new JPanel();
    downScaleSection.setBorder(BorderFactory.createTitledBorder("Downscale Image"));
    // Add button under slider
    downScaleButton = new JButton("Apply Scale");
    downScaleButton.setActionCommand("Apply Scale");
    downScaleSection.add(downScaleButton);
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridwidth = 2;
    c.gridx = 0;
    c.gridy = 5;
    editButtons.add(downScaleSection, c);

    add(editButtons, BorderLayout.EAST);

    // Adding histogram to left side of frame
    // Histogram will start with no data until a photo is loaded
    histogram = new Histogram();
    histogram.setPreferredSize(new Dimension(310, 300));
    histogram.setMinimumSize(new Dimension(310, 300));
    add(histogram, BorderLayout.WEST);


    pack();
    setVisible(true);
  }

  @Override
  public void addFeatures(Features features) {
    fileOpenButton.addActionListener(evt -> features.loadPicture(this.fileChooser()));
    fileExportButton.addActionListener(evt -> features.savePicture(this.fileSaver()));
    flipVerticalButton.addActionListener(evt -> features.flipPicture("vertical"));
    flipHorizontalButton.addActionListener(evt -> features.flipPicture("horizontal"));
    brightenApplyButton.addActionListener(evt -> features.brighten(brightnessSlider.getValue()));
    undoButton.addActionListener(evt -> features.undo());
    blurButton.addActionListener(evt -> features.filter("blur"));
    sharpenButton.addActionListener(evt -> features.filter("sharpen"));
    greyscaleApplyButton.addActionListener(evt ->
            features.greyscale((String)greyScaleBoxOptions.getSelectedItem()));
    colorApplyButton.addActionListener(evt ->
            features.colorTransform((String)colorTransBoxOptions.getSelectedItem()));
    downScaleButton.addActionListener(evt ->
            features.downscaleImage(entryBox("Please enter height ratio (as decimal): "),
            entryBox("Please enter width ratio(as decimal): ")));
  }

  /**
   * Opens a file browser so the user is able to visually navigate and find a stored image. Returns
   * a String representing the root file path of the image.
   * @return the root file path of the image as a String
   */
  private String fileChooser() {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG & PNG & PPM & BMP", "jpg", "ppm", "jpeg", "png", "bmp");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(GUIView.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      fileOpenDisplay.setText(f.getAbsolutePath());
      return f.getAbsolutePath();
    }
    JOptionPane.showMessageDialog(this, "Must select a photo to import!",
            "Import Error", JOptionPane.PLAIN_MESSAGE);
    return ""; // if nothing is chosen, controller will ignore
  }

  /**
   * Opens a file browser so the user is able to visually navigate and enter a name to
   * save a file. Returns a string with the root file path plus the name they have entered.
   * @return the root file path of the image save location as a String
   */
  private String fileSaver() {
    String fileLocation = "";
    JFrame mainFrame = new JFrame();
    JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG & PNG & PPM & BMP", "jpg", "ppm", "jpeg", "png", "bmp");
    fchooser.setFileFilter(filter);
    fchooser.setDialogTitle("Specify a file to save");

    int userSelection = fchooser.showSaveDialog(mainFrame);

    if (userSelection == JFileChooser.APPROVE_OPTION) {
      File fileToSave = fchooser.getSelectedFile();
      System.out.println("Save as file: " + fileToSave.getAbsolutePath());
      fileLocation = fileToSave.toString();
      return fileLocation;
    }
    JOptionPane.showMessageDialog(this, "Must select file location to save!",
            "Export Error", JOptionPane.PLAIN_MESSAGE);
    return ""; // if nothing is chosen, controller will ignore
  }

  /**
   * Initiates a new pop-up panel that has a text box. The panel waits for user input and
   * returns the input as a string.
   * @param message The message this pop up message should display
   * @return The value the user enter in String format
   */
  private String entryBox(String message) {
    String value = JOptionPane.showInputDialog(this,
            message, 0);
    return value;
  }

  @Override
  public void setImage(BufferedImage image) {
    imageLabel.setIcon(new ImageIcon(image));
    repaint();
    revalidate();
  }

  @Override
  public void drawHistogram(int[] redValues, int[] greenValues, int[] blueValues) {
    histogram.setValues(redValues, greenValues, blueValues);
    repaint();
    revalidate();
  }

  @Override
  public void pictureError() {
    JOptionPane.showMessageDialog(this, "Cannot perform transformation"
                    + " until a photo is loaded!",
            "Edit Error", JOptionPane.PLAIN_MESSAGE);
  }
}
