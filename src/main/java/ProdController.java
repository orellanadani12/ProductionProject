import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import java.sql.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * Handles user interaction with the Production GUI.
 *
 * @author Fernando Orellana
 */
public class ProdController {

  /**
   * The database connection.
   */
  private static Connection conn;

  /**
   * The Welcome Tab.
   */
  @FXML
  private Tab tabWelcome;

  /**
   * The user's name(input) text field.
   */
  @FXML
  private TextField fldUserName;

  /**
   * The New user's name(input) text field.
   */
  @FXML
  private TextField fldNewUserName;

  /**
   * The user's password(input) password field.
   */
  @FXML
  private PasswordField fldUserPw;

  /**
   * The New user's password(input) password field.
   */
  @FXML
  private PasswordField fldNewUserPw;

  /**
   * Message label for returning users.
   */
  @FXML
  private Label lblRetUserMessage;

  /**
   * Message label for new users.
   */
  @FXML
  private Label lblNewUserMessage;

  /**
   * The Production Line Tab.
   */
  @FXML
  private Tab tabProductionLine;

  /**
   * The product name(input) text field.
   */
  @FXML
  private TextField txtProductName;

  /**
   * The product manufacturer(input) text field.
   */
  @FXML
  private TextField txtManufacturer;

  /**
   * The Item Type Choice Box(User selection).
   */
  @FXML
  private ChoiceBox<String> cbItemType;

  /**
   * The table that shows the products from the database.
   */
  @FXML
  private TableView<Product> existingProduct;

  /**
   * The Production Name column for the table.
   */
  @FXML
  private TableColumn<?, ?> colProdName;

  /**
   * The Production Manufacturer column for the table.
   */
  @FXML
  private TableColumn<?, ?> colManufacturer;

  /**
   * The Production Type column for the table.
   */
  @FXML
  private TableColumn<?, ?> colType;

  /**
   * The Produce Tab.
   */
  @FXML
  private Tab tabProduce;

  /**
   * The Quantity combo box(user selection).
   */
  @FXML
  private ComboBox<String> cmbQuantity;

  /**
   * The List View of the products to produce.
   */
  @FXML
  private ListView<Product> produceView;

  /**
   * The Production Log Tab.
   */
  @FXML
  private Tab tabProductionLog;

  /**
   * The Production Log text area.
   */
  @FXML
  private TextArea txtAreaProductLog;

  /**
   * List of products loaded from the database.
   */
  final ObservableList<Product> productLine = FXCollections.observableArrayList();

  /**
   * Handles the login button being pressed.
   *
   * @throws IllegalStateException
   */
  @FXML
  void login() throws IllegalStateException {

    // Connects to the database
    connect();

    // Makes sure the user's name is not empty
    String userName = fldUserName.getText().trim();

    // Makes sure the password is not empty
    String pw = fldUserPw.getText().trim();

    // Gets the user credentials
    Employee user = getUserInDb(fldUserName.getText());

    //Starting point of the tabs
    tabProductionLog.setDisable(true);
    tabProductionLine.setDisable(true);
    tabProduce.setDisable(true);

    if (userName.isEmpty() && pw.isEmpty()){
      lblRetUserMessage.setText("Please type a Name and Password");
    }

    else if (userName.isEmpty()) {
      lblRetUserMessage.setText("Please type a Name");
    }

    else if (pw.isEmpty()) {
      lblRetUserMessage.setText("Please type a Password");
    }

    else if (!user.getName().equals(userName) || (user.getName().equals(userName) && !user.getPassword().equals(pw))){
      lblRetUserMessage.setText("Wrong Name or Password! Please try again!");
    }

    else if (user.getName().equals(userName) && user.getPassword().equals(pw)) {

      lblRetUserMessage.setText("Welcome back " + user.getName() + "!");

      tabProductionLog.setDisable(false);
      tabProductionLine.setDisable(false);
      tabProduce.setDisable(false);
      tabWelcome.setDisable(true);
    }
  }

