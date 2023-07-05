package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.ShipType;
import java.util.LinkedHashMap;

/**
 * a JSON representation of the setup inputs for the battlesalvo game
 *
 * @param width the width of the board
 * @param height the height of the board
 * @param specifications a mapping of ShipType to the integer amount on the board
 */
public record SetupJson(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") LinkedHashMap<ShipType, Integer> specifications) {
}
