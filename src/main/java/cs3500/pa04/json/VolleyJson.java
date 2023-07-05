package cs3500.pa04.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.pa03.model.Coord;
import java.util.List;

/**
 * a JSON representation of the volley of shots taken towards another ship
 *
 * @param shots the shots fired at another ship
 */
public record VolleyJson(
    @JsonProperty("coordinates") List<Coord> shots) {
}
