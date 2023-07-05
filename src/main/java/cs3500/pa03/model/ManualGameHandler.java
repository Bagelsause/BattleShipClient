package cs3500.pa03.model;

import java.util.List;
import java.util.Map;

/**
 * the overarching handler for two players, both with their own functionalities
 */
public class ManualGameHandler {
  private final AbstractPlayer player;
  private final AbstractPlayer opponent;
  private List<Coord> playerShots;
  private List<Coord> opponentShots;

  public ManualGameHandler(AbstractPlayer p, AbstractPlayer op) {
    player = p;
    opponent = op;
  }

  /**
   * runs the setup functions on both of the players
   *
   * @param height height of the board
   * @param width width of the board
   * @param specifications the mapping of shiptypes to their amounts
   */
  public void runSetups(int height, int width, Map<ShipType, Integer> specifications) {
    player.setup(height, width, specifications);
    opponent.setup(height, width, specifications);
  }

  /**
   * determines if the game is done
   *
   * @return true if one or both of the players has died
   */
  public boolean isDone() {
    if (playerShots == null && opponentShots == null) {
      return false;
    }

    return player.getShipAmount() == 0 || opponent.getShipAmount() == 0;
  }

  /**
   * checks to see if the main player that we are seeing is a manual player
   *
   * @return true if it is a manual player
   */
  public boolean isManual() {
    return player.isManual();
  }

  /**
   * gets the current player's ship amount
   *
   * @return the integer value of the current perspective's ship count
   */
  public int getShipAmount() {
    return player.getShipAmount();
  }

  /**
   * gets the absolute maximum number of shots the current player can take
   *
   * @return the absolute maximum number of shots the current player can take
   */
  public int getShotMaximum() {
    return player.getShotMaximum();
  }

  /**
   * triggers the firing of shots from both sides and analysis of successes and damages
   * for both players
   */
  public void fireShots() {
    playerShots = player.takeShots();
    opponentShots = opponent.takeShots();

    List<Coord> opponentShotsHit = player.reportDamage(opponentShots);
    List<Coord> playerShotsHit = opponent.reportDamage(playerShots);

    player.successfulHits(playerShotsHit);
    opponent.successfulHits(opponentShotsHit);
  }

  /**
   * returns an appropriate game result if the game is over
   *
   * @return either Win, Lose, or Draw from the perspective of the current player
   */
  public GameResult endResult() {
    if (player.getShipAmount() == 0 && opponent.getShipAmount() == 0) {
      return GameResult.DRAW;
    } else if (opponentShots.isEmpty()) {
      return GameResult.WIN;
    } else {
      return GameResult.LOSE;
    }
  }

  /**
   * stringifies the player's board states
   *
   * @return a string representing the player's perspective of the opponent's board and their own
   */
  @Override
  public String toString() {
    return player.toString();
  }
}
