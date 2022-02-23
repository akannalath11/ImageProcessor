Partner Programming Class Assignment: 
Jared Ritchie and Alvin Kannalath

The image we used to modify was a 3 by 3 pixel image that we made ourselves. On GIMP, we colored
in each pixel a different color. Then we made different versions of this picture by manipulating it
to fit the various commands such as brighten, darken, and set it to greyscale depending on the type.
When tested upon, the red, green, and blue values go above 255 and below 0 to make sure that our
code sets the minimum and maximum color values of each of the red, green, and blue components to 0
and 255 respectively.

Citation:
- Image used in SCREENSHOT.jpg located in res/ folder
    “Australian Shepherd Dog Snow - Free Photo on Pixabay.” Pixabay,
    https://pixabay.com/photos/australian-shepherd-dog-snow-5902421/.
- Image license link: https://pixabay.com/service/terms/#license

Documented Changes for HW 6 (GUI Implementation):
New Controller
- Added a new Controller implements the ImageProcessorController and can run the
  program with a GUI view
- Added a Features interface that different features objects can implement. These represent all
  the abilities of the controller paired with the GUI view. The controller takes in a features
  object which is responsible for delegating information to the model and view for each necessary
  method.

Updated Model
- No previous code was changed, although we added new methods to our ImageProcessorModel interface
  to add new abilities for the GUI implementation
- For example, our model features a counting system which can keep track of images that are added,
  and allows for a cleaner implementation in the GUI view. This allows us to store our IPictures
  the same in our model, and in the new View the user no longer needs to input a name for each
  photo variation
- We also added new enhancements, such as the ability to undo, which removes the most recently
  added photo from our model
- Our model also has new method colorValues, which helps to pass data to the controller, that the
  view can then use to visualize the current model

  Updated IPicture Interface
  - Added a public method that can return a BufferedImage of the current IPicture. This passes
    the current photo data as BufferedImage without saving to a file and reimporting it.
  - Added a public method that returns all the data of the pixels that the histogram in the view
    needs to draw.
  - These additions are necessary because the controller needed a way to access this data to pass
    it back to the view. All IPictures should have these methods, so we decided to break SOLID
    principles knowing our previous design was incomplete.

New View
- Added interface GUIView, this interface has public methods that help draw and update the current
  view.
- Added class JFrameView which represents the active GUI. The constructor initializes the starting
  view, which has an empty photo slot and histogram, surrounded by the available action buttons.
  This class has a setImage feature which allows the controller to pass the current image to be
  viewed.

Updated ImageProcessorMain
- This main method now supports inputs for -file and -text, and defaults to the GUI view

/**************************************************************************************/

Document Changes for HW 5:
- Added filter to IPicture interface (this represents filters such as blur and sharpen)
- Added get value to IPixel, so we could access this data within the Picture classes
- Added command class Filter (sends data to filter method in model)
- Added blur and sharpen command as controller option (these make a new Filter Command object)

- Added color transform command object (represents color transformations like sepia)
- Added sepia to controller options which calls this command class
- Added color transform method in IPicture which can apply a kernel of any size given
  a list of values
- Changed various greyscale types to use this new method to help simplify our code

- Large changes
  - Changed all command classes to take in IPicture instead of PPM Picture
  - Model holds IPictures instead of only PPM pictures
  - IPicture interface added public clone method, instead of using concrete constructors to
    duplicate objects (this was needed for better abstraction)
  - Convert PPMPicture class to AllPicture class since any picture type that holds RGB pixel
    data can be stored in the same way
  - Added Transparent Picture class that represents Images that need to store alpha values
    in their pixel array(PNG images for now)

- Save changes
  - The method from the IPicture interface now calls the ImageUtil class to avoid writing data
    with ImageIO through the controller
  - There are two save methods, the first can take any IPicture data and save it into PPM format
  - The second method can take any IPicture data and will save it into any format based on the
   ending of the file name. PNG photos will export with alpha values

We decided to store pixels with types RGB and RGBA. While these classes serve a similar purpose
we thought it would be best to separate them to maximize efficiency when storing large images.
We found that if we stored alpha values for all the types it slowed down our program, so we decided
to only store them when needed.

We decided to change PPMPicture class to AllPicture class since we realized we can represent any
Picture that stores RGB data in the same way. This was helpful in minimizing the number of classes
we needed to represent one IPicture.

/******************************************************************************************/
HOW TO RUN:
Please run our JAR file by running: java -jar ImageProcessor.jar -file RUNME.txt
   - If you want to input commands run with -text
   - the RUNME.txt is located in the /res and demonstrates an example of all our functions
   - Run without java -jar ImageProcessor.jar to run with GUI view

