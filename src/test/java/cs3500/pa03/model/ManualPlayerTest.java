package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.controller.PlayerShotData;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ManualPlayerTest {
  private ManualPlayer mp;
  private PlayerShotData psd;

  /**
   * initializing the manual player and the shot data
   */
  @BeforeEach
  public void initializeData() {
    psd = new PlayerShotData();
    mp = new ManualPlayer(psd);
  }

  /**
   * Testing if the override works properly
   */
  @Test
  public void testIsManual() {
    assertTrue(mp.isManual());
  }


  /**
   * testing if the setup for a player works properly
   */
  @Test
  public void testSetup() {
    Map<ShipType, Integer> map = new LinkedHashMap<>();
    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 2);
    map.put(ShipType.DESTROYER, 2);
    map.put(ShipType.SUBMARINE, 1);
    List<Ship> shipList = mp.setup(6, 6, map);
    assertEquals(6, shipList.size());

    //since the carrier ship is 6 units, 2 battleships are 10 units,
    //2 destroyers are 8 units, and 1 submarine is 3 units, we know that the total
    //cells that the ships take up must be 6+10+8+3=27
    String stringification = mp.toString();
    int shipCount = 0;
    for (int i = 0; i < stringification.length(); i++) {
      if (stringification.charAt(i) == 'S') {
        shipCount += 1;
      }
    }
    assertEquals(27, shipCount);
  }

  /**
   * testing if the registration of shooting works properly
   */
  @Test
  public void testShooting() {
    Map<ShipType, Integer> map = new LinkedHashMap<>();
    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 2);
    map.put(ShipType.DESTROYER, 2);
    map.put(ShipType.SUBMARINE, 1);
    mp.setup(6, 6, map);

    ArrayList<Coord> shots = new ArrayList<>();
    shots.add(new Coord(1, 2));
    shots.add(new Coord(0, 0));
    shots.add(new Coord(5, 5));
    psd.updateShots(shots);

    List<Coord> takenShots = mp.takeShots();

    assertTrue(takenShots.get(0).equals(shots.get(0)));
    assertTrue(takenShots.get(1).equals(shots.get(1)));
    assertTrue(takenShots.get(2).equals(shots.get(2)));
  }

  /**
   * Testing if the .name function works properly
   */
  @Test
  public void testName() {
    assertEquals("Bagelsause", mp.name());
  }

  /**
   * testing if a group of all direct hits registers correctly
   */
  @Test
  public void testReportingDamage() {
    Map<ShipType, Integer> map = new LinkedHashMap<>();
    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 2);
    map.put(ShipType.DESTROYER, 1);
    map.put(ShipType.SUBMARINE, 1);
    List<Ship> shipList = mp.setup(6, 6, map);

    List<Coord> directHitShots = new ArrayList<>();
    for (Ship s : shipList) {
      directHitShots.add(s.getCoord());
    }

    String stringification = mp.toString();
    int shipCount = 0;
    for (int i = 0; i < stringification.length(); i++) {
      if (stringification.charAt(i) == 'S') {
        shipCount += 1;
      }
    }

    assertEquals(directHitShots, mp.reportDamage(directHitShots));

    String postDamange = mp.toString();
    int shipsRemaining = 0;
    for (int i = 0; i < stringification.length(); i++) {
      if (postDamange.charAt(i) == 'S') {
        shipsRemaining += 1;
      }
    }

    assertEquals(shipCount - directHitShots.size(), shipsRemaining);
  }

  /**
   * tests if the ship amount is equal to the ships that should have been created with it
   */
  @Test
  public void testShipAmount() {
    Map<ShipType, Integer> map = new LinkedHashMap<>();
    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 2);
    map.put(ShipType.DESTROYER, 1);
    map.put(ShipType.SUBMARINE, 1);
    List<Ship> shipList = mp.setup(6, 6, map);

    assertEquals(mp.getShipAmount(), shipList.size());
  }

  @Test
  public void testShotMinimum() {
    Map<ShipType, Integer> map = new LinkedHashMap<>();
    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 2);
    map.put(ShipType.DESTROYER, 2);
    map.put(ShipType.SUBMARINE, 1);
    mp.setup(6, 6, map);

    assertEquals(36, mp.getShotMaximum());
  }
}