  /**
   * Handles the register button being pressed.
   *
   * @throws IllegalStateException
   */
  @FXML
  void register() throws IllegalStateException {

    //Connects to database
    connect();

    //Registers the new user
    userRegistration();

    // Makes sure the username is not empty
    String userName = fldNewUserName.getText().trim();

    // Makes sure the password is not empty
    String pw = fldNewUserPw.getText().trim();


    Employee user = getUserInDb(fldNewUserName.getText());

    tabProductionLog.setDisable(true);
    tabProductionLine.setDisable(true);
    tabProduce.setDisable(true);

    if (userName.isEmpty() && pw.isEmpty()){

      lblNewUserMessage.setText("Please type a Name and Password");
    }
    else if (userName.isEmpty()) {
      lblNewUserMessage.setText("Please type a Name");
    }

    else if (pw.isEmpty()) {
      lblNewUserMessage.setText("Please type a Password");
    }
    else{

      lblNewUserMessage.setText("Employee Details: \nName: " + user.getName() + "\n" + "Username: "
          + user.getUsername() + "\n" + "Email: " + user.getEmail() + "\n" + "Password: " + user
          .getPassword());

      tabProductionLog.setDisable(false);
      tabProductionLine.setDisable(false);
      tabProduce.setDisable(false);
      tabWelcome.setDisable(true);
    }
  }

