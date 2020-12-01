/**
 * An interface for an item with an id, name, and manufacturer.
 *
 * @author Fernando Orellana
 */
public interface Item {

  /**
   * Gets the id number of the item.
   *
   * @return the Id number
   */
  int getId();

  /**
   * Sets the name of the item.
   *
   * @param name the item name
   */
  void setName(String name);

  /**
   * Gets the name of the item.
   *
   * @return the name
   */
  String getName();

  /**
   * Sets the manufacturer of the item.
   *
   * @param manufacturer the item manufacturer
   */
  void setManufacturer(String manufacturer);

  /**
   * Gets the manufacturer of the item.
   *
   * @return the manufacturer
   */
  String getManufacturer();
}
