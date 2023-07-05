package cs3500.pa03.view;

import cs3500.pa03.model.GameResult;

/**
 * view controller for the BattleSalvo game
 */
public class BoardViewer {
  private final OutputWriter output;

  public BoardViewer(OutputWriter out) {
    output = out;
  }

  /**
   * writes a specific string to the output
   *
   * @param s string to write to output
   */
  private void write(String s) {
    output.write(s + "\n");
  }

  /**
   * ease-of-use function to write a separator without having to manually write it every time
   */
  private void separator() {
    this.write("------------------------------------------------------");
  }

  /**
   * outputs an introduction to the BattleSalvo game, awaits a valid height and width from user
   */
  public void introduction() {
    this.write("Hello! Welcome to the OOD BattleSalvo Game!");
    this.write("Please enter a valid height and width below:");
    this.separator();
  }

  /**
   * if the user gives an incorrect dimension, inform the user that they did so and ask again
   */
  public void retryIntroduction() {
    this.separator();
    this.write(
        "Uh Oh! You've entered invalid dimensions. Please remember that the height and width");
    this.write("of the game must be in the range [6, 15], inclusive. Try again!");
    this.separator();
  }

  /**
   * asks the user about the size of the fleet that both sides should have
   *
   * @param maxFleetSize the maximum total of ships allowed on the board
   */
  public void shipStartup(int maxFleetSize) {
    this.separator();
    this.write("Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].");
    this.write("Remember, your fleet may not exceed size " + maxFleetSize + ".");
    this.separator();
  }

  /**
   * if the user inputs an incorrect size of the fleet, inform them and ask again
   *
   * @param maxFleetSize the maximum total of ships allowed on the board
   */
  public void reportInvalidShips(int maxFleetSize) {
    this.separator();
    this.write("Uh Oh! You've entered invalid fleet sizes.");
    this.write("Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].");
    this.write("Remember, your fleet may not exceed size " + maxFleetSize + ".");
    this.separator();
  }

  /**
   * shows the current player's perspective of the board
   *
   * @param boards a string representation of the player's current board perspective
   * @param shotsAvailable the maximum amount of shots left for the user to submit
   */
  public void showBoard(String boards, int shotsAvailable) {
    this.separator();
    String[] boardLines = boards.split("\n");
    for (String line : boardLines) {
      this.write(line);
    }

    this.write("\nPlease Enter " + shotsAvailable + " Shots:");
    this.separator();
  }

  /**
   * shows if the user wins, loses, or has a draw with the opponent
   *
   * @param result the resulting game state after the game is ended
   */
  public void endGameMessage(GameResult result) {
    switch (result) {

      case WIN -> this.write("Congratulations, you win!!");
      case DRAW -> this.write("That was a close one, it's a draw!!");
      default -> this.write("Better luck next time, you lose :(");
    }

  }
}
