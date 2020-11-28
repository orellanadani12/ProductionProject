
import java.sql.Timestamp;


public class ProductionRecord {

  //Fields
  public int ProductionNumber;
  public int ProductID;
  public String SerialNumber;
  public Timestamp DateProduced;

  // Constructor
  public ProductionRecord(int productID) {
    this.ProductionNumber = 0;
    this.ProductID = productID;
    this.SerialNumber = "0";
    this.DateProduced = new Timestamp(System.currentTimeMillis());
  }

  // Overloaded Constructor
  ProductionRecord(int productionNumber, int productId, String serialNumber, Timestamp dateProduced) {
    this.ProductionNumber = productionNumber;
    this.ProductID = productId;
    this.SerialNumber = serialNumber;
    this.DateProduced = dateProduced;
  }

  // Overloaded Constructor--Serial Number
  public ProductionRecord(Product product, int count) {
    this.ProductID = product.getId();
    this.SerialNumber =
        product.getManufacturer().substring(0, 3).toUpperCase() + product.getType().code
            + String.format("%05d", count);
    this.DateProduced = new Timestamp(System.currentTimeMillis());
  }

  // toString (returns data)
  public String toString() {

    ProdController prod = new ProdController();
    Product name = prod.getProductName(ProductID);

    return "Prod. Num: "+ ProductionNumber + " Prod. Name: "+ name.getName() +
      " Serial Num: "+ SerialNumber + " Date: "+ DateProduced + "\n";
}

  //Getters
  public int getProductionNumber() {

    return ProductionNumber;
  }

  public int getProductID() {

    return ProductID;
  }

  public String getSerialNumber() {

    return SerialNumber;
  }

  public Timestamp getDateProduced() {

    return DateProduced;
  }

  // Setters
  public void setProductionNumber(int productionNumber) {

    this.ProductionNumber = productionNumber;
  }

  public void setProductID(int productID) {

    this.ProductID = productID;
  }

  public void setSerialNumber(String serialNumber) {

    this.SerialNumber = serialNumber;
  }

  public void setDateProduced(Timestamp dateProduced) {

    this.DateProduced = dateProduced;
  }

}
