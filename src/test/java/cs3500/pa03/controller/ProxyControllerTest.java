package cs3500.pa03.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.DudPlayer;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.RandomPlayer;
import cs3500.pa03.model.ShipType;
import cs3500.pa04.json.EndJson;
import cs3500.pa04.json.FleetJson;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupJson;
import cs3500.pa04.json.VolleyJson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * testing class responsible for ensuring that the ProxyController communicates properly
 * to the output socket
 */
class ProxyControllerTest {
  private ByteArrayOutputStream testLog;
  private ProxyController controller;
  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");

  /**
   * sets up the mocket for use with the testLog
   */
  @BeforeEach
  public void instantiateLogs() {
    testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
  }

  /**
   * check that when sending a join message, the server receives a proper join back from the player
   */
  @Test
  public void testJoinMessages() {
    JsonNode serverMessage = JsonUtils.serializeRecord(
        new MessageJson("join", VOID_RESPONSE));

    Mocket socket = new Mocket(testLog, List.of(serverMessage.toString()));

    try {
      controller = new ProxyController(socket, new RandomPlayer());
    } catch (IOException e) {
      fail("ProxyController couldn't be instantiated");
    }

    controller.run();

    //checks if we received an actual message
    responseToClass(MessageJson.class);

    //checks if the message was indeed a valid fleet json callback
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(logToString());
      MessageJson message = jsonParser.readValueAs(MessageJson.class);
      new ObjectMapper().convertValue(message.arguments(), JoinJson.class);
    } catch (IOException e) {
      fail("returned message wasn't a valid FleetJSON!");
    }
  }

  /**
   * checks that when receiving a setup message, it returns back a valid message with FleetJSON
   */
  @Test
  public void testSetupMessages() {
    LinkedHashMap<ShipType, Integer> map = new LinkedHashMap<>();
    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 2);
    map.put(ShipType.DESTROYER, 2);
    map.put(ShipType.SUBMARINE, 1);

    SetupJson setup = new SetupJson(6, 6, map);
    JsonNode node = JsonUtils.serializeRecord(setup);

    JsonNode serverMessage = JsonUtils.serializeRecord(new MessageJson("setup", node));

    Mocket socket = new Mocket(testLog, List.of(serverMessage.toString()));

    try {
      controller = new ProxyController(socket, new RandomPlayer());
    } catch (IOException e) {
      fail("ProxyController couldn't be instantiated");
    }

    controller.run();

    //checks if we received an actual message
    responseToClass(MessageJson.class);

    //checks if the message was indeed a valid fleet json callback
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(logToString());
      MessageJson message = jsonParser.readValueAs(MessageJson.class);
      new ObjectMapper().convertValue(message.arguments(), FleetJson.class);
    } catch (IOException e) {
      fail("returned message wasn't a valid FleetJSON!");
    }
  }

  /**
   * checks that when receiving a takeshots call, it returns a valid shot volley
   */
  @Test
  public void testTakeShots() {
    LinkedHashMap<ShipType, Integer> map = new LinkedHashMap<>();
    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 2);
    map.put(ShipType.DESTROYER, 2);
    map.put(ShipType.SUBMARINE, 1);

    SetupJson setup = new SetupJson(6, 6, map);
    JsonNode node = JsonUtils.serializeRecord(setup);

    JsonNode serverMessage = JsonUtils.serializeRecord(new MessageJson("setup", node));

    List<String> serverMessages = new ArrayList<>();
    serverMessages.add(serverMessage.toString());

    JsonNode secondMessage = JsonUtils.serializeRecord(
        new MessageJson("take-shots", VOID_RESPONSE));
    serverMessages.add(secondMessage.toString());

    Mocket socket = new Mocket(testLog, serverMessages);

    try {
      controller = new ProxyController(socket, new RandomPlayer());
    } catch (IOException e) {
      fail("ProxyController couldn't be instantiated");
    }

    controller.run();

    String secondaryResponse = logToString().split("\n")[1];

    try {
      JsonParser jsonParser = new ObjectMapper().createParser(secondaryResponse);
      MessageJson message = jsonParser.readValueAs(MessageJson.class);
      new ObjectMapper().convertValue(message.arguments(), VolleyJson.class);
    } catch (IOException e) {
      fail("returned message wasn't a valid VolleyJSON!");
    }
  }

  /**
   * checks that when receiving a report-damage shot, there is a valid VolleyJSON returned
   */
  @Test
  public void testReportDamage() {
    LinkedHashMap<ShipType, Integer> map = new LinkedHashMap<>();
    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 2);
    map.put(ShipType.DESTROYER, 2);
    map.put(ShipType.SUBMARINE, 1);

    SetupJson setup = new SetupJson(6, 6, map);
    JsonNode node = JsonUtils.serializeRecord(setup);

    JsonNode serverMessage = JsonUtils.serializeRecord(new MessageJson("setup", node));

    List<String> serverMessages = new ArrayList<>();
    serverMessages.add(serverMessage.toString());

    List<Coord> shots = new ArrayList<>();
    shots.add(new Coord(0, 0));
    shots.add(new Coord(1, 2));

    VolleyJson shotsHit = new VolleyJson(shots);

    JsonNode secondMessage = JsonUtils.serializeRecord(
        new MessageJson("report-damage", JsonUtils.serializeRecord(shotsHit)));
    serverMessages.add(secondMessage.toString());

    Mocket socket = new Mocket(testLog, serverMessages);

    try {
      controller = new ProxyController(socket, new DudPlayer());
    } catch (IOException e) {
      fail("ProxyController couldn't be instantiated");
    }

    controller.run();

    String secondaryResponse = logToString().split("\n")[1];

    try {
      JsonParser jsonParser = new ObjectMapper().createParser(secondaryResponse);
      MessageJson message = jsonParser.readValueAs(MessageJson.class);
      new ObjectMapper().convertValue(message.arguments(), VolleyJson.class);
    } catch (IOException e) {
      fail("returned message wasn't a valid VolleyJSON!");
    }
  }

  /**
   * checks that when receiving a successful hits, there is only a void response given back
   */
  @Test
  public void testSuccessfulHits() {
    LinkedHashMap<ShipType, Integer> map = new LinkedHashMap<>();
    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 2);
    map.put(ShipType.DESTROYER, 2);
    map.put(ShipType.SUBMARINE, 1);

    SetupJson setup = new SetupJson(6, 6, map);
    JsonNode node = JsonUtils.serializeRecord(setup);

    JsonNode serverMessage = JsonUtils.serializeRecord(new MessageJson("setup", node));

    List<String> serverMessages = new ArrayList<>();
    serverMessages.add(serverMessage.toString());

    List<Coord> shots = new ArrayList<>();
    shots.add(new Coord(0, 0));
    shots.add(new Coord(1, 2));

    VolleyJson shotsFired = new VolleyJson(shots);

    JsonNode secondMessage = JsonUtils.serializeRecord(
        new MessageJson("successful-hits", JsonUtils.serializeRecord(shotsFired)));
    serverMessages.add(secondMessage.toString());

    Mocket socket = new Mocket(testLog, serverMessages);

    try {
      controller = new ProxyController(socket, new DudPlayer());
    } catch (IOException e) {
      fail("ProxyController couldn't be instantiated");
    }

    controller.run();

    String secondaryResponse = logToString().split("\n")[1];

    try {
      JsonParser jsonParser = new ObjectMapper().createParser(secondaryResponse);
      MessageJson message = jsonParser.readValueAs(MessageJson.class);

      assertEquals("successful-hits", message.methodName());
      assertEquals(VOID_RESPONSE, message.arguments());
    } catch (IOException e) {
      fail("returned message wasn't a valid VolleyJSON!");
    }
  }

  /**
   * checks that when receiving an endgame call, the program returns a void response back
   */
  @Test
  public void testEndGame() {
    LinkedHashMap<ShipType, Integer> map = new LinkedHashMap<>();
    map.put(ShipType.CARRIER, 1);
    map.put(ShipType.BATTLESHIP, 2);
    map.put(ShipType.DESTROYER, 2);
    map.put(ShipType.SUBMARINE, 1);

    SetupJson setup = new SetupJson(6, 6, map);
    JsonNode node = JsonUtils.serializeRecord(setup);

    JsonNode serverMessage = JsonUtils.serializeRecord(new MessageJson("setup", node));

    List<String> serverMessages = new ArrayList<>();
    serverMessages.add(serverMessage.toString());

    EndJson ending = new EndJson(GameResult.WIN, "YOU WIN!!!");

    JsonNode secondMessage = JsonUtils.serializeRecord(
        new MessageJson("end-game", JsonUtils.serializeRecord(ending)));
    serverMessages.add(secondMessage.toString());

    Mocket socket = new Mocket(testLog, serverMessages);

    try {
      controller = new ProxyController(socket, new DudPlayer());
    } catch (IOException e) {
      fail("ProxyController couldn't be instantiated");
    }

    controller.run();

    String secondaryResponse = logToString().split("\n")[1];

    try {
      JsonParser jsonParser = new ObjectMapper().createParser(secondaryResponse);
      MessageJson message = jsonParser.readValueAs(MessageJson.class);

      assertEquals("end-game", message.methodName());
      assertEquals(VOID_RESPONSE, message.arguments());
    } catch (IOException e) {
      fail("returned message wasn't a valid VolleyJSON!");
    }
  }

  /**
   * translates the log from the server into a readable string
   *
   * @return the readable string of the log from the server
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  /**
   * Try converting the current test log to a string of a certain class.
   *
   * @param classRef Type to try converting the current test stream to.
   * @param <T> Type to try converting the current test stream to.
   */
  private <T> void responseToClass(@SuppressWarnings("SameParameterValue") Class<T> classRef) {
    try {
      JsonParser jsonParser = new ObjectMapper().createParser(logToString());
      jsonParser.readValueAs(classRef);
    } catch (IOException e) {
      fail();
    }
  }
}