package cs3500.pa04;

import cs3500.pa03.controller.GameController;
import cs3500.pa03.controller.ProxyController;
import cs3500.pa03.model.AiPlayer;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) {
    if (args.length == 2) {
      //for the server-based playing of two users

      Socket s;

      //if creating the socket doesn't work, then it must have given an invalid HOST or PORT
      try {
        s = new Socket(args[0], Integer.parseInt(args[1]));
      } catch (IOException e) {
        throw new IllegalArgumentException("Please give a valid IP HOST and PORT value!");
      }

      //initialize ProxyController with valid socket
      ProxyController pc;
      try {
        pc = new ProxyController(s, new AiPlayer());
      } catch (IOException e) {
        throw new IllegalStateException("Input or output can't be connected to!");
      }
      //run the client-side inputs and outputs
      pc.run();

    } else if (args.length == 0) {
      //for the local playing of two users, one manual and one random

      GameController bc = new GameController(new InputStreamReader(System.in),
          new PrintStream(System.out));

      //initialize each of the boards
      bc.initialize();

      //run all the turns until it exits
      bc.runTurns();
    } else {
      throw new IllegalArgumentException(
          "Please input either no command-line arguments or a host and port!");
    }
  }
}