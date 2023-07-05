package cs3500.pa03.model;

/**
 * represents all possible endgame results
 */
public enum GameResult {
  //if two users die at the same time
  DRAW,
  //if the current user dies before the other user
  LOSE,
  //if the other user dies before the current user
  WIN
}
