public enum ItemType {

  AUDIO("Audio","AU"),
  VISUAL("Visual","VI"),
  AUDIOMOBILE("Audio Mobile","AM"),
  VISUALMOBILE("Visual Mobile","VM");

  public final String code;
  public final String display;

  //Constructor
  ItemType(String display, String code) {

    this.display = display;
    this.code = code;
  }

}



