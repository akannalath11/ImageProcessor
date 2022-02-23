package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * A model for the ImageProcessor. PictureStorage class represents a storage location for all
 * pictures loaded and created. It is able to store each Picture with an associated name,
 * and can check if an image is already added.
 */
public class PictureStorageModel implements ImageProcessorModel {
  private Map<String, IPicture> pictureList;
  private int count;
  private Stack<Integer> countStack;
  private Stack<Integer> redoStack;

  /**
   * Creates a new PictureStorage object that has no Images added by default.
   */
  public PictureStorageModel() {
    this.pictureList = new HashMap<>();
    this.count = 0;
    this.countStack = new Stack<>();
    this.redoStack = new Stack<>();
  }

  @Override
  public void addPicture(IPicture picture, String pictureName) throws IllegalArgumentException {
    if ((pictureName == null) || (picture == null)) {
      throw new IllegalArgumentException("Need a non-null picture and name to add a Picture!");
    }
    this.pictureList.put(pictureName, picture.clone());
    this.countStack.push(this.count);
    this.count++;
  }

  @Override
  public IPicture getPicture(String pictureName) throws IllegalArgumentException {
    for (Map.Entry<String, IPicture> p : pictureList.entrySet()) {
      if (p.getKey().equals(pictureName)) {
        return p.getValue();
      }
    }
    throw new IllegalArgumentException("No picture is loaded with that name!");
  }

  @Override
  public String getCurrCount() {
    if (this.countStack.empty()) {
      return String.valueOf(0);
    } else {
      return String.valueOf(this.countStack.peek() + 1);
    }
  }

  @Override
  public String getLastCount() {
    if (this.countStack.empty()) {
      return String.valueOf(0);
    } else {
      return String.valueOf(this.countStack.peek());
    }
  }

  @Override
  public void undo() {
    this.redoStack.push(this.countStack.pop());
    this.count--;
  }

  @Override
  public int[] colorValues(String type) {
    return this.getPicture(this.getLastCount()).colorValues(type);
  }
}
