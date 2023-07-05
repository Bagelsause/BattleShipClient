package cs3500.pa03.model;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa03.controller.PlayerShotData;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * testing class for the game handler
 */
class ManualGameHandlerTest {
  /**
   * testing if the setup of the gamehandler properly edits the first player's board perspective
   */
  @Test
  public void testSetups() {
    AbstractPlayer p1 = new RandomPlayer();
    AbstractPlayer p2 = new RandomPlayer();

    ManualGameHandler gameHandler = new ManualGameHandler(p1, p2);
    assertFalse(gameHandler.isManual());

    Map<ShipType, Integer> map = new LinkedHashMap<>();
    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 1);
    map.put(ShipType.DESTROYER, 1);
    map.put(ShipType.SUBMARINE, 1);
    gameHandler.runSetups(6, 6, map);

    assertEquals(p1.toString(), gameHandler.toString());
  }

  /**
   * testing if the gamehandler can tell that a freshly initialized program isn't done
   */
  @Test
  public void testBasicBooleans() {
    PlayerShotData psd = new PlayerShotData();
    AbstractPlayer p1 = new ManualPlayer(psd);
    AbstractPlayer p2 = new RandomPlayer();

    ManualGameHandler gameHandler = new ManualGameHandler(p1, p2);

    assertTrue(gameHandler.isManual());
    assertFalse(gameHandler.isDone());
  }

  /**
   * tests if the gamehandler can properly get the player's ship amount,
   * the shot maximum, and string representation
   */
  @Test
  public void testPlayerStats() {
    AbstractPlayer p1 = new RandomPlayer();
    AbstractPlayer p2 = new RandomPlayer();

    Map<ShipType, Integer> map = new LinkedHashMap<>();
    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 1);
    map.put(ShipType.DESTROYER, 1);
    map.put(ShipType.SUBMARINE, 1);

    ManualGameHandler gameHandler = new ManualGameHandler(p1, p2);
    gameHandler.runSetups(6, 6, map);

    assertEquals(p1.getShipAmount(), gameHandler.getShipAmount());
    assertEquals(p1.getShotMaximum(), gameHandler.getShotMaximum());
    assertEquals(p1.toString(), gameHandler.toString());
  }

  /**
   * testing that two players can fire at each other and it will register
   */
  @Test
  public void testFiringShots() {
    PlayerShotData psd = new PlayerShotData();
    ArrayList<Coord> shotList = new ArrayList<>();
    shotList.add(new Coord(0, 0));
    psd.updateShots(shotList);

    Map<ShipType, Integer> map = new LinkedHashMap<>();
    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 1);
    map.put(ShipType.DESTROYER, 1);
    map.put(ShipType.SUBMARINE, 1);

    AbstractPlayer p1 = new ManualPlayer(psd);
    AbstractPlayer p2 = new DudPlayer();
    assertTrue(p2.takeShots().isEmpty());

    ManualGameHandler gameHandler = new ManualGameHandler(p1, p2);
    gameHandler.runSetups(6, 6, map);

    gameHandler.fireShots();
    assertEquals("""
        Opponent Board Data:
            0 0 0 0 0 0
            0 0 0 0 0 0
            0 0 0 0 0 0
            0 0 0 0 0 0
            0 0 0 0 0 0
            0 0 0 0 0 0
            
        Your Board:
            H 0 0 0 0 0
            0 0 0 0 0 0
            0 0 0 0 0 0
            0 0 0 0 0 0
            0 0 0 0 0 0
            0 0 0 0 0 0
        """, p2.toString());
  }

  /**
   * testing the various end results with a dud player
   */
  @Test
  public void testEndResults() {
    //checking if the WIN condition works
    PlayerShotData psd = new PlayerShotData();
    ArrayList<Coord> shotList = new ArrayList<>();
    shotList.add(new Coord(0, 0));
    psd.updateShots(shotList);

    AbstractPlayer p1 = new ManualPlayer(psd);
    AbstractPlayer p2 = new DudPlayer();

    Map<ShipType, Integer> map = new LinkedHashMap<>();
    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 1);
    map.put(ShipType.DESTROYER, 1);
    map.put(ShipType.SUBMARINE, 1);

    ManualGameHandler gameHandler = new ManualGameHandler(p1, p2);
    gameHandler.runSetups(6, 6, map);

    gameHandler.fireShots();

    assertTrue(gameHandler.isDone());
    assertEquals(GameResult.WIN, gameHandler.endResult());

    //Checking if the LOSE condition works
    p1 = new DudPlayer();
    p2 = new ManualPlayer(psd);
    gameHandler = new ManualGameHandler(p1, p2);
    gameHandler.runSetups(6, 6, map);

    gameHandler.fireShots();

    assertTrue(gameHandler.isDone());
    assertEquals(GameResult.LOSE, gameHandler.endResult());

    //Checking if the DRAW condition works
    p1 = new DudPlayer();
    p2 = new DudPlayer();
    gameHandler = new ManualGameHandler(p1, p2);
    gameHandler.runSetups(6, 6, map);

    gameHandler.fireShots();

    assertTrue(gameHandler.isDone());
    assertEquals(GameResult.DRAW, gameHandler.endResult());
  }
}