package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;

/**
 * an instance of the abstract player that only fires randomly
 */
public class RandomPlayer extends AbstractPlayer {

  public RandomPlayer() {
    previouslyShot = new ArrayList<>();
  }

  /**
   * for the maximum amount of shots, randomly shoot at points on the board
   *
   * @return the list of randomly selected points on the board to shoot at
   */
  @Override
  public List<Coord> takeShots() {
    ArrayList<Coord> shotsFired = new ArrayList<>();
    for (int i = 0; i < Math.min(this.getShotMaximum(), this.getShipAmount()); i++) {
      shotsFired.add(this.takeRandomShot());
    }
    return shotsFired;
  }
}
