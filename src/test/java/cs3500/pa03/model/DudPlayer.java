package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A dud player that represents a player that just died
 */
public class DudPlayer extends AbstractPlayer {
  /**
   * returns the name "dud"
   *
   * @return the string "dud"
   */
  @Override
  public String name() {
    return "dud";
  }

  /**
   * doesn't set up any ships
   *
   * @param height the height of the board, range: [6, 15] inclusive
   * @param width the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return an empty arraylist of empty ships
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    personalBoard = new ArrayList<>();
    opponentBoard = new ArrayList<>();
    activeShipList = new ArrayList<>();
    for (int i = 0; i < height; i++) {
      ArrayList<Cell> blankRow = new ArrayList<>();
      for (int j = 0; j < width; j++) {
        blankRow.add(new Cell(new Coord(j, i), CellType.BLANK));
      }
      personalBoard.add(blankRow);
      blankRow = new ArrayList<>();
      for (int j = 0; j < width; j++) {
        blankRow.add(new Cell(new Coord(j, i), CellType.BLANK));
      }
      opponentBoard.add(blankRow);
    }
    return new ArrayList<>();
  }

  /**
   * doesn't fire any shots
   *
   * @return an empty list of shots
   */
  @Override
  public List<Coord> takeShots() {
    return new ArrayList<>();
  }

  /**
   * reports all the shots as damage
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return the shots that hit the ships (all of them)
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    for (Coord c : opponentShotsOnBoard) {
      personalBoard.get(c.getY()).set(c.getX(), new Cell(c, CellType.HIT));
    }
    return opponentShotsOnBoard;
  }

  /**
   * does nothing on the successful hits
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
  }

  /**
   * does nothing when the game has ended
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {
  }
}
