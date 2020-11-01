import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.*;
import java.util.Set;
import javafx.scene.control.cell.PropertyValueFactory;

public class ProdController {


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

    setupProductLineTable();
    setupProduceListView();
  }

  @FXML
  void recordProduction(ActionEvent event) {
    System.out.println("Product Recorded");

    Product prodProduced = new Product("Ipod", "Apple", ItemType.VISUALMOBILE);
    prodProduced.setId(5);
    ProductionRecord productRec = new ProductionRecord(prodProduced, 7);
    txtAreaProductLog.appendText(productRec.toString());
  }

  public void initialize() {

    //options for choiceBox
    for(ItemType item : ItemType.values()) {
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

    // repl.it output need it
    Product product1 = new Product("iPod", "Apple", ItemType.AUDIO);
    System.out.println(product1.toString());
    Product product2 = new Product("Zune", "Microsoft", ItemType.AUDIOMOBILE);
    System.out.println(product2.toString());

    //Test MultiMedia
      AudioPlayer newAudioProduct = new AudioPlayer("DP-X1A", "Onkyo",
          "DSD/FLAC/ALAC/WAV/AIFF/MQA/Ogg-Vorbis/MP3/AAC", "M3U/PLS/WPL");
      Screen newScreen = new Screen("720x480", 40, 22);
      MoviePlayer newMovieProduct = new MoviePlayer("DBPOWER MK101", "OracleProduction", newScreen,
          MonitorType.LCD);
      ArrayList<MultimediaControl> productList = new ArrayList<MultimediaControl>();
      productList.add(newAudioProduct);
      productList.add(newMovieProduct);
      for (MultimediaControl p : productList) {
        System.out.println(p);
        p.play();
        p.stop();
        p.next();
        p.previous();
      }

      // Repl.it Issue 4
      ProductionRecord record = new ProductionRecord(product1, 1);
    System.out.println(record);
    txtAreaProductLog.appendText(record.toString());
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

  public void setupProductLineTable() {

    String name = txtProductName.getText();
    String manufacturer = txtManufacturer.getText();
    String type = cbItemType.getValue();

    // adds info to ObservableList
    Product product = new Product(name, manufacturer, ItemType.valueOf(type));
    productLine.add(product);
    colProdName.setCellValueFactory(new PropertyValueFactory<>("Name"));
    colManufacturer.setCellValueFactory(new PropertyValueFactory<>("Manufacturer"));
    colType.setCellValueFactory(new PropertyValueFactory<>("Type"));

    existingProduct.setItems(productLine);

  }

  public void setupProduceListView() {

  produceView.setItems(productLine);
  }
}