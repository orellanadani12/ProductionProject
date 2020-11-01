
import java.util.Date;

public class ProductionRecord {

  //Fields
  public int ProductionNumber;
  public int ProductID;
  public String SerialNumber;
  public Date DateProduced;

  // Constructor
  public ProductionRecord( int productID){
    this.ProductionNumber = 0;
    this.ProductID = productID;
    this.SerialNumber = "0";
    this.DateProduced = new Date();
  }

  // Overloaded Constructor
  ProductionRecord( int productionNumber, int productID, String serialNumber, Date dateProduced){
    this.ProductionNumber = productionNumber;
    this.ProductID = productID;
    this.SerialNumber = serialNumber;
    this.DateProduced = dateProduced;
  }

  // Overloaded Constructor--Serial Number
  public ProductionRecord(Product product, int count) {
    this.ProductionNumber = 0;
    this.ProductID = product.getId();
    this.SerialNumber = product.getManufacturer().substring(0,3).toUpperCase() + product.getType().code
                        +  String.format("%05d", count);
    this.DateProduced = new Date();
  }

  // toString (returns data)
  public String toString() {
    return "Prod. Num: " + ProductionNumber + " Product ID: " + ProductID +
        " Serial Num: " + SerialNumber + " Date: " + DateProduced + "\n";
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

  public Date getDateProduced() {

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

  public void setDateProduced(Date dateProduced) {

    this.DateProduced = dateProduced;
  }

}
