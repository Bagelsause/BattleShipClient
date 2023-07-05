package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * a record representing a generic message to the server as written in JSON
 *
 * @param methodName the function called towards the user or the server
 * @param arguments the given arguments to the function
 */
public record MessageJson(
    @JsonProperty("method-name") String methodName,
    @JsonProperty("arguments") JsonNode arguments) {
}