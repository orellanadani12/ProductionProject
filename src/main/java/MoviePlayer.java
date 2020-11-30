public class MoviePlayer extends Product implements MultimediaControl {

    //Fields
    public final Screen Screen;
    public final MonitorType MonitorType;

    // Constructor
    MoviePlayer( String name, String manufacturer, Screen screen, MonitorType monitorType){
      super(name, manufacturer, ItemType.VISUAL);
      this.Name = name;
      this.Manufacturer = manufacturer;
      this.Screen = screen;
      this.MonitorType = monitorType;
    }

    // toString (returns data)
    public String toString() {
      return "Name: " + Name + "\n" + "Manufacturer: " + Manufacturer + "\n" + "Type: " + Type.code + "\n" +
          "Screen: " + Screen + "\n"+ "Monitor Type: " + MonitorType;
    }

    //Implementing Methods of MultimediaControl Interface
    public void play(){

      System.out.println("Playing movie");
    }

    public void stop(){

      System.out.println("Stopping movie");
    }

    public void previous(){

      System.out.println("Previous movie");
    }

    public void next(){

      System.out.println("Next movie");
    }
  }

