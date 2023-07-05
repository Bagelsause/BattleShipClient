package cs3500.pa03.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * represents a ship on the battleship board
 */
public class Ship {
  private final Coord topLeft;
  private final int length;
  private final String direction;

  /**
   * creates a ship with it's associated coordinate, length, and direction
   *
   * @param tl the top-left most coordinate of the ship
   * @param len the integer length of the ship
   * @param dir the direction of the ship, VERTICAL means downwards, HORIZONTAL means rightwards
   */
  @JsonCreator
  public Ship(@JsonProperty("coord") Coord tl, @JsonProperty("length") int len,
              @JsonProperty("direction") String dir) {
    topLeft = tl;
    length = len;
    direction = dir;
  }

  /**
   * returns the top-left most coordinate
   *
   * @return the top left coordinate of the ship
   */
  public Coord getCoord() {
    return topLeft;
  }

  /**
   * returns the length of the ship
   *
   * @return the length of the ship
   */
  public int getLength() {
    return length;
  }

  /**
   * returns the direction of the ship
   *
   * @return the direction of the ship
   */
  public String getDirection() {
    return direction;
  }
}
