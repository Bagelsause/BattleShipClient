package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.GameResult;
import org.junit.jupiter.api.Test;

/**
 * testing class for the EndJSON format
 */
class EndJsonTest {

  /**
   * testing to see if we can initialize and access the JSON elements
   */
  @Test
  public void testInitialization() {
    EndJson json = new EndJson(GameResult.WIN, "You won!");

    assertEquals(GameResult.WIN, json.result());
    assertEquals("You won!", json.reason());
  }
}