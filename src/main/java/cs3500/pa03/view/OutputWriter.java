package cs3500.pa03.view;

import java.io.IOException;
import java.util.Objects;

/**
 * a writer that appends to a specific output source, edited from Lab 04
 */
public class OutputWriter {
  private final Appendable appendable;

  public OutputWriter(Appendable appendable) {
    this.appendable = Objects.requireNonNull(appendable);
  }

  /**
   * writes a specific string to the given appendable
   *
   * @param phrase the phrase to write to the appendable
   */
  public void write(String phrase) {
    try {
      appendable.append(phrase); // this may fail, hence the try-catch
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }
}