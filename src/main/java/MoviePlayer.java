/**
 * Representation of a movie player with multimedia controls.
 *
 * @author Fernando Orellana
 */
public class MoviePlayer extends Product implements MultimediaControl {

  /**
   * The screen used by the movie player.
   */
  public final Screen screen;

  /**
   * The monitor type used by the movie player.
   */
  public final MonitorType monitorType;

  /**
   * Creates a movie player product.
   *
   * @param name          the display name
   * @param manufacturer  the manufacturer name
   * @param screen        the screen
   * @param monitorType   the monitor type
   */
  MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType) {
    super(name, manufacturer, ItemType.VISUAL);
    this.screen = screen;
    this.monitorType = monitorType;
  }

  /**
   * Information data on name, manufacturer, type, screen, and monitor type.
   *
   * @return the data in a String
   */
  public String toString() {
    return super.toString() + String.format("Screen: \nMonitor Type: ",
        getScreen(), getMonitorType());
  }

  /**
   * Implements the multimedia control play method.
   */
  public void play() {

    System.out.println("Playing movie");
  }

  /**
   * Implements the multimedia control stop method.
   */
  public void stop() {

    System.out.println("Stopping movie");
  }

  /**
   * Implements the multimedia control previous method.
   */
  public void previous() {

    System.out.println("Previous movie");
  }

  /**
   * Implements the multimedia control next method.
   */
  public void next() {

    System.out.println("Next movie");
  }

  /**
   * Gets this movie player's screen.
   *
   * @return the screen
   */
  public Screen getScreen() {

    return screen;
  }

  /**
   * Gets the monitor type of this movie player.
   *
   * @return the monitor type
   */
  public MonitorType getMonitorType() {

    return monitorType;
  }
}

