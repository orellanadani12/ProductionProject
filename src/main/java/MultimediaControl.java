/**
 * Interface for the controls of a multimedia device.
 *
 * @author Fernando Orellana
 */
public interface MultimediaControl {

  /**
   * Pretends pressing the play button.
   */
  void play();

  /**
   * Pretends pressing the stop button.
   */
  void stop();

  /**
   * Pretends pressing the previous button.
   */
  void previous();

  /**
   * Pretends pressing the next button.
   */
  void next();
}
