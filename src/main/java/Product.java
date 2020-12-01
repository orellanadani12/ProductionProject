/**
 * Representation of a product.
 *
 * @author Fernando Orellana
 */
public class Product implements Item {

  /**
   * The unique id number of the product.
   */
  private int id;

  /**
   * The the type of the product.
   */
  private final ItemType type;

  /**
   * The manufacturer of the product.
   */
  private String manufacturer;

  /**
   * The name of the product.
   */
  private String name;

  /**
   * Creates a new product.
   *
   * @param name         the product name
   * @param manufacturer the product manufacturer
   * @param type         the product type
   */
  Product(String name, String manufacturer, ItemType type) {
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
  }

  /**
   * Creates a new product with id.
   *
   * @param id           the product id
   * @param name         the product name
   * @param manufacturer the product manufacturer
   * @param type         the product type
   */
  Product(int id, String name, String manufacturer, ItemType type) {
    this.id = id;
    this.name = name;
    this.manufacturer = manufacturer;
    this.type = type;
  }

  /**
   * Information data of product's name, manufacturer, and type to be displayed.
   *
   * @return data as a String
   */
  public String toString() {
    return "Name: " + name + "\n" + "Manufacturer: " + manufacturer + "\n" + "Type: " + type;
  }

  /**
   * Gets the product's id.
   *
   * @return the id of the product
   */
  public int getId() {
    return id;
  }

  /**
   * Gets the product's name.
   *
   * @return the name of the product
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the product's manufacturer.
   *
   * @return the manufacturer of the product
   */
  public String getManufacturer() {
    return manufacturer;
  }

  /**
   * Gets the product's type.
   *
   * @return the type of the product
   */
  public ItemType getType() {

    return type;
  }

  /**
   * Sets the product's name.
   *
   * @param name the name of the product
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets the product's manufacturer.
   *
   * @param manufacturer the manufacturer of the product
   */
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  /**
   * Sets the product's id.
   *
   * @param id the id of the product
   */
  public void setId(int id) {
    this.id = id;
  }

}
