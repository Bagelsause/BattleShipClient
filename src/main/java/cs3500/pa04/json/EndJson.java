package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.GameResult;

/**
 * a JSON representation of an end message as sent from the server
 *
 * @param result an enumeration of the possible game results, WIN, LOSE, DRAW
 * @param reason a reasonable description of why this result was achieved
 */
public record EndJson(
    @JsonProperty("result") GameResult result, @JsonProperty("reason") String reason) {
}
