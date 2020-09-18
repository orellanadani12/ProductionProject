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
    cbItemType.getItems().add("AUDIO");
    cbItemType.getItems().add("VISUAL");
    cbItemType.getItems().add("AUDIO_MOBILE");
    cbItemType.getItems().add("VISUAL_MOBILE");

    for (int count = 1; count <= 10; count++) {
      cmbQuantity.getItems().add(String.valueOf(count));
    }
    cmbQuantity.setEditable(true);
    cmbQuantity.getSelectionModel().selectFirst();
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

      String insertSql = "INSERT INTO Product(item_type, manufacturer, product_name ) "
          + "VALUES ( '" + itemType + "', '" + manufacturer + "', '" + productName + "')";

      stmt.executeUpdate(insertSql);

      String sql = " SELECT item_type, manufacturer, product_name"
          + " FROM PRODUCT";

      ResultSet rs = stmt.executeQuery(sql);

      while (rs.next()) {
        System.out.println(rs.getString(1));
        System.out.println(rs.getString(2));
        System.out.println(rs.getString(3));
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
}