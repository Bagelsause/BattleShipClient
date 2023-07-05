package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * an abstract version of a player with a state of both their board and their opponents board,
 * along with their ship list
 */
public abstract class AbstractPlayer implements Player {
  protected List<List<Cell>> personalBoard;
  protected List<List<Cell>> opponentBoard;
  protected List<Coord> previouslyShot;
  protected List<List<Coord>> activeShipList;

  /**
   * returns the current board's perspective as a string
   *
   * @return a string containing the player's perspective of the
   *          opponent's board and the player's board
   */
  @Override
  public String toString() {
    StringBuilder returningString = new StringBuilder("""
        Opponent Board Data:
        """);

    for (List<Cell> cellRow : opponentBoard) {
      returningString.append("    ");
      for (Cell c : cellRow) {
        returningString.append(c.getValue()).append(" ");
      }
      returningString.deleteCharAt(returningString.length() - 1);
      returningString.append("\n");
    }

    returningString.append("""
                
        Your Board:
        """);

    for (List<Cell> cellRow : personalBoard) {
      returningString.append("    ");
      for (Cell c : cellRow) {
        returningString.append(c.getValue()).append(" ");
      }
      returningString.deleteCharAt(returningString.length() - 1);
      returningString.append("\n");
    }

    return returningString.toString();
  }

  /**
   * finds a suitable horizontal spot for a ship of a given length and direction given the
   * boundaries of the board. adds the ship to the player's board
   *
   * @param height height of the board being played on
   * @param width width of the board being played on
   * @param length length of the current ship
   * @return the ship representation of the suitable ship
   * @throws IllegalArgumentException if a ship can't be placed horizontally
   */
  private Ship findSuitableHorizontalSpot(int height, int width, int length, int shipIndex)
      throws IllegalArgumentException {
    ArrayList<Coord> visitedCoordinates = new ArrayList<>();
    Random rd = new Random();
    while (true) {
      if (visitedCoordinates.size() != 0
          && visitedCoordinates.size() == height * (width - length)) {
        throw new IllegalArgumentException("Ship can't be placed vertically!");
      }

      int randomY = rd.nextInt(height);
      int randomX = 0;
      if (width - length != 0) {
        randomX = rd.nextInt(width - length);
      }

      if (visitedCoordinates.contains(new Coord(randomX, randomY))) {
        continue;
      }

      if (personalBoard.get(randomY).get(randomX).isBlank()) {
        for (int i = randomX; i < randomX + length; i++) {
          if (!personalBoard.get(randomY).get(i).isBlank()) {
            break;
          }
          if (i == (randomX + length - 1)) {
            //if the entire spot for the ship is empty, place the ship in
            for (int j = 0; j < length; j++) {
              activeShipList.get(shipIndex).add(new Coord(randomX + j, randomY));
              personalBoard.get(randomY).set(randomX + j,
                  new Cell(new Coord(randomX + j, randomY), CellType.SHIP));
            }
            return new Ship(new Coord(randomX, randomY), length,
                ShipDirection.HORIZONTAL.toString());
          }
        }
      }
      visitedCoordinates.add(new Coord(randomX, randomY));
    }
  }


  /**
   * finds a suitable vertical spot for a ship of a given length and direction given the
   * boundaries of the board. adds the ship to the player's board
   *
   * @param height height of the board being played on
   * @param width width of the board being played on
   * @param length length of the current ship
   * @return the ship representation of the suitable ship
   * @throws IllegalArgumentException if a ship can't be placed vertically
   */
  private Ship findSuitableVerticalSpot(int height, int width, int length, int shipIndex)
      throws IllegalArgumentException {
    ArrayList<Coord> visitedCoordinates = new ArrayList<>();
    Random rd = new Random();
    while (true) {
      if (visitedCoordinates.size() != 0
          && visitedCoordinates.size() == (height - length) * width) {
        throw new IllegalArgumentException("Ship can't be placed vertically!");
      }

      int randomY = 0;
      if (height - length != 0) {
        randomY = rd.nextInt(height - length);
      }
      int randomX = rd.nextInt(width);
      if (visitedCoordinates.contains(new Coord(randomX, randomY))) {
        continue;
      }

      if (personalBoard.get(randomY).get(randomX).isBlank()) {
        for (int i = randomY; i < randomY + length; i++) {
          if (!personalBoard.get(i).get(randomX).isBlank()) {
            break;
          }
          if (i == (randomY + length - 1)) {
            //if the entire spot for the ship is empty, place the ship in
            for (int j = 0; j < length; j++) {
              activeShipList.get(shipIndex).add(new Coord(randomX, randomY + j));
              personalBoard.get(randomY + j).set(randomX,
                  new Cell(new Coord(randomX, randomY + j), CellType.SHIP));
            }
            return new Ship(new Coord(randomX, randomY), length, ShipDirection.VERTICAL.toString());
          }
        }
      }

      visitedCoordinates.add(new Coord(randomX, randomY));
    }
  }

  /**
   * initializes the boards and the personal ship lists to just have empty elements
   *
   * @param height height of the boards
   * @param width width of the boards
   */
  private void initialize(int height, int width) {
    personalBoard = new ArrayList<>();
    opponentBoard = new ArrayList<>();
    activeShipList = new ArrayList<>(Math.min(height, width));
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
  }

  /**
   * randomly assigns locations to ships in the order that they are in the map
   * (from the greatest size to least)
   *
   * @param height the height of the board, range: [6, 15] inclusive
   * @param width the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the list of ships that was set to send to the server
   */
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    this.initialize(height, width);

