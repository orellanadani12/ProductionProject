/**
 * A representation of an audio player product with multimedia controls.
 *
 * @author Fernando Orellana
 */
public class AudioPlayer extends Product implements MultimediaControl {

  /**
   * The audio formats supported by the audio player.
   */
  private final String supportedAudioFormats;

  /**
   * The playlist formats supported by the audio player.
   */
  private final String supportedPlaylistFormats;

  /**
   * Creates a product of audio player.
   *
   * @param name                      the audio player name
   * @param manufacturer              the audio player manufacturer
   * @param supportedAudioFormats     supported audio formats
   * @param supportedPlaylistFormats  supported playlist formats
   */
  AudioPlayer(String name, String manufacturer, String supportedAudioFormats,
      String supportedPlaylistFormats) {
    super(name, manufacturer, ItemType.AUDIO);
    this.supportedAudioFormats = supportedAudioFormats;
    this.supportedPlaylistFormats = supportedPlaylistFormats;
  }

  /**
   * Information data of supported audio and supported playlist formats.
   *
   * @return data as a String
   */
  public String toString() {
    return super.toString() + String.format("Supported Audio Formats: \nSupported Playlist Formats: ",
        getSupportedAudioFormats(), getSupportedPlaylistFormats());
  }

  /**
   * Implements the multimedia control play method.
   */
  public void play() {
    System.out.println("Playing");
  }

  /**
   * Implements the multimedia control stop method.
   */
  public void stop() {
    System.out.println("Stopping");
  }

  /**
   * Implements the multimedia control previous method.
   */
  public void previous() {
    System.out.println("Previous");
  }

  /**
   * Implements the multimedia control next method.
   */
  public void next() {
    System.out.println("Next");
  }

  /**
   * Gets the supported audio formats.
   *
   * @return the supported audio formats
   */
  public String getSupportedAudioFormats() {

    return supportedAudioFormats;
  }

  /**
   * Gets the supported playlist formats.
   *
   * @return the supported playlist formats
   */
  public String getSupportedPlaylistFormats() {

    return supportedPlaylistFormats;
  }
}