import java.sql.Timestamp;

/**
 * Record collection of all products added to the database.
 *
 * @author Fernando Orellana
 */
public class ProductionRecord {

  /**
   * The unique production number of the record.
   */
  private int productionNumber;

  /**
   * The product id of the product recorded.
   */
  private int productID;

  /**
   * The serial number of the product recorded.
   */
  private String serialNumber;

  /**
   * The date of the product recorded.
   */
  private Timestamp dateProduced;

  /**
   * Creates a record of the product recorded(produced).
   *
   * @param productID the id of the product being recorded
   */
  public ProductionRecord(int productID) {
    this.productionNumber = 0;
    this.productID = productID;
    this.serialNumber = "0";
    this.dateProduced = new Timestamp(System.currentTimeMillis());
  }

  /**
   * Creates a record of the product recorded(produced).
   *
   * @param productionNumber the unique production number
   * @param productID        the recorded product id
   * @param serialNumber     the recorded product serial number
   * @param dateProduced     the recorded product date
   */
  ProductionRecord(int productionNumber, int productID, String serialNumber,
      Timestamp dateProduced) {
    this.productionNumber = productionNumber;
    this.productID = productID;
    this.serialNumber = serialNumber;
    this.dateProduced = dateProduced;
  }

  /**
   * Creates a record of the product recorded(produced) with formatted serial number.
   *
   * @param product the product being recorded
   * @param count   the count of the same product recorded
   */
  public ProductionRecord(Product product, int count) {
    this.productID = product.getId();
    this.serialNumber =
        product.getManufacturer().substring(0, 3).toUpperCase() + product.getType().code
            + String.format("%05d", count);
    this.dateProduced = new Timestamp(System.currentTimeMillis());
  }

  // toString (returns data)
  public String toString() {

    ProdController prod = new ProdController();
    Product name = prod.getProductName(productID);

    return "Prod. Num: " + productionNumber + " Prod. Name: " + name.getName() +
        " Serial Num: " + serialNumber + " Date: " + dateProduced + "\n";
  }

  /**
   * Gets the unique production number of the product recorded.
   */
  public int getProductionNumber() {

    return productionNumber;
  }

  /**
   * Gets the id of the product recorded.
   */
  public int getProductID() {

    return productID;
  }

  /**
   * Gets the serial number of the product recorded.
   */
  public String getSerialNumber() {

    return serialNumber;
  }

  /**
   * Gets the date of the product recorded.
   */
  public Timestamp getDateProduced() {

    return dateProduced;
  }

  /**
   * Sets the unique production number of the product.
   *
   * @param productionNumber the product's production number
   */
  public void setProductionNumber(int productionNumber) {

    this.productionNumber = productionNumber;
  }

  /**
   * Sets the id of the product.
   *
   * @param productID the product's id
   */
  public void setProductID(int productID) {

    this.productID = productID;
  }

  /**
   * Sets the serial number of the product.
   *
   * @param serialNumber the product's serial number
   */
  public void setSerialNumber(String serialNumber) {

    this.serialNumber = serialNumber;
  }

  /**
   * Sets the date produced of the product.
   *
   * @param dateProduced the product's date produced
   */
  public void setDateProduced(Timestamp dateProduced) {

    this.dateProduced = dateProduced;
  }
}
