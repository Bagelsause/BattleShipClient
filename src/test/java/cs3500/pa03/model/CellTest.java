package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * testing class responsible for ensuring that cells work as intended
 */
class CellTest {

  /**
   * testing if the creation of a cell returns the values that we expect
   */
  @Test
  public void testCellCreation() {
    Cell c = new Cell(new Coord(0, 0), CellType.HIT);
    assertEquals("H", c.getValue());
    assertTrue(c.getCoordinate().equals(new Coord(0, 0)));
    assertFalse(c.isBlank());

    c = new Cell(new Coord(0, 0), CellType.BLANK);
    assertEquals("0", c.getValue());
    assertTrue(c.isBlank());
  }
}