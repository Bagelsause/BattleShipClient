package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * representing a computer-ran player that determines where to shoot and how to shoot
 */
public class AiPlayer extends AbstractPlayer {
  private Coord focusedCoord;
  private final List<Coord> workList;

  /**
   * instantiates an AiPlayer with a new shotList, nonfocused coord, and a workList
   */
  public AiPlayer() {
    previouslyShot = new ArrayList<>();
    focusedCoord = null;
    workList = new ArrayList<>();
  }

  /**
   * checks if the coordinate has been untouched by our missiles
   *
   * @param c the coordinate that we're looking for
   * @return true if we haven't shot at the coordinate, false otherwise
   */
  private boolean untouched(Coord c) {
    for (Coord coord : previouslyShot) {
      if (coord.equals(c)) {
        return false;
      }
    }
    return true;
  }

  /**
   * given a list of coordinates of interest, determine if we should shoot, then return coordinate
   *
   * @param coords array of coordinates of interest
   * @return the coordinate that we are firing at, otherwise null (we've shot all interesting cells)
   */
  private Coord fireShots(Coord[] coords) {
    for (Coord c : coords) {
      if (c.getY() < opponentBoard.size() && c.getX() < opponentBoard.get(0).size()) {
        if (this.untouched(c)) {
          previouslyShot.add(c);
          opponentBoard.get(c.getY()).set(c.getX(), new Cell(c, CellType.MISS));
          return c;
        }
      }
    }
    if (workList.size() == 0) {
      focusedCoord = null;
    } else {
      focusedCoord = workList.remove(0);
    }
    return null;
  }

  /**
   * given a non-empty workList, determine what shot would be smart to take next
   *
   * @return a coordinate that is likely to have a ship
   */
  private Coord takeFocusedShot() {
    if (focusedCoord == null) {
      try {
        focusedCoord = workList.remove(0);
      } catch (IndexOutOfBoundsException e) {
        return takeRandomShot();
      }
    }
    int x = focusedCoord.getX();
    int y = focusedCoord.getY();

    Coord left = new Coord(x - 1, y);
    Coord right = new Coord(x + 1, y);
    Coord up = new Coord(x, y - 1);
    Coord down = new Coord(x, y + 1);

    if (x == 0 && y == 0) {
      Coord fired = fireShots(new Coord[]{right, down});
      return Objects.requireNonNullElseGet(fired, this::takeFocusedShot);
    } else if (x == 0) {
      Coord fired = fireShots(new Coord[]{up, right, down});
      return Objects.requireNonNullElseGet(fired, this::takeFocusedShot);
    } else if (y == 0) {
      Coord fired = fireShots(new Coord[]{right, down, left});
      return Objects.requireNonNullElseGet(fired, this::takeFocusedShot);
    } else {
      Coord fired = fireShots(new Coord[]{up, right, down, left});
      try {
        return Objects.requireNonNullElseGet(fired, this::takeFocusedShot);
      } catch (NullPointerException e) {
        return this.takeFocusedShot();
      }
    }
  }

  /**
   * for the amount of shots needed to be taken, determine if we have to scout for a new
   * area to focus on or if we can focus on something else
   *
   * @return a list of shots that the AIPlayer is shooting at the other board
   */
  @Override
  public List<Coord> takeShots() {
    ArrayList<Coord> shotsFired = new ArrayList<>();

    int minimum = Math.min(getShotMaximum(), getShipAmount());

    for (int i = 0; i < minimum; i++) {
      if (workList.size() == 0 && focusedCoord == null) {
        shotsFired.add(takeRandomShot());
      } else {
        shotsFired.add(takeFocusedShot());
      }
    }

    return shotsFired;
  }

  /**
   * for every shot in the shot that hits the opponent's ships, mark that cell as a hit
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    for (Coord c : shotsThatHitOpponentShips) {
      opponentBoard.get(c.getY()).set(c.getX(), new Cell(c, CellType.HIT));
      workList.add(c);
    }
  }
}
