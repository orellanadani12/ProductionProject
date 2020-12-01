/**
 * Interface for accessing a screen's specifications.
 *
 * @author Fernando Orellana
 */
public interface ScreenSpec {

  /**
   * Gets the screen's resolution.
   *
   * @return the resolution
   */
  String getResolution();

  /**
   * Gets the screen's refresh rate.
   *
   * @return the refresh rate
   */
  int getRefreshRate();

  /**
   * Gets the screen's response time.
   *
   * @return the response time
   */
  int getResponseTime();
}
