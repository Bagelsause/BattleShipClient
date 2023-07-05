package cs3500.pa03.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.StringReader;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

/**
 * testing class to test out the game controller
 */
class GameControllerTest {
  private StringBuilder builder;
  private GameController bc;

  /**
   * testing to see if the initialize function works properly
   */
  @Test
  public void testInitialize() {
    builder = new StringBuilder();
    bc = new GameController(
        new StringReader("0 0 \n20 6\n6 20\n6 6\n0 1 1 1\n 8 8 8 8\n1 1 1 1"), builder);

    bc.initialize();
    assertEquals("""
        Hello! Welcome to the OOD BattleSalvo Game!
        Please enter a valid height and width below:
        ------------------------------------------------------
        ------------------------------------------------------
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range [6, 15], inclusive. Try again!
        ------------------------------------------------------
        ------------------------------------------------------
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range [6, 15], inclusive. Try again!
        ------------------------------------------------------
        ------------------------------------------------------
        Uh Oh! You've entered invalid dimensions. Please remember that the height and width
        of the game must be in the range [6, 15], inclusive. Try again!
        ------------------------------------------------------
        ------------------------------------------------------
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 6.
        ------------------------------------------------------
        ------------------------------------------------------
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 6.
        ------------------------------------------------------
        ------------------------------------------------------
        Uh Oh! You've entered invalid fleet sizes.
        Please enter your fleet in the order [Carrier, Battleship, Destroyer, Submarine].
        Remember, your fleet may not exceed size 6.
        ------------------------------------------------------
        """, builder.toString());
  }

  /**
   * testing running a battlesalvo turn, but it is given invalid coordinates
   */
  @Test
  public void testInvalidCoordinates() {
    builder = new StringBuilder();
    StringReader sr = new StringReader("""
      6 6
      1 1 1 1
      -1 1
      1 -1
      -1 -1
      10 10
      6 10
      10 6
      -1 10
      10 -1
      1 1
      1 1
      1 1
      1 1
        """);
    bc = new GameController(sr, builder);
    bc.initialize();
    try {
      bc.runTurns();
    } catch (NoSuchElementException e) {
      //this happens because the game isn't completed, and we terminated our inputs for brevity
      //since we want this to happen, this is the only way to handle it.
      //NOTE: this should never happen in practice, as the user wouldn't be able to terminate
      //their input sources even if they wanted to
    }

    //since we know that after every input it's validity is checked, we should have 10
    //occurances of the system asking for valid points
    assertEquals(10,
        builder.toString().split("Please Enter 4 Shots:",  - 1).length - 1);
  }

  /**
   * testing if the runTurns function works properly and ending the game works properly
   */
  @Test
  public void testRunTurnsAndEnd() {
    builder = new StringBuilder();
    String repeatInputs = """
        1 1
        1 1
        1 1
        1 1
        """;
    StringBuilder sb = new StringBuilder();
    sb.append("""
        6 6
        1 1 1 1
        """);
    sb.append(repeatInputs.repeat(14));
    StringReader sr = new StringReader(sb.toString());
    bc = new GameController(sr, builder);
    bc.initialize();
    bc.runTurns();

    //since the game should have ended, we check if we got the proper message
    assertTrue(builder.toString().contains("Better luck next time, you lose :("));
  }
}