A list of the accepted commands are shown as follows:
    save 'imageName' 'outputFile.ppm'  |  load 'inputFile.ppm' 'imageName'
    vertical-flip 'imageName' 'newName' |  horizontal-flip 'imageName' 'newName'
    value-component 'imageName' 'newName'  |  luma-component 'imageName' 'newName'
    intensity-component 'imageName' 'newName' |  red-component 'imageName' 'newName'
    green-component 'imageName' 'newName'   |  blue-component 'imageName' 'newName'
    brighten 'integerValue' 'imageName' 'newName'
    darken 'integerValue 'imageName' 'newName'
    blur 'imageName' 'newName' | sharpen 'imageName' 'newName'
    sepia 'imageName' 'newName'
    q or quit to exit program

View SCREENSHOT.jpg to view an example of our GUI running

/********************************** Overview *************************************************/
The "Commands" Package
- This Package contains all the command classes which are in charge of manipulating and saving
  images.

- Command Interface
    This interface consists of an execute method which executes a specific command based on the
    user's desire. It also includes an outputMessage method which describes the action that
    was performed by the execute method.

    When instantiated, the commands take in the model so that they can access the available
    photos. The commands that mutate the model create an identical copy the desired Picture,
    add it to the model and then mutate the fields based on the command class.

    Since the command Interface only has two methods it wil be easy to add new commands that
    represent other Picture transformations.

    There are 6 classes that implement the Command interface. They are listed as such:

    BrightenImage:
        This class is in charge of brightening a certain image of the user's liking by a certain
        amount the user inputs.

    GreyScale:
        This class is in charge of converting an image into a greyscale form based on the type that
        is desired by the user.

    HorizontalFlip:
        This class is in charge of horizontally flipping an image of the user's liking.

    VerticalFlip:
        This class is in charge of vertically flipping an image of the user's liking.

    ColorTransform
        This class is in charge of applying a color transformation to a given image. The type
        of transformation is determined by user input.

    FilterImage
        This class applies a filter to an image. The filter type is determined by user input.

    Load Image:
        This class is in charge of loading an image of the user's liking as a PPM form
        with a given filename that the user chooses.

    Save Image:
        This class is in charge of saving an image and outputting it in PPM format to
        a given filename that the user chooses.


The Controller

- ImageProcessingController Interface
    This interface represents a controller for the Image Processing Application.
    It is responsible for handling all inputs and exchanging this information with the model and
    view. It asks the user for specific command inputs and determines when to run and exit
    the application.

- Features Interface
    This interface represents the handling features of a controller used in a GUI for the
    ImageProcessor. A GUI controller should be able to perform each of these given features,
    passing this data from the View to the model. After features are performed, the controller
    must update the view to demonstrate the current model state.

    TextController:
        This class implements ImageProcessorModel. This class takes in a ImageProcessorView,
        Readable object for its input, and an ImageProcessorModel. On runProcessor(), this
        controller reads input from the Readable, determines if the input is a valid command,
        and in the event it is executes the corresponding command class respectively.
        Refactor from Controller -> TextController

        Invalid Inputs:
        - if an invalid command is given, it will ask the user to enter a valid command
        - if a valid command is given but missing a command input or given an invalid input type,
         it will ask to re-enter whole command
        - the program will not end until the input given is q or quit

    GUIController:
        This class is a controller that runs the image processor with a GUI view. This
        class takes in an object of the features interface. When run is
        called, this class delegate's to the Feature object which then launches
        its current GUI View.

    BaseProcessorFeatures:
        This class performs all the actions given by the Features interface. This base
        implementation takes in a model and GUI view and communicates data between them. When the
        view calls on this class, it tells the model how to update, and transmit the updated model
        data back to the view for drawing purposes.

        Invalid Inputs:
        - if a button is clicked before a picture has been loaded, an error message will pop
        indicating the error to the user, and will allow for as many retries as possible
        - the user can also undo until the initial image that was loaded is present. When clicked
        afterwards, the image will simply stay constant until a valid entry is pressed

    ImageProcessorMain:
        This class is to allow for user to run the ImageProcessor using the console.

    ImageProcessorGUIMain:
        This class is to allow the user to run the GUI ImageProcessor.

    ImageUtil:
        This class contains utility methods to read a PPM image, save a PPM image, read
        any other filetype(.png, .jpg, .bmp), and save any filetype(.png, .jpg, .bmp). These
        methods are able to save an IPicture to any file by converting the data to match the
        output file type. If an AllPicture object is saved as a PNG the default alpha values will
        be set to 255, if a TransparentPicture is saved to .jpg or .bmp the alpha values are
        ignored.


The Model

- ImageProcessorModel Interface:
    This interface contains methods that allow a model to add pictures to itself. The addPicture
    method in this interface is tasked with adding an edited image to a map of images. The
    getPicture method gets the specific image from the map pictures based on the name
    the picture is stored with.

    PictureStorageModel:
        This class represents a model for the ImageProcessor. This class represents a storage
        location for all pictures loaded and created. It is able to store each picture with an
        associated name, and can check if an image is already added. The model also keeps a count
        of each image added to itself, this helps to keep track through the GUI view.

