package cs3500.pa03.controller;

import cs3500.pa03.model.Coord;
import java.util.ArrayList;

/**
 * represents the manual player's shot data
 */
public class PlayerShotData {
  private ArrayList<Coord> shots;


  /**
   * updates the shot list with the current shots that the user is taking
   *
   * @param newShots the list of shots that the user is taking
   */
  public void updateShots(ArrayList<Coord> newShots) {
    shots = newShots;
  }

  /**
   * gets the current shots that the user is taking
   *
   * @return the list of shots the user is taking
   */
  public ArrayList<Coord> getShots() {
    return shots;
  }
}
