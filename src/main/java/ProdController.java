import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;
import java.util.Set;

public class ProdController {

  @FXML
  private TextField txtProductName;

  @FXML
  private TextField txtManufacturer;

  @FXML
  private ChoiceBox<String> cbItemType;

  @FXML
  private Button productButton;

  @FXML
  private Button recordButton;

  @FXML
  private ComboBox<String> cmbQuantity;

  @FXML
  void addProduct(ActionEvent event) {
    connectToDb();
    System.out.println("Product Added");
  }

  @FXML
  void recordProduction(ActionEvent event) {
    System.out.println("Production Recorded");
  }

  public void initialize() {

    //options for choiceBox
    for(ItemType item : ItemType.values()) {
      cbItemType.getItems().add(String.valueOf(item));
    }
    cbItemType.getSelectionModel().selectFirst();

    for (int count = 1; count <= 10; count++) {
      cmbQuantity.getItems().add(String.valueOf(count));
    }
    cmbQuantity.setEditable(true);
    cmbQuantity.getSelectionModel().selectFirst();

    // repl.it output need it
    Product product1 = new Widget("iPod", "Apple", ItemType.AUDIO);
    System.out.println(product1.toString());
    Product product2 = new Widget("Zune", "Microsoft", ItemType.AUDIOMOBILE);
    System.out.println(product2.toString());
  }


  public void connectToDb() {
    final String JDBC_DRIVER = "org.h2.Driver";
    final String DB_URL = "jdbc:h2:./res/production";

    //  Database credentials
    final String USER = "";
    final String PASS = "";
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
}