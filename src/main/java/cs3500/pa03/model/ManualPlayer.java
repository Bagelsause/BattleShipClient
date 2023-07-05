package cs3500.pa03.model;

import cs3500.pa03.controller.PlayerShotData;
import java.util.ArrayList;
import java.util.List;

/**
 * represents a player operating the game manually
 */
public class ManualPlayer extends AbstractPlayer {
  private final PlayerShotData dataLink;

  public ManualPlayer(PlayerShotData dl) {
    dataLink = dl;
  }

  /**
   * through the user's inputs, returns the shots that the user makes and sets
   * all of them to misses for now (until they are updated in the successfulHits call)
   *
   * @return the list of coordinates of the shots the user inputted
   */
  @Override
  public List<Coord> takeShots() {
    ArrayList<Coord> shotsTaken = dataLink.getShots();
    for (Coord c : shotsTaken) {
      opponentBoard.get(c.getY()).set(c.getX(), new Cell(c, CellType.MISS));
    }
    return shotsTaken;
  }

  /**
   * a return to check if the player we're looking at is a manual player
   */
  @Override
  public boolean isManual() {
    return true;
  }
}
