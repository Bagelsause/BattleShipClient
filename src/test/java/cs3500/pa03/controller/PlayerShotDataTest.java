package cs3500.pa03.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import cs3500.pa03.model.Coord;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

/**
 * testing class responsible for testing if the PlayerShotData is updating properly
 */
class PlayerShotDataTest {

  /**
   * testing if the initialization and changing of the shot data is working
   */
  @Test
  public void testInitAndChanges() {
    PlayerShotData psd = new PlayerShotData();
    ArrayList<Coord> coordList = new ArrayList<>();
    assertNull(psd.getShots());
    psd.updateShots(coordList);
    assertEquals(coordList, psd.getShots());
  }
}