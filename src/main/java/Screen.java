public class Screen implements ScreenSpec{

  //Fields
  public String resolution;
  public int refreshRate;
  public int responseTime;

  //Constructor
  Screen(String resolution, int refreshrate, int responsetime){
    this.resolution = resolution;
    this.refreshRate = refreshrate;
    this.responseTime = responsetime;
  }

  //Complemeting Methods
  public String getResolution(){
    return resolution;
  }
  public int getRefreshRate(){
    return refreshRate;
  }
  public int getResponseTime(){
    return responseTime;
  }

  // toString (returns data)
  public String toString() {
    return "\n" + "Resolution: " + resolution + "\n" + "Refresh Rate: " + refreshRate + "\n" + "Response Time: " + responseTime;
  }

}
