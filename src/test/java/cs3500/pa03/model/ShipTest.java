package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * testing class for the ship class, it isn't directly operated on,
 * so we are just checking the initialization of the values
 */
class ShipTest {

  /**
   * testing if the initialization of a ship works properly
   */
  @Test
  public void testShipInitialization() {
    Ship s = new Ship(new Coord(1, 2), 5, ShipDirection.HORIZONTAL.toString());

    assertTrue(s.getCoord().equals(new Coord(1, 2)));
    assertEquals(5, s.getLength());
    assertEquals("HORIZONTAL", s.getDirection());
  }
}