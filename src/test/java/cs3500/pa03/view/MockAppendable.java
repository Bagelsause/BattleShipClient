package cs3500.pa03.view;

import java.io.IOException;

/**
 * a mock appendable purely for testing purposes of throwing errors
 */
public class MockAppendable implements Appendable {

  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException("Can't be appended to!");
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException("Can't be appended to!");
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException("Can't be appended to!");
  }
}
