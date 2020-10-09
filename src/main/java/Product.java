public abstract class Product implements Item {

  //Fields
  public int Id;
  public ItemType Type;
  public String Manufacturer;
  public String Name;


  // Constructor
  Product( String name, String manufacturer, ItemType type){
    this.Name = name;
    this.Manufacturer = manufacturer;
    this.Type = type;
  }

  // toString (returns data)
  public String toString() {
    return "Name: " + Name + "\n" + "Manufacturer: " + Manufacturer + "\n" + "Type: " + Type.code;
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

// Setters
  public void setName(String name) {
    this.Name = name;
  }

  public void setManufacturer(String manufacturer) {
    this.Manufacturer = manufacturer;
  }

}
