import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;
import javafx.scene.control.cell.PropertyValueFactory;

@SuppressWarnings("SameReturnValue")
public class ProdController {

  private static Connection conn;

  @FXML
  private Tab tabWelcome;

  @FXML
  private TextField fldUserName;


  @FXML
  private TextField fldNewUserName;

  @FXML
  private PasswordField fldUserPw;

  @FXML
  private PasswordField fldNewUserPw;

  @FXML
  private Label lblRetUserMessage;

  @FXML
  private Label lblNewUserMessage;

  @FXML
  private Tab tabProductionLine;

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
  private Tab tabProduce;

  @FXML
  private ComboBox<String> cmbQuantity;

  @FXML
  private ListView<Product> produceView;

  @FXML
  private Tab tabProductionLog;

  @FXML
  private TextArea txtAreaProductLog;

  @FXML
  void login() throws IllegalStateException {

    connect();

    // Makes sure the username is not empty
    String userName = fldUserName.getText().trim();
    if (userName.isEmpty()) {
      throw new IllegalArgumentException("Please type a Name");
    }

    // Makes sure the password is not empty
    String pw = fldUserPw.getText().trim();
    if (pw.isEmpty()) {
      throw new IllegalArgumentException("Please type a Password");
    }

    // Gets the user credentials
    Employee user = getUserInDb(fldUserName.getText());

    if (user.getName().equals(userName) && user.getPassword().equals(pw)) {

      lblRetUserMessage.setText("Welcome back " + user.getName() + "!");

      tabWelcome.setDisable(true);
    }
    else{
      lblRetUserMessage.setText("Wrong Name or Password! Please try again!");
    }

    tabProductionLog.setDisable(false);
    tabProductionLine.setDisable(false);
    tabProduce.setDisable(false);

  }

  @FXML
  void register() throws IllegalStateException {

    connect();
    userRegistration();

    // Makes sure the username is not empty
    String userName = fldNewUserName.getText().trim();
    if (userName.isEmpty()) {
      lblNewUserMessage.setText("Please type a Name");
    }

    // Makes sure the password is not empty
    String pw = fldNewUserPw.getText().trim();
    if (pw.isEmpty()) {
      lblNewUserMessage.setText("Please type a Password");
    }

    Employee user = getUserInDb(fldNewUserName.getText());
    lblNewUserMessage.setText("Employee Details: \nName: " + user.getName() + "\n" + "Username: "
        + user.getUsername() + "\n" + "Email: " + user.getEmail() + "\n" + "Password: " + user.getPassword());

    tabProductionLog.setDisable(false);
    tabProductionLine.setDisable(false);
    tabProduce.setDisable(false);
    tabWelcome.setDisable(true);
  }

  //List of products loaded from DB

  final ObservableList<Product> productLine = FXCollections.observableArrayList();


  public static void connect(){

    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/production";

    //  Database credentials
    final String USER = "";
    final String PASS = reverseString(Objects.requireNonNull(getPwFromFile()));

    try {
      // STEP 1: Register JDBC driver
      Class.forName(JDBC_DRIVER);

      //STEP 2: Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);

      //STEP 3: Execute a query
      Statement stmt = conn.createStatement();

      // STEP 4: Clean-up environment
      stmt.close();

    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }

  }

