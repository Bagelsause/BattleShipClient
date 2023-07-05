package cs3500.pa03.model;

import java.util.List;
import java.util.Map;

/**
 * represents a single player in a game of battlesalvo
 */
public interface Player {

  /**
   * gets the players name
   *
   * @return the player's name
   */
  String name();

  /**
   * given the specifications for a BattleSalvo board, return a list of ships with their locations
   * on the board.
   *
   * @param height the height of the board, range: [6, 15] inclusive
   * @param width the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurances each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications);

  /**
   * returns this player's shots on the opponent's board. the number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  List<Coord> takeShots();

  /**
   * given the list of shots the opponent has fired on this player's board, report which
   * shots hit a ship on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a filtered list of the given shots that contain all locations of shots that hit a
   *          ship on this board
   */
  List<Coord> reportDamage(List<Coord> opponentShotsOnBoard);

  /**
   * reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  void successfulHits(List<Coord> shotsThatHitOpponentShips);

  /**
   * notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  void endGame(GameResult result, String reason);
}
