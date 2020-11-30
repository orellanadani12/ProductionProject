public class Screen implements ScreenSpec{

  //Fields
  public final String resolution;
  public final int refreshRate;
  public final int responseTime;

  //Constructor
  Screen(String resolution, int refreshRate, int responseTime){
    this.resolution = resolution;
    this.refreshRate = refreshRate;
    this.responseTime = responseTime;
  }

  //Complementing Methods
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
