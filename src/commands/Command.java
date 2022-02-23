package commands;

/**
 * This interface represents a Command object that can execute a method based on the respective
 * implementation.
 */
public interface Command {

  /**
   * Executes the current command mutating the current model.
   */
  void execute();

  /**
   * Returns a string explaining what the executed command changed.
   *
   * @return returns the result in a String message
   */
  String outputMessage();
}
