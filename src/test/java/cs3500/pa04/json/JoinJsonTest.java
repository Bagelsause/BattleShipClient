package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * testing class for the JoinJSON format
 */
class JoinJsonTest {

  /**
   * testing to see if we can initialize and access the JSON elements
   */
  @Test
  public void testInitialization() {
    JoinJson json = new JoinJson("Bagelsause", "SINGLE");

    assertEquals("Bagelsause", json.name());
    assertEquals("SINGLE", json.gameType());
  }
}