/**
 * Representation of a screen.
 *
 * @author Fernando Orellana
 */
public class Screen implements ScreenSpec {

  /**
   * The screen's resolution.
   */
  private final String resolution;

  /**
   * The screen's refresh rate.
   */
  private final int refreshRate;

  /**
   * The screen's response time.
   */
  private final int responseTime;

  /**
   * Creates a screen with the given specs.
   *
   * @param resolution   the screen's resolution
   * @param refreshRate  the screen's refresh rate
   * @param responseTime the screen's response time
   */
  Screen(String resolution, int refreshRate, int responseTime) {
    this.resolution = resolution;
    this.refreshRate = refreshRate;
    this.responseTime = responseTime;
  }

  /**
   * Complementing the resolution method from the ScreenSpec interface.
   *
   * @return the screen's resolution
   */
  public String getResolution() {
    return resolution;
  }

  /**
   * Complementing the refresh rate method from the ScreenSpec interface.
   *
   * @return the screen's refresh rate
   */
  public int getRefreshRate() {
    return refreshRate;
  }

  /**
   * Complementing the response time method from the ScreenSpec interface.
   *
   * @return the screen's response time
   */
  public int getResponseTime() {
    return responseTime;
  }

  /**
   * Information data of resolution, refresh rate, and response time to be used in a String.
   *
   * @return the data in a String
   */
  public String toString() {
    return "\n" + "Resolution: " + resolution + "\n" + "Refresh Rate: " + refreshRate + "\n"
        + "Response Time: " + responseTime;
  }
}
