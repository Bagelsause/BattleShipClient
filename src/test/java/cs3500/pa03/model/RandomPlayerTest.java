package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * testing class for the RandomPlayer class
 */
class RandomPlayerTest {

  /**
   * testing if randomly shooting from the RandomPlayer produces
   */
  @Test
  public void testShooting() {
    Map<ShipType, Integer> map = new LinkedHashMap<>();
    map.put(ShipType.CARRIER, 2);
    map.put(ShipType.BATTLESHIP, 1);
    map.put(ShipType.DESTROYER, 1);
    map.put(ShipType.SUBMARINE, 1);

    RandomPlayer rp = new RandomPlayer();
    rp.setup(6, 6, map);

    List<Coord> randomShots = rp.takeShots();

    assertEquals(5, randomShots.size());
  }
}