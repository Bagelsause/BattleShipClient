package cs3500.pa03.model;

/**
 * represents a cell with a coordinate value and a cell value
 */
public class Cell {
  private final Coord coordinate;
  private final CellType value;

  public Cell(Coord coord, CellType val) {
    coordinate = coord;
    value = val;
  }

  /**
   * returns the string interpretation of the cell's value
   *
   * @return a string representing the cell's value
   */
  public String getValue() {
    return switch (value) {
      case HIT -> "H";
      case MISS -> "M";
      case SHIP -> "S";
      default -> "0";
    };
  }

  /**
   * returns the coordinate of the given cell
   *
   * @return the Coordinate XY representation of the cell
   */
  public Coord getCoordinate() {
    return coordinate;
  }

  /**
   * determines if the current cell is blank
   *
   * @return a boolean value if the cell is blank or not
   */
  public boolean isBlank() {
    return value.equals(CellType.BLANK);
  }
}
