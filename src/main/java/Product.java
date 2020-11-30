public class Product implements Item {

  //Fields
  public int Id;
  public final ItemType Type;
  public String Manufacturer;
  public String Name;


  // Constructor
  Product(String name, String manufacturer, ItemType type){
    this.Name = name;
    this.Manufacturer = manufacturer;
    this.Type = type;
  }

  // Overloaded Constructor
  Product(int id, String name, String manufacturer, ItemType type){
    this.Id = id;
    this.Name = name;
    this.Manufacturer = manufacturer;
    this.Type = type;
  }

  // toString (returns data)
  public String toString() {
    return "Name: " + Name + "\n" + "Manufacturer: " + Manufacturer + "\n" + "Type: " + Type;
  }

  // Completing the methods from the Interface(Item)

  //Getters
  public int getId() {
    return Id;
  }
  public String getName() {
    return Name;
  }

  public String getManufacturer() {
    return Manufacturer;
  }

  public ItemType getType() {

    return Type;
  }

// Setters
  public void setName(String name) {
    this.Name = name;
  }

  public void setManufacturer(String manufacturer) {
    this.Manufacturer = manufacturer;
  }

  public void setId(int id) {
    this.Id = id;
  }

}