  /**
   * Connects to the database.
   */
  public static void connect() {

    //Properties
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

  /**
   * Closes connection to the database.
   */
  public static void closeDb() {

    //Closes the database connection
    try {
      conn.close();
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
  }

  /**
   * Handles the add product button being pressed.
   */
  @FXML
  void addProduct() {

    //Connects to the Product database
    connectToProductDb();

    System.out.println("Product Added");

    //Loads the products
    loadProductList();
  }

  /**
   * Handles the record production button being pressed.
   */
  @FXML
  void recordProduction() {
    System.out.println("Product Recorded");

    //Clears the text area
    txtAreaProductLog.clear();

    //Gets the user selection both product & quantity
    Product prodRecord = produceView.getSelectionModel().getSelectedItem();
    int quantity = cmbQuantity.getSelectionModel().getSelectedIndex();

    ObservableList<ProductionRecord> productionRun = FXCollections.observableArrayList();

    int sameTypeAmount = getSameProductTypeAmount(prodRecord.getId());

    //Adds the product to the database based on the quantity selected
    for (int i = 0; i <= quantity; i++) {
      productionRun.add(new ProductionRecord(prodRecord, ++sameTypeAmount));
    }
    addToProductionDB(productionRun);
    loadProductionLog();
  }

  /**
   * Initializes the Production GUI.
   */
  public void initialize() {

    //Initial tab settings
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

  /**
   * Loads the product to production line & produce.
   */
  public void loadProductList() {

    //Clears the Observable List so it doesn't duplicate the products
    productLine.clear();

    //Connects to the database
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

        //Adds the products to the table view
        existingProduct.setItems(productLine);

        //Adds the products to the produce list view
        produceView.setItems(productLine);

      }

      // STEP 4: Clean-up environment
      stmt.close();
      closeDb();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Connects to the product database to insert & select products.
   */
  public void connectToProductDb() {

    //Connects to the database
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

  /**
   * Adds data to the Observable List(Table columns).
   */
  public void setupProductLineTable() {

    // adds info to ObservableList
    colProdName.setCellValueFactory(new PropertyValueFactory<>("Name"));
    colManufacturer.setCellValueFactory(new PropertyValueFactory<>("Manufacturer"));
    colType.setCellValueFactory(new PropertyValueFactory<>("Type"));

  }

  /**
   * Inserts a product to the production record database.
   *
   * @param productionRun list of production records created
   */
  public void addToProductionDB(ObservableList<ProductionRecord> productionRun) {

    //Connects to the database
    connect();

    try {

      //STEP 3: Execute a query
      Statement stmt = conn.createStatement();

      //Inserts products to the database
      for (ProductionRecord productionRecord : productionRun) {

        final String sql = "INSERT INTO ProductionRecord(product_id, serial_num, date_produced) "
            + "VALUES ( ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, productionRecord.getProductID());
        ps.setString(2, productionRecord.getSerialNumber());
        ps.setTimestamp(3, productionRecord.getDateProduced());
        ps.executeUpdate();
      }

      //Clean up environment(Close connection to the database)
      stmt.close();
      closeDb();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Loads a list of all production records in database.
   *
   * @return list of production records from database
   */
  public ArrayList<ProductionRecord> loadProductionLog() {

    //Connects to the database
    connect();

    ArrayList<ProductionRecord> records = new ArrayList<>();

    try {
      //STEP 3: Execute a query
      Statement stmt = conn.createStatement();

      //Selects all products from the database
      final String sql = "SELECT * FROM ProductionRecord";
      PreparedStatement ps = conn.prepareStatement(sql);

      ResultSet rs = ps.executeQuery();

      //Gets properties
      while (rs.next()) {

        //get properties from the result set
        int prodsNum = rs.getInt(1);
        int prodId = rs.getInt(2);
        String serialNum = rs.getString(3);
        Timestamp date = rs.getTimestamp(4);

        records.add(new ProductionRecord(prodsNum, prodId, serialNum, date));
      }

      showProduction(records);

      //Close connection to the database
      stmt.close();
      closeDb();

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return records;
  }

  /**
   * Reverses the password.
   *
   * @param pw gets the password
   * @return string of the password reversed
   */
  public static String reverseString(String pw) {

    // Reverses Password
    return (pw.length() <= 1) ? pw : pw.substring(pw.length() - 1)
        + reverseString(pw.substring(0, pw.length() - 1));
  }

  /**
   * Gets the password from the file.
   *
   * @return string of the password in file
   */
  public static String getPwFromFile() {

    //Gets database password from file
    try {
      BufferedReader reader = new BufferedReader(new FileReader(
          "C:\\Users\\ffafu\\OneDrive - Florida Gulf Coast University\\Fall 2020 Courses\\OOP Work\\JDK Projects\\ProductionProject\\src\\main\\java\\password DB.txt"));

      StringBuilder content = new StringBuilder();
      String line;

      while ((line = reader.readLine()) != null) {
        content.append(line);
        content.append(System.lineSeparator());
      }

      return content.toString().trim();

    } catch (IOException ex) {
      return null;
    }
  }

  /**
   * Shows production records in the text area.
   *
   * @param record list of production records created
   */
  public void showProduction(ArrayList<ProductionRecord> record) {

    //Adds products to the text area
    for (ProductionRecord productionRecord : record) {

      txtAreaProductLog.appendText(productionRecord.toString());
    }
  }

  /**
   * Gets the product id.
   *
   * @param id gets the id
   * @return Product with the specified id
   */
  public Product getProductName(int id) {

    //Connects to the database
    connect();

    try {

      String sql = "SELECT * FROM product WHERE id = ?";
      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setInt(1, id);

      ResultSet rs = ps.executeQuery();

      //Gets properties
      if (rs.next()) {

        // Get properties
        String name = rs.getString("product_name");

        ItemType type = null;

        for (ItemType item : ItemType.values()) {
          if (String.valueOf(item).equals(rs.getString("item_type"))) {
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

  /**
   * Gets the id of the same product type(compares id).
   *
   * @param id gets the id
   * @return int id of the product
   */
  public int getSameProductTypeAmount(int id) {

    //Connects to the database
    connect();

    try {

      String sql = "SELECT * FROM productionRecord WHERE product_id = ?";
      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setInt(1, id);

      ResultSet rs = ps.executeQuery();

      // Get properties
      while (rs.next()) {

        String serialNum = rs.getString("serial_num");

        String subSerialNum = serialNum.substring(5);
        int sameTypeAmount = Integer.parseInt(subSerialNum);

      }

      //Close connection to the database
      ps.close();
      closeDb();

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return 0;
  }

  /**
   * Gets user from Employee database.
   *
   * @param userName gets the name of the user
   * @return Employee if found on database
   */
  public Employee getUserInDb(String userName) {

    //Connects to the database
    connect();

    try {

      String sql = "SELECT * FROM employee WHERE name = ?";
      PreparedStatement ps = conn.prepareStatement(sql);

      ps.setString(1, userName);

      ResultSet rs = ps.executeQuery();

      //Get properties
      if (rs.next()) {

        String name = rs.getString("name");
        String pw = rs.getString("password");

        return new Employee(name, pw);
      }

      //Close connection to the database
      ps.close();
      closeDb();

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Registers a user to the Employee database.
   */
  public void userRegistration() {

    //Connects to the database
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