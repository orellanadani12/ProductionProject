/**
 * Enum of product types.
 *
 * @author Fernando Orellana
 */
public enum ItemType {

  AUDIO("AU"),
  VISUAL("VI"),
  AUDIOMOBILE("AM"),
  VISUALMOBILE("VM");

  /**
   * The two letter item type code.
   */
  public final String code;

  /**
   * Creates an item type.
   *
   * @param code the two letter item type code
   */
  ItemType(String code) {
    this.code = code;
  }
}



