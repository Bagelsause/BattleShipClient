package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

/**
 * testing class for the MessageJSON format
 */
class MessageJsonTest {

  /**
   * testing to see if we can initialize and access the JSON elements
   */
  @Test
  public void testInitialization() {
    JoinJson join = new JoinJson("Bagelsause", "SINGLE");
    JsonNode jsonResponse = JsonUtils.serializeRecord(join);

    MessageJson json = new MessageJson("join", jsonResponse);
    assertEquals("join", json.methodName());
    assertEquals(jsonResponse, json.arguments());
  }
}