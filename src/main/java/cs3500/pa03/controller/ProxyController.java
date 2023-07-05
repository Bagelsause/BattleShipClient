package cs3500.pa03.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.Ship;
import cs3500.pa03.model.ShipType;
import cs3500.pa04.json.EndJson;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.RequestTypes;
import cs3500.pa04.json.SetupJson;
import cs3500.pa04.json.VolleyJson;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * a controller class that represents a proxy between the current player and a virtual player
 * through a socket/server.
 */
public class ProxyController {
  private final Player player;
  private final Socket socket;
  private final InputStream input;
  private final PrintStream output;
  private final ObjectMapper mapper = new ObjectMapper();
  private final String gameMode;
  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");

  /**
   * a constructor initializing the socket's input and output streams
   *
   * @param s the socket that has already been initialized
   * @param p the player object that our client is hosting
   * @throws IOException if the input or output can't be connected to
   */
  public ProxyController(Socket s, Player p) throws IOException {
    player = p;
    socket = s;
    input = s.getInputStream();
    output = new PrintStream(s.getOutputStream());
    gameMode = "SINGLE";
  }

  /**
   * until the socket is closed, read every message from the server and perform the requested
   * action with the player object or the socket
   */
  public void run() {
    try {
      JsonParser parser = mapper.getFactory().createParser(input);

      while (!socket.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        handleMessage(message);
      }
    } catch (IOException e) {
      // Disconnected from server or parsing exception
    }
  }

  /**
   * given a generic MessageJSON, determine what action the server is trying to get the player
   * to perform
   *
   * @param message the message from the server
   */
  private void handleMessage(MessageJson message) {
    String functionName = message.methodName();
    JsonNode arguments = message.arguments();

    functionName = functionName.replace("-", "").toUpperCase();
    RequestTypes requestEnum = RequestTypes.valueOf(functionName);

    //after determining the function is one of the request types, send the request and
    //arguments to their individual handler

    switch (requestEnum) {
      case SETUP -> handleSetup(arguments);
      case TAKESHOTS -> handleShots();
      case REPORTDAMAGE -> handleDamage(arguments);
      case SUCCESSFULHITS -> handleHits(arguments);
      case ENDGAME -> handleEndgame(arguments);
      default -> handleJoin();
    }
  }

  /**
   * on call, sends the server a join message with the name "Bagelsause" and
   * the hardcoded gameMode as set in the constructor
   */
  private void handleJoin() {
    //call the name function, send that and gameMode value
    JoinJson response = new JoinJson(player.name(), gameMode);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);

    MessageJson message = new MessageJson("join", jsonResponse);
    JsonNode fullResponse = JsonUtils.serializeRecord(message);
    output.println(fullResponse);
  }

  /**
   * on call, parses the setup arguments for the game, sets up the player's game, and returns
   * back the initial list of ships
   *
   * @param arguments a JSON interpretation of the setup specifications for the game
   */
  private void handleSetup(JsonNode arguments) {
    SetupJson setupArgs = mapper.convertValue(arguments, SetupJson.class);

    int width = setupArgs.width();
    int height = setupArgs.height();
    LinkedHashMap<ShipType, Integer> specs = setupArgs.specifications();
    //call the setup function, send the ships back

    List<Ship> setup = player.setup(height, width, specs);
    FleetJson response = new FleetJson(setup);

    JsonNode jsonResponse = JsonUtils.serializeRecord(response);

    MessageJson message = new MessageJson("setup", jsonResponse);
    JsonNode fullResponse = JsonUtils.serializeRecord(message);
    output.println(fullResponse);
  }

  /**
   * on call, tell the player to take shots and return the taken shots to the server
   */
  private void handleShots() {
    List<Coord> shots = player.takeShots();

    //call the takeShots function, send the shots back

    VolleyJson response = new VolleyJson(shots);
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);

    MessageJson message = new MessageJson("take-shots", jsonResponse);
    JsonNode fullResponse = JsonUtils.serializeRecord(message);
    output.println(fullResponse);
  }

  /**
   * on call, parse the shots that the opponent took, then ask the player which shots hit,
   * return those shots to the server
   *
   * @param arguments a JSON interpretation of a volley of shots from the opponent
   */
  private void handleDamage(JsonNode arguments) {
    List<Coord> shotsFired = mapper.convertValue(arguments, VolleyJson.class).shots();

    //call the reportDamage function, send the shots that hit back
    VolleyJson response = new VolleyJson(player.reportDamage(shotsFired));
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);

    MessageJson message = new MessageJson("report-damage", jsonResponse);
    JsonNode fullResponse = JsonUtils.serializeRecord(message);
    output.println(fullResponse);
  }

  /**
   * on call, parse the shots that hit the opponents board, inform the player of those shots,
   * and return nothing to the server
   *
   * @param arguments a JSON representation of the shots that hit the opponent's ships
   */
  private void handleHits(JsonNode arguments) {
    List<Coord> shotsHit = mapper.convertValue(arguments, VolleyJson.class).shots();

    player.successfulHits(shotsHit);
    //call the successfulHits function, send back nothing

    MessageJson message = new MessageJson("successful-hits", VOID_RESPONSE);
    JsonNode fullResponse = JsonUtils.serializeRecord(message);
    output.println(fullResponse);
  }

  /**
   * on call, parse the ending result from the server and give the result and reason to the player,
   * return nothing to the server
   *
   * @param arguments a JSON representation of the ending result and reason that the game is over
   */
  private void handleEndgame(JsonNode arguments) {
    EndJson endingResult = mapper.convertValue(arguments, EndJson.class);
    GameResult result = endingResult.result();
    String reason = endingResult.reason();

    player.endGame(result, reason);

    //call the endGame function, send back nothing
    MessageJson message = new MessageJson("end-game", VOID_RESPONSE);
    JsonNode fullResponse = JsonUtils.serializeRecord(message);
    output.println(fullResponse);
    try {
      socket.close();
    } catch (IOException e) {
      System.err.println("An error occurred when closing the socket connection!");
    }
  }
}
