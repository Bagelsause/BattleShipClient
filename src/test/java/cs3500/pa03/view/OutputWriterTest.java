package cs3500.pa03.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * testing class for the output writer
 */
class OutputWriterTest {
  /**
   * testing if writing a phrase to an output writer works properly
   */
  @Test
  public void testWriting() {
    StringBuilder sb = new StringBuilder();
    OutputWriter ow = new OutputWriter(sb);
    ow.write("Test phrase");
    assertEquals("Test phrase", sb.toString());
  }

  /**
   * testing if the output writer throws the correct exception with a mock appendable
   */
  @Test
  public void testErrorWriting() {
    MockAppendable mock = new MockAppendable();
    OutputWriter ow = new OutputWriter(mock);
    assertThrows(RuntimeException.class,
        () -> ow.write(""),
        "Can't be appended to!");
  }
}