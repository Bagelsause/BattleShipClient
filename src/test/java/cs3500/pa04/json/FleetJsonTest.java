package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipDirection;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * testing class for the FleetJSON format
 */
class FleetJsonTest {

  /**
   * testing to see if we can initialize and access the JSON elements
   */
  @Test
  public void testInitialization() {
    Ship ship1 = new Ship(new Coord(0, 0), 3, ShipDirection.VERTICAL.toString());
    Ship ship2 = new Ship(new Coord(5, 6), 7, ShipDirection.HORIZONTAL.toString());
    List<Ship> shipList = new ArrayList<>();
    shipList.add(ship1);
    shipList.add(ship2);

    FleetJson json = new FleetJson(shipList);

    assertEquals(ship1, json.ships().get(0));
    assertEquals(ship2, json.ships().get(1));

    assertEquals(shipList, json.ships());
  }
}