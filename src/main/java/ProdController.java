import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProdController {

  private Connection conn;
  private final String PROPERTIES =

  @FXML
  private TextField txtProductName;

  @FXML
  private TextField txtManufacturer;

  @FXML
  private ChoiceBox<String> cbItemType;

  @FXML
  private TableView<Product> existingProduct;

  @FXML
  private TableColumn<?, ?> colProdName;

  @FXML
  private TableColumn<?, ?> colManufacturer;

  @FXML
  private TableColumn<?, ?> colType;


  @FXML
  private Button productButton;

  @FXML
  private Button recordButton;

  @FXML
  private ComboBox<String> cmbQuantity;

  @FXML
  private ListView<Product> produceView;

  @FXML
  private TextArea txtAreaProductLog;

  //List of products loaded from DB

  ObservableList<Product> productLine = FXCollections.observableArrayList();


  @FXML
  void addProduct(ActionEvent event) {
    connectToDb();
    System.out.println("Product Added");

    loadProductList();
  }

  @FXML
  void recordProduction(ActionEvent event) {
    System.out.println("Product Recorded");

    txtAreaProductLog.clear();
    Product prodRecord = produceView.getSelectionModel().getSelectedItem();
    int quantity = cmbQuantity.getSelectionModel().getSelectedIndex();
    ObservableList<ProductionRecord> productionRun = FXCollections.observableArrayList();

    int sameTypeAmnt = getSameProductTypeAmnt(prodRecord.getId());

    for (int i = 0; i <= quantity; i++) {
      productionRun.add(new ProductionRecord(prodRecord, ++sameTypeAmnt));
    }
    addToProductionDB(productionRun);
    loadProductionLog();
  }

  public void initialize() {

    //options for choiceBox
    for (ItemType item : ItemType.values()) {
      cbItemType.getItems().add(String.valueOf(item));
    }
    // Defaults to first enum type
    cbItemType.getSelectionModel().selectFirst();

    for (int count = 1; count <= 10; count++) {
      cmbQuantity.getItems().add(String.valueOf(count));
    }

    // Sets number to default and makes it editable
    cmbQuantity.setEditable(true);
    cmbQuantity.getSelectionModel().selectFirst();

    setupProductLineTable();
    loadProductList();
    loadProductionLog();
  }

  public void loadProductList() {

    //Clears the Observable List so it doesn't duplicate the products
    productLine.clear();

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/production";

    //  Database credentials
    final String USER = "";
    final String PASS = reverseString(getPwFromFile());
    Connection conn = null;
    Statement stmt = null;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      String selectSql = "Select * FROM Product";
      ResultSet rs = stmt.executeQuery(selectSql);

// while loop to all data in DB
      while (rs.next()) {

        int id = rs.getInt(1);
        String name = rs.getString(2);

        // Gets ItemType from DB
        ItemType type = null;
        for (ItemType item : ItemType.values()) {
          if (String.valueOf(item).equals(rs.getString(3))) {
            type = item;
          }
        }

        String manuf = rs.getString(4);

        Product product = new Product(id, name, manuf, type);
        productLine.add(product);

        existingProduct.setItems(productLine);
        produceView.setItems(productLine);

      }

      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();

    } catch (ClassNotFoundException e) {
      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void connectToDb() {
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/production";

    //  Database credentials
    final String USER = "";
    final String PASS = reverseString(getPwFromFile());
    Connection conn = null;
    Statement stmt = null;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      String productName = txtProductName.getText();
      String manufacturer = txtManufacturer.getText();
      String itemType = cbItemType.getValue();

      final String sql = "INSERT INTO Product(item_type, manufacturer, product_name ) "
          + "VALUES ( ?, ?, ?)";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, itemType);
      ps.setString(2, manufacturer);
      ps.setString(3, productName);

      ps.executeUpdate();

      String selectSql = "Select item_type, manufacturer, product_name"
          + " FROM Product";
      ResultSet rs = stmt.executeQuery(selectSql);

// while loop to all data in DB
      while (rs.next()) {
        System.out.println(rs.getString(1));
        System.out.println(rs.getString(2));
        System.out.println(rs.getString(3));
      }
      ;
      // STEP 4: Clean-up environment
      stmt.close();
      conn.close();

    } catch (ClassNotFoundException e) {
      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void setupProductLineTable() {

    // adds info to ObservableList
    colProdName.setCellValueFactory(new PropertyValueFactory<>("Name"));
    colManufacturer.setCellValueFactory(new PropertyValueFactory<>("Manufacturer"));
    colType.setCellValueFactory(new PropertyValueFactory<>("Type"));

  }

  public void addToProductionDB(ObservableList<ProductionRecord> productionRun) {

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/production";

    //  Database credentials
    final String USER = "";
    final String PASS = reverseString(getPwFromFile());
    Connection conn = null;
    Statement stmt = null;

    try {

      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      //int productionNum = (int) recordDB.;

      for (int i = 0; i < productionRun.size(); i++) {

        final String sql = "INSERT INTO ProductionRecord(product_id, serial_num, date_produced) "
            + "VALUES ( ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, productionRun.get(i).getProductID());
        ps.setString(2, productionRun.get(i).getSerialNumber());
        ps.setTimestamp(3, productionRun.get(i).getDateProduced());
        ps.executeUpdate();
      }

      stmt.close();
      conn.close();

    } catch (ClassNotFoundException e) {
      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public ArrayList<ProductionRecord> loadProductionLog() {
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/production";

    //  Database credentials
    final String USER = "";
    final String PASS = reverseString(getPwFromFile());
    Connection conn = null;
    Statement stmt = null;

    ArrayList<ProductionRecord> records = new ArrayList<>();

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query
      stmt = conn.createStatement();

      //int productionNum = (int) recordDB.;
      final String sql = "SELECT * FROM ProductionRecord";
      PreparedStatement ps = conn.prepareStatement(sql);

      ResultSet rs = ps.executeQuery();
      while(rs.next()){

        //get properties from the result set
        int prodsNum = rs.getInt(1);
        int prodId = rs.getInt(2);
        String serialNum = rs.getString(3);
        Timestamp date = rs.getTimestamp(4);

        records.add(new ProductionRecord(prodsNum, prodId, serialNum, date));
      }

      showProduction(records);

      stmt.close();
      conn.close();

    } catch (ClassNotFoundException e) {
      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return records;
  }

  // Reverses Password
  public String reverseString(String pw){

    return (pw.length() <= 1) ? pw : pw.substring(pw.length() - 1)
        + reverseString(pw.substring(0, pw.length() - 1));
  }

  public String getPwFromFile(){

    try{
      BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\ffafu\\OneDrive - Florida Gulf Coast University\\Fall 2020 Courses\\OOP Work\\JDK Projects\\ProductionProject\\src\\main\\java\\password DB.txt"));

      StringBuilder content = new StringBuilder();
      String line;

      while ((line = reader.readLine()) != null) {
        content.append(line);
        content.append(System.lineSeparator());
      }

      return content.toString().trim();

    } catch(IOException ex){
      return null;
    }
  }

  public void showProduction(ArrayList<ProductionRecord> record){

    for(int i = 0; i < record.size(); i++){

      txtAreaProductLog.appendText(record.get(i).toString());
    }
  }

  public Product getProductName(int id){

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/production";

    //  Database credentials
    final String USER = "";
    final String PASS = reverseString(getPwFromFile());
    Connection conn;
    Statement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      String sql = "SELECT * FROM product WHERE id = ?";
      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setInt(1, id);

      ResultSet rs = ps.executeQuery();

      if(rs.next()) {

        // Get properties
        String name = rs.getString("product_name");

        ItemType type = null;

        for(ItemType item: ItemType.values()){
          if(String.valueOf(item).equals(rs.getString("item_type"))){
            type = item;
          }
        }

        String manuf = rs.getString("manufacturer");

        // Return a product
        return new Product(id, name, manuf, type);
      }

      ps.close();
      conn.close();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public int getSameProductTypeAmnt(int id){

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/production";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn;
    Statement stmt;

    int sameTypeAmnt = 0;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      String sql = "SELECT * FROM productionRecord WHERE product_id = ?";
      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setInt(1, id);

      ResultSet rs = ps.executeQuery();

      while(rs.next()) {

        // Get properties
        String serialNum = rs.getString("serial_num");

        String subSerialNum = serialNum.substring(5);
        sameTypeAmnt = Integer.parseInt(subSerialNum);

        }

      ps.close();
      conn.close();

    } catch (ClassNotFoundException e) {
      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  public static Employee getUser(String username){
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/production";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
    Connection conn;
    Statement stmt;

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      String sql = "SELECT * FROM employee WHERE user = ?";
      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(1, username);
      ResultSet rs = ps.executeQuery();

      if(rs.next()) {

        // Get properties
        String name = rs.getString("name");
        String pw = rs.getString("password");

        return new Employee(name, pw);
      }

      ps.close();
      conn.close();

    } catch (ClassNotFoundException e) {
      e.printStackTrace();

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }
}