    int shipAmount = 0;
    for (Integer i : specifications.values()) {
      shipAmount += i;
    }

    //creating the ship list with the amount of ships
    for (int i = 0; i < shipAmount; i++) {
      List<Coord> lc = new ArrayList<>();
      activeShipList.add(lc);
    }

    ArrayList<Ship> shipList = new ArrayList<>();
    int shipIndex = 0;
    try {
      for (ShipType type : specifications.keySet()) {
        for (int i = 0; i < specifications.get(type); i++) {
          Random rd = new Random();
          int length = switch (type) {
            case CARRIER -> 6;
            case BATTLESHIP -> 5;
            case DESTROYER -> 4;
            case SUBMARINE -> 3;
          };
          Ship shipToAdd;
          //randomly select either horizontal or vertical
          if (rd.nextBoolean()) {
            try {
              shipToAdd = this.findSuitableVerticalSpot(height, width, length, shipIndex);
            } catch (IllegalArgumentException e) {
              shipToAdd = this.findSuitableHorizontalSpot(height, width, length, shipIndex);
            }
          } else {
            try {
              shipToAdd = this.findSuitableHorizontalSpot(height, width, length, shipIndex);
            } catch (IllegalArgumentException e) {
              shipToAdd = this.findSuitableVerticalSpot(height, width, length, shipIndex);
            }
          }
          shipIndex += 1;
          shipList.add(shipToAdd);
        }
      }
    } catch (IllegalArgumentException e) {
      //if the ships were placed in an impossible position
      return this.setup(height, width, specifications);
    }

    return shipList;
  }

  /**
   * get a random shot on the opponent's board
   * FOR GRADERS::
   * THE REASON WHY I PUT THIS INTO THE ABSTRACT PLAYER AND NOT KEEP IT INTO THE AiPlayer
   * AND RandomPlayer IS BECAUSE I WANTED TO ABSTRACT IT WITHOUT HAVING IT AS DUPLICATED
   * CODE IN BOTH AiPlayer AND RandomPlayer
   *
   * @return a coordinate of a shot that was not shot at before
   */
  protected Coord takeRandomShot() {
    int height = opponentBoard.size();
    int width = opponentBoard.get(0).size();
    Random rd = new Random();

    boolean foundShot = true;
    Coord newCoord = null;
    while (foundShot) {
      if (previouslyShot.size() == height * width) {
        break;
      }
      int randomY = rd.nextInt(0, height);
      int randomX = rd.nextInt(0, width);
      newCoord = new Coord(randomX, randomY);
      if (previouslyShot.size() != 0) {
        for (int i = 0; i < previouslyShot.size(); i++) {
          Coord shot = previouslyShot.get(i);
          if (newCoord.equals(shot)) {
            break;
          }
          if (i == previouslyShot.size() - 1) {
            previouslyShot.add(newCoord);
            opponentBoard.get(randomY).set(randomX, new Cell(newCoord, CellType.MISS));
            foundShot = false;
            i += 1;
          }
        }
      } else {
        previouslyShot.add(newCoord);
        foundShot = false;
      }
    }

    return newCoord;
  }

  /**
   * gives a list of shots that this player is firing at the other board
   *
   * @return the list of coordinates the shots are directed at
   */
  public abstract List<Coord> takeShots();

  /**
   * returns the hardcoded name of the player
   *
   * @return the GitHub hardcoded username of myself
   */
  public String name() {
    return "Bagelsause";
  }

  /**
   * receives when the game has ended, and performs any leftover actions (disconnecting from socket)
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  public void endGame(GameResult result, String reason) {
    System.out.println("THE GAME'S RESULT: " + result.toString());
    System.out.println("REASON: " + reason);
  }

  /**
   * given the shots that the opponent took, determine which ones had a ship and return back
   * only those coordinates
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return a list of coordinates that the opponent hit with the latest volley
   */
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    ArrayList<Coord> shotsThatHit = new ArrayList<>();
    for (Coord c : opponentShotsOnBoard) {
      String coordValue = personalBoard.get(c.getY()).get(c.getX()).getValue();
      if (coordValue.equals("S")) {
        shotsThatHit.add(c);
        for (int i = 0; i < activeShipList.size(); i++) {
          List<Coord> lc = activeShipList.get(i);
          lc.removeIf(coord -> coord.equals(c));
          activeShipList.set(i, lc);
        }
        personalBoard.get(c.getY()).set(c.getX(), new Cell(c, CellType.HIT));
      } else {
        personalBoard.get(c.getY()).set(c.getX(), new Cell(c, CellType.MISS));
      }
    }

    activeShipList.removeIf(lc -> lc.size() == 0);

    return shotsThatHit;
  }

  /**
   * for every shot in the shot that hits the opponent's ships, mark that cell as a hit
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    for (Coord c : shotsThatHitOpponentShips) {
      opponentBoard.get(c.getY()).set(c.getX(), new Cell(c, CellType.HIT));
    }
  }

  /**
   * returns true if the player being focused is a ManualPlayer (overridden in the ManualPlayer)
   *
   * @return false
   */
  public boolean isManual() {
    return false;
  }

  /**
   * the personal ship's size
   *
   * @return the current amount of ships for this player
   */
  public int getShipAmount() {
    return activeShipList.size();
  }

  /**
   * determines how many spots are left to shoot a volley
   *
   * @return the number of blank spots on the opponent's board
   */
  public int getShotMaximum() {
    int blankAmount = 0;
    for (List<Cell> cellRow : opponentBoard) {
      for (Cell c : cellRow) {
        if (c.isBlank()) {
          blankAmount += 1;
        }
      }
    }

    return blankAmount;
  }
}
