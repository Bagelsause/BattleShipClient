package cs3500.pa03.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.util.List;

/**
 * a mock socket where the inputs and outputs to the socket are predetermined
 */
public class Mocket extends Socket {

  private final InputStream testInputs;
  private final ByteArrayOutputStream testLog;

  /**
   * the corresponding inputs and outputs to the socket
   *
   * @param log the log to be edited
   * @param toSend the list of strings to be send back as a bytearrayoutputstream
   */
  public Mocket(ByteArrayOutputStream log, List<String> toSend) {
    testLog = log;

    StringWriter stringWriter = new StringWriter();
    PrintWriter printWriter = new PrintWriter(stringWriter);
    for (String message : toSend) {
      printWriter.println(message);
    }
    testInputs = new ByteArrayInputStream(stringWriter.toString().getBytes());
  }

  /**
   * gets the input stream that was set in the constructor
   *
   * @return the inputstream
   */
  @Override
  public InputStream getInputStream() {
    return testInputs;
  }

  /**
   * gets the output stream that was set in the constructor
   *
   * @return the outputstream
   */
  @Override
  public OutputStream getOutputStream() {
    return testLog;
  }
}
