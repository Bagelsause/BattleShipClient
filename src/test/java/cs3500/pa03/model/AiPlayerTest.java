package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * representing a testing class for the AiPlayer player implementation
 */
class AiPlayerTest {

  /**
   * testing if the focused shooting from the AiPlayer actually focuses around the specific point
   */
  @Test
  public void testFocusedShooting() {
    Map<ShipType, Integer> map = new LinkedHashMap<>();
    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 1);
    map.put(ShipType.DESTROYER, 1);
    map.put(ShipType.SUBMARINE, 1);

    AiPlayer ap = new AiPlayer();
    ap.setup(6, 6, map);

    List<Coord> randomShots = ap.takeShots();

    Coord hitShot = randomShots.get(0);
    List<Coord> hitShots = new ArrayList<>();
    hitShots.add(hitShot);

    ap.successfulHits(hitShots);

    List<Coord> focusedShots = ap.takeShots();
    int surroundingShots = 0;
    for (Coord c : focusedShots) {
      if (Math.abs(c.getX() - hitShot.getX()) <= 1 && Math.abs(c.getY() - hitShot.getY()) <= 1
          && (Math.abs(c.getX() - hitShot.getX()) != Math.abs(c.getY() - hitShot.getY()))) {
        surroundingShots += 1;
      }
    }

    if (hitShot.getX() == 0 && hitShot.getY() == 0) {
      assertEquals(2, surroundingShots);
    } else if (hitShot.getX() == 0 && hitShot.getY() == 5) {
      //if the selected coord is in the bottom left corner
      assertEquals(2, surroundingShots);
    } else if (hitShot.getX() == 5 && hitShot.getY() == 0) {
      //if the selected coord is in the top right corner
      assertEquals(2, surroundingShots);
    } else if (hitShot.getX() == 5 && hitShot.getY() == 5) {
      //if the selected coord is in the bottom right corner
      assertEquals(2, surroundingShots);
    } else if (hitShot.getX() == 0 || hitShot.getY() == 0) {
      //if the selected coord is on the left or top edge
      assertEquals(3, surroundingShots);
    } else if (hitShot.getX() == 5 || hitShot.getY() == 5) {
      //if it's on the bottom or right edges
      assertEquals(3, surroundingShots);
    } else {
      //if it's anywhere else on the board
      assertEquals(4, surroundingShots);
    }
  }
}