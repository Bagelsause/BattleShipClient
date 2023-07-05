package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.ShipType;
import java.util.LinkedHashMap;
import org.junit.jupiter.api.Test;

/**
 * testing class for the SetupJSON format
 */
class SetupJsonTest {

  /**
   * testing to see if we can initialize and access the JSON elements
   */
  @Test
  public void testInitialization() {
    LinkedHashMap<ShipType, Integer> map = new LinkedHashMap<>();
    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 2);
    map.put(ShipType.DESTROYER, 3);
    map.put(ShipType.SUBMARINE, 4);

    SetupJson json = new SetupJson(1, 2, map);

    assertEquals(1, json.width());
    assertEquals(2, json.height());

    //checking that every value in the keyset has the same values both ways
    for (ShipType type : map.keySet()) {
      assertEquals(map.get(type), json.specifications().get(type));
    }

    assertEquals(map, json.specifications());
  }
}