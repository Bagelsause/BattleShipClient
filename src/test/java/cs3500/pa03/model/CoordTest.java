package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * testing class responsible for determining if coordinates are working properly
 */
class CoordTest {

  /**
   * tests if all the functions of coordinate initialization are working
   * and if two coordinates can be equal to each other
   */
  @Test
  public void testInitialization() {
    Coord c1 = new Coord(1, 0);
    Coord c2 = new Coord(0, 1);

    assertEquals(1, c1.getX());
    assertEquals(1, c2.getY());
    assertFalse(c1.equals(c2));

    Coord c3 = new Coord(1, 0);
    assertTrue(c1.equals(c3));
  }
}