package cs3500.pa04.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

/**
 * testing class for the JsonUtils serialization and deserialization
 */
class JsonUtilsTest {

  /**
   * testing if serializing and deserializing results in the same information
   */
  @Test
  public void testSerializing() {
    JoinJson join = new JoinJson("Bagelsause", "SINGLE");
    JsonNode jsonResponse = JsonUtils.serializeRecord(join);

    JoinJson joinResponse = new ObjectMapper().convertValue(jsonResponse, JoinJson.class);

    assertEquals(join.name(), joinResponse.name());
    assertEquals(join.gameType(), joinResponse.gameType());
  }
}