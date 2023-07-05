package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Ship;
import java.util.List;

/**
 * a JSON representation of a fleet of ships on a players board
 *
 * @param ships the ships on a player's board
 */
public record FleetJson(
    @JsonProperty("fleet") List<Ship> ships) {
}
