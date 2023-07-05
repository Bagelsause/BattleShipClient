package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * a JSON of a join message to the server
 *
 * @param name the name of the current player
 * @param gameType the game type to send to the server, SINGLE or MULTI
 */
public record JoinJson(
    @JsonProperty("name") String name, @JsonProperty("game-type") String gameType) {
}
