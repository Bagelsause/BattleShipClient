package cs3500.pa03.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.GameResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * testing class of the board viewer
 */
class BoardViewerTest {
  private BoardViewer bv;
  private StringBuilder sb;

  /**
   * initializes the stringbuilder (the output) and the board viewer (the connector to the output)
   */
  @BeforeEach
  public void initializeViewer() {
    sb = new StringBuilder();
    bv = new BoardViewer(new OutputWriter(sb));
  }

  /**
   * testing if the introduction function works properly
   */
  @Test
  public void testIntroduction() {
    bv.introduction();
    assertEquals("""
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:
        ------------------------------------------------------
        """, sb.toString());
  }

  /**
   * testing if the retrying of the introduction works properly
   */
  @Test
  public void testRetryIntroduction() {
    bv.retryIntroduction();
    assertEquals("""
        ------------------------------------------------------
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range [6, 15], inclusive. Try again!
        ------------------------------------------------------
        """, sb.toString());
  }

  /**
   * testing if the visualization of the ship startup works properly
   */
  @Test
  public void testShipStartup() {
    bv.shipStartup(6);
    assertEquals("""
        ------------------------------------------------------
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 6.
        ------------------------------------------------------
        """, sb.toString());
  }

  /**
   * testing the output if the user inputted an invalid fleet size
   */
  @Test
  public void testReportInvalidShip() {
    bv.reportInvalidShips(6);
    assertEquals("""
        ------------------------------------------------------
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 6.
        ------------------------------------------------------
        """, sb.toString());
  }

  /**
   * testing the displaying of board function with a simple board
   */
  @Test
  public void testShowingBoard() {
    bv.showBoard("0 0 0 0\n1 1 1 1\n0 0 0 0\n", 5);
    assertEquals("""
        ------------------------------------------------------
        0 0 0 0
        1 1 1 1
        0 0 0 0
        
        Please Enter 5 Shots:
        ------------------------------------------------------
        """, sb.toString());
  }

  /**
   * testing the various endings and if the switch case works
   */
  @Test
  public void testEndings() {
    bv.endGameMessage(GameResult.WIN);
    assertEquals("""
        Congratulations, you win!!
        """, sb.toString());

    bv.endGameMessage(GameResult.DRAW);
    assertEquals("""
        Congratulations, you win!!
        That was a close one, it's a draw!!
        """, sb.toString());

    bv.endGameMessage(GameResult.LOSE);
    assertEquals("""
        Congratulations, you win!!
        That was a close one, it's a draw!!
        Better luck next time, you lose :(
        """, sb.toString());
  }
}