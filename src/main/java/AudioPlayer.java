public class AudioPlayer extends Product implements MultimediaControl {

  //Fields
  public String SupportedAudioFormats;
  public String SupportedPlaylistFormats;

  // Constructor
  AudioPlayer( String name, String manufacturer, String supportedAudioFormats, String supportedPlaylistFormats){
    super(name, manufacturer, ItemType.AUDIO);
    this.Name = name;
    this.Manufacturer = manufacturer;
    this.SupportedAudioFormats = supportedAudioFormats;
    this.SupportedPlaylistFormats = supportedPlaylistFormats;
  }

  // toString (returns data)
  public String toString() {
    return "Name: " + Name + "\n" + "Manufacturer: " + Manufacturer + "\n" + "Type: " + Type.code + "\n" +
        "Supported Audio Format: " + SupportedAudioFormats + "\n" + "Supported Playlist Format: " + SupportedPlaylistFormats;
  }

  //Implementing Methods of MultimediaControl Interface
  public void play(){

    System.out.println("Playing");
  }

  public void stop(){

    System.out.println("Stopping");
  }

  public void previous(){

    System.out.println("Previous");
  }

  public void next(){

    System.out.println("Next");
  }
}
