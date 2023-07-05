package cs3500.pa04.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * a JSON utility class derived from the Lab 06 JSON portion
 */
public class JsonUtils {
  /**
   * transforms a record into a JsonNode that can be appended into a MessageJSON
   *
   * @param r the given record
   * @return the transformed record as a JsonNode
   * @throws IllegalArgumentException if the record can't be serialized
   */
  public static JsonNode serializeRecord(Record r) throws IllegalArgumentException {
    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.convertValue(r, JsonNode.class);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Record can't be serialized!");
    }
  }
}
