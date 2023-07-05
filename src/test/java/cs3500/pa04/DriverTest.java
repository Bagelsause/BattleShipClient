package cs3500.pa04;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * a testing class of the driver for coverage
 */
class DriverTest {

  /**
   * testing if errors are thrown if the commandline args are missing or invalid host/ports
   */
  @Test
  public void testInvalidResponses() {
    String[] args = new String[]{"invalid"};
    assertThrows(IllegalArgumentException.class,
        () -> Driver.main(args),
        "Please input either no command-line arguments or a host and port!");

    String[] secondaryArgs = new String[]{"failed", "host"};
    assertThrows(IllegalArgumentException.class,
        () -> Driver.main(secondaryArgs),
        "Please give a valid IP HOST and PORT value!");
  }
}