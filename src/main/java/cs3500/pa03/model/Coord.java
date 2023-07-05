package cs3500.pa03.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * represents a coordinate position of x and y
 */
public class Coord {
  private final int xcoord;
  private final int ycoord;

  @JsonCreator
  public Coord(@JsonProperty("x") int x, @JsonProperty("y") int y) {
    xcoord = x;
    ycoord = y;
  }

  /**
   * returns the x value of the coordinate
   *
   * @return an integer representing the x coordinate
   */
  public int getX() {
    return xcoord;
  }

  /**
   * returns the y value of the coordinate
   *
   * @return an integer representing the y coordinate
   */
  public int getY() {
    return ycoord;
  }

  /**
   * determines if two coordinates are equal to each other
   *
   * @param other a different coordinate object
   * @return true if the X and Y values are the exact same integers
   */
  public boolean equals(Coord other) {
    return this.getX() == other.getX() && this.getY() == other.getY();
  }
}
