public enum ItemType {

  Audio("AU"),
  Visual("VI"),
  AudioMobile ("AM"),
  VisualMobile ("VM");

  public final String code;

  ItemType(String code) {
    this.code = code;
  }

  private String code() {
    return code;
  }

}



