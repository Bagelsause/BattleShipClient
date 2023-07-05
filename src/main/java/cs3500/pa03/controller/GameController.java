package cs3500.pa03.controller;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.ManualGameHandler;
import cs3500.pa03.model.ManualPlayer;
import cs3500.pa03.model.RandomPlayer;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.view.BoardViewer;
import cs3500.pa03.view.OutputWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * controls the actions between the input/output sources and the player's associated data
 */
public class GameController {
  private final Scanner input;
  private final OutputWriter output;
  private BoardViewer viewer;
  private final ManualGameHandler model;
  private int height;
  private int width;
  private final PlayerShotData playerShotData;

  /**
   * sets the inputs and outputs towards the gamecontroller, and creates the associated model
   *
   * @param in a readable input that the user interacts with
   * @param out an output source that the user can see
   */
  public GameController(Readable in, Appendable out) {
    input = new Scanner(in);
    output = new OutputWriter(out);
    playerShotData = new PlayerShotData();
    model = new ManualGameHandler(new ManualPlayer(playerShotData), new RandomPlayer());
  }

  /**
   * runs associated introduction and setups for the game
   */
  public void initialize() {
    viewer = new BoardViewer(output);
    viewer.introduction();

    //should contain something that looks like coordinates
    height = input.nextInt();
    width = input.nextInt();

    //if they're invalid dimensions, ask until they are valid
    while (height > 15 || height < 6 || width > 15 || width < 6) {
      viewer.retryIntroduction();
      height = input.nextInt();
      width = input.nextInt();
    }

    int maxFleetSize = Math.min(height, width);
    viewer.shipStartup(maxFleetSize);

    ArrayList<Integer> shipAmount = new ArrayList<>();
    int sizeSum = 0;
    while (ShipType.values().length > shipAmount.size()) {
      int shipSize = input.nextInt();
      sizeSum += shipSize;
      shipAmount.add(shipSize);
    }

    boolean improperSize = false;
    for (int ship : shipAmount) {
      if (ship < 1) {
        improperSize = true;
        break;
      }
    }

    while (improperSize || sizeSum > maxFleetSize) {
      viewer.reportInvalidShips(maxFleetSize);
      shipAmount = new ArrayList<>();

      sizeSum = 0;
      while (ShipType.values().length > shipAmount.size()) {
        int shipSize = input.nextInt();
        sizeSum += shipSize;
        shipAmount.add(shipSize);
      }

      for (int i = 0; i < shipAmount.size(); i++) {
        int ship = shipAmount.get(i);
        if (ship < 1) {
          improperSize = true;
          break;
        }
        if (i == shipAmount.size() - 1) {
          improperSize = false;
        }
      }
    }

    Map<ShipType, Integer> shipMapping = new LinkedHashMap<>();

    for (ShipType ship : ShipType.values()) {
      shipMapping.put(ship, shipAmount.get(ship.ordinal()));
    }

    model.runSetups(height, width, shipMapping);
  }

  /**
   * triggers the firing of both side's shots and intakes the user's inputted shots
   */
  public void runTurns() {
    while (!model.isDone()) {
      if (model.isManual()) {
        //should output the prompt to request
        boolean invalidShooting = true;
        int shotAmount = model.getShipAmount();
        int min = model.getShotMaximum();

        ArrayList<Coord> shotList = new ArrayList<>();
        while (invalidShooting) {
          invalidShooting = false;
          viewer.showBoard(model.toString(), Math.min(shotAmount, min));

          shotList = new ArrayList<>();
          while (shotList.size() < Math.min(shotAmount, min)) {
            int xcoord = input.nextInt();
            int ycoord = input.nextInt();

            if (xcoord < 0 || xcoord >= width || ycoord < 0 || ycoord >= height) {
              invalidShooting = true;
              break;
            }
            shotList.add(new Coord(xcoord, ycoord));
          }

        }

        playerShotData.updateShots(shotList);
      }

      model.fireShots();
    }
    this.end();
  }

  /**
   * on the game ending, display an end game message to the user, the result being
   * the parameter to the view controller
   */
  public void end() {
    viewer.endGameMessage(model.endResult());
  }
}