- IPixel Interface:
    This interface represents a single Pixel of an Image and its data values. It contains methods
    that manipulates individual pixels based on the command that was called. The color values
    of each pixel can be read as well.

    AbstractPixel
        This abstract class was created to represent shared code between the two pixel types.
        Since we want RGBA pixels to also have the functionality of the RGB methods, we abstracted
        the code to work with both object types.

    RGBPixel:
        This class represents a single pixel of an image that has a Red, Green, and Blue color
        values. The methods within this class are able to manipulate the individual pixels based
        on the unique commands.

    RGBAPixel:
         This class represents a single RGBA pixel of an image that has a Red, Green, Blue,
         and Alpha color values. The methods within this class are able to manipulate the
         individual pixels based on the unique commands. This class specifically, is designed to
         store data from PNG images, or any other type that needs to keep track of transparency.
         While we did not need to include alpha values, we thought it was important to have
         for future implementations.

- IPicture Interface:
    This interface represents a picture that a ImageProcessor model can store. This interface
    represents all the operations the Image Processor can perform on a single image.

    AbstractPicture:
        This abstract class is responsible for minimizing code duplication between AllPictures and
        PNG pictures. Since all transformations thus far do not include alpha value changes,
        many of the methods were identical in both of these classes and could be abstracted.

    AllPicture:
        This class represents an image and its data and creates a IPicture with a given
        width, height, max Color value, and list of all the RGB pixels that make up this image. It
        contains methods that apply the commands to each pixel in the image, and essentially
        mutates the entire Picture based on the specific command given.

    TransparentPicture:
        This class represents a transparent image and its data and creates a IPicture with a given
        width, height, max Color value, and list of all the RGBA pixels that make up this image. It
        contains methods that apply the commands to each pixel in the image, and essentially
        mutates the entire Picture based on the specific command given. This class specifically
        holds an array of RGBA pixels which will help to keep track of transparency in the future.
        For now, only PNG images are represented by this object, but we designed this to support
        any filetype that needs to track transparency.

    Model Expansion Logic:
    We designed the IPixel interface to account for new pixel types with different data values,
    such as a pixel that stores an alpha value. Our AllPicture class allows us to read in
    and convert any image type with RGB pixels to the same object. The implementation of our
    PictureStorage class is essential to help store data without updated any fields within the
    controller class itself. This ensures the model is completely separate from reliance on the
    controller or view. With our current design, we can choose to read in any new file type and
    store the data as either an AllPicture or TransparentPicture, giving us flexibility to
    choose what data we want to keep track of.



The View
- This package contains the classes that represent the view for the ImageProcessor.

- ImageProcessorView Interface:
    This interface represents the view for an ImageProcessor. The view can display messages to
    an Appendable notifying user of any changes made.

    This interface contains 1 class which implements the above interface. It is listed as follows:

    TextView:
        This class renders a message and sends it to the Appendable.
        Whatever message needs to be sent to the user is done in this class in
        the renderMessage method.

- ImageProcessorGuiView Interface:
    This interface represents a GUI View for our ImageProcessor. It contains methods that are needed
    for the controller to call on. Specifically these methods help the view take in new data passed
    from the controller to update the displayed view.

    This interface contains 1 class which implements the above interface and is listed as follows:

    GUIView:
        This class represents the GUI view for the ImageProcessor. It starts by opening with a
        blank Picture in the center, surrounded by the available controls. After displaying,
        it waits for the user input to determine what the next action should be.

    Histogram:
        This class draws a Histogram of the current model's data passed to the view by the
        controller. The graphics draw a histogram that is 255 x 255 pixels, drawing the
        frequencies of each pixel color value in the current image as well as the intensity levels.

    View Expansion:
    We designed our view to keep all display methods separate from the controller. While there
    is only one method now, this will help streamline our GUI implementation in the future.


Testing Logic
   Please open res/square.png to view our custom image!

   To test, we created our own 3x3 pixel image that is an original creation with personal
   permission to use for this assignment. To test, we manually manipulated the images in Gimp,
   and read these into our tests to compare them to the mutated objects. To achieve this,
   we overrode equals and hashcode with our own implementation that checked all fields of an
   PPM image were equal.

   To test the controller we used a MockView and MockModel that updated a log. This helped
   test that the controller properly read in data and transmitted it to the view and model.

   To test the two read methods in ImageUtil we compared the read pixel values to the
   string representation of our image. Since we made our image, we know what the correct
   pixel values should be. 
 
   To test saving, we saved a current IPicture and then re imported this data. To confirm
   saving works as expect, we confirmed the data is not altered when exporting from the 
   model to a file type. 

   After testing the RGBPixel and PPMPicture class we also additionally tested that all the
   Command classes work as expected. This confirms data is properly being sent from the
   command class to the picture class and mutating objects as expected.
