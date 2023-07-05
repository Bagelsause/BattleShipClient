package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.Coord;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * testing class for the VolleyJSON format
 */
class VolleyJsonTest {

  /**
   * testing to see if we can initialize and access the JSON elements
   */
  @Test
  public void testInitialization() {
    Coord c1 = new Coord(0, 0);
    Coord c2 = new Coord(1, 2);
    List<Coord> coordList = new ArrayList<>();
    coordList.add(c1);
    coordList.add(c2);

    VolleyJson json = new VolleyJson(coordList);

    assertEquals(c1, json.shots().get(0));
    assertEquals(c2, json.shots().get(1));

    assertEquals(coordList, json.shots());
  }
}