  public static void closeDb(){

    try {
      conn.close();
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
  }
  @FXML
  void addProduct() {
    connectToDb();
    System.out.println("Product Added");

    loadProductList();
  }

  @FXML
  void recordProduction() {
    System.out.println("Product Recorded");

    txtAreaProductLog.clear();
    Product prodRecord = produceView.getSelectionModel().getSelectedItem();
    int quantity = cmbQuantity.getSelectionModel().getSelectedIndex();
    ObservableList<ProductionRecord> productionRun = FXCollections.observableArrayList();

    int sameTypeAmount = getSameProductTypeAmount(prodRecord.getId());

    for (int i = 0; i <= quantity; i++) {
      productionRun.add(new ProductionRecord(prodRecord, ++sameTypeAmount));
    }
    addToProductionDB(productionRun);
    loadProductionLog();
  }

  public void initialize() {

    tabProductionLog.setDisable(true);
    tabProductionLine.setDisable(true);
    tabProduce.setDisable(true);

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

    connect();

    try {
      //STEP 3: Execute a query
      Statement stmt = conn.createStatement();

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

        String manufacturer = rs.getString(4);

        Product product = new Product(id, name, manufacturer, type);
        productLine.add(product);

        existingProduct.setItems(productLine);
        produceView.setItems(productLine);

      }

      // STEP 4: Clean-up environment
      stmt.close();
      closeDb();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void connectToDb() {
    connect();

    try {

      //STEP 3: Execute a query
      Statement stmt = conn.createStatement();

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
      // STEP 4: Clean-up environment
      stmt.close();
      closeDb();

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

    connect();

    try {

      //STEP 3: Execute a query
      Statement stmt = conn.createStatement();

      //int productionNum = (int) recordDB.;

      for (ProductionRecord productionRecord : productionRun) {

        final String sql = "INSERT INTO ProductionRecord(product_id, serial_num, date_produced) "
            + "VALUES ( ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, productionRecord.getProductID());
        ps.setString(2, productionRecord.getSerialNumber());
        ps.setTimestamp(3, productionRecord.getDateProduced());
        ps.executeUpdate();
      }

      stmt.close();
      closeDb();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public ArrayList<ProductionRecord> loadProductionLog() {

    connect();
    ArrayList<ProductionRecord> records = new ArrayList<>();

    try {
      //STEP 3: Execute a query
      Statement stmt = conn.createStatement();

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
      closeDb();

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return records;
  }

  // Reverses Password
  public static String reverseString(String pw){

    return (pw.length() <= 1) ? pw : pw.substring(pw.length() - 1)
        + reverseString(pw.substring(0, pw.length() - 1));
  }

  public static String getPwFromFile(){

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

    for (ProductionRecord productionRecord : record) {

      txtAreaProductLog.appendText(productionRecord.toString());
    }
  }

  public Product getProductName(int id){

    connect();

    try {

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

        String manufacturer = rs.getString("manufacturer");

        // Return a product
        return new Product(id, name, manufacturer, type);
      }

      ps.close();
      closeDb();

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public int getSameProductTypeAmount(int id){

    connect();

    try {

      String sql = "SELECT * FROM productionRecord WHERE product_id = ?";
      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setInt(1, id);

      ResultSet rs = ps.executeQuery();

      while(rs.next()) {

        // Get properties
        String serialNum = rs.getString("serial_num");

        String subSerialNum = serialNum.substring(5);
        int sameTypeAmount = Integer.parseInt(subSerialNum);

        }

      ps.close();
      closeDb();

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  public Employee getUserInDb(String userName) {

    connect();

    try {

      String sql = "SELECT * FROM employee WHERE name = ?";
      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(1, userName);

      ResultSet rs = ps.executeQuery();

      if(rs.next()){

        String name = rs.getString("name");
        String pw = rs.getString("password");

        return new Employee(name, pw);
      }

      ps.close();
      closeDb();

    } catch(SQLException e){
      e.printStackTrace();
    }
    return null;
  }

  public void userRegistration(){

    connect();

    try {

      String name = fldNewUserName.getText();
      String pw = fldNewUserPw.getText();

      Employee user = new Employee(name, pw);

      String sql = "INSERT INTO employee(name, email, password) VALUES (?, ?, ?)";
      PreparedStatement ps = conn.prepareStatement(sql);

      // Given Properties
      ps.setString(1, user.getName());
      ps.setString(2, user.getEmail());
      ps.setString(3, user.getPassword());
      ps.execute();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}