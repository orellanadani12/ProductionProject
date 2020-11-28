import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Main extends Application {

  private static final String PRODUCT_FXML = "production.fxml";
  private static final String LOGIN_FXML = "login.fxml";

  private static Stage primaryStage;
  private static Stage productionWindow;
  private static Stage loginPopUp;

  private static ProdController mainControl;
  private static LoginController loginControl;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage){

    /*Parent root = FXMLLoader.load(getClass().getResource("production.fxml"));
    Scene scene = new Scene(root, 811, 507);
    primaryStage.setTitle("Production Project");
    primaryStage.setScene(scene);*/

    setPrimaryStage(primaryStage);

    makeLoginPopUp();
    getLoginPopUp().show();
    getLoginControl().setUserFld("forellana");

    // connect to DB
    ProdController.connect();
  }

  // Opens the Production window & Closes the Login PopUp
  public static void productionWindow(Employee user){

    closeLoginPopUp();

    makeProductionWindow();
    getMainControl().setUser(user);
    getProductionWindow().show();
  }

  // Closes the Production Window & Re-Opens the Login PopUp
  public static void logOut(){

    closeProductionWindow();

    makeLoginPopUp();
    getLoginPopUp().show();
  }

  private void setPrimaryStage(Stage stage){
    primaryStage = stage;
  }

  private static Stage getProductionWindow(){
    return productionWindow;
  }

  private static Stage getLoginPopUp(){
    return loginPopUp;
  }

  private static ProdController getMainControl(){
    return mainControl;
  }

  private LoginController getLoginControl(){
    return loginControl;
  }

  private static void makeProductionWindow(){

    if(productionWindow != null) {
      productionWindow.close();
    }

    try{

      Pair<Stage, Object> rs = createWindow(primaryStage, "Production Project", PRODUCT_FXML, 811, 507, null);

      productionWindow = rs.getKey();
      mainControl = (ProdController) rs.getValue();

    } catch(IOException ex){
      ex.printStackTrace();
    }
  }

  private static void makeLoginPopUp(){

    if(loginPopUp != null){
      loginPopUp.close();
    }

    try{
      Pair<Stage, Object> rs = createWindow(new Stage(), "User Login", LOGIN_FXML, 327, 171,
          Modality.APPLICATION_MODAL);

      loginPopUp = rs.getKey();
      loginControl = (LoginController) rs.getValue();

    } catch(IOException | ClassCastException ex){
      ex.printStackTrace();
    }
  }

  private static void closeProductionWindow(){

    if(productionWindow != null){
      productionWindow.close();
      productionWindow = null;
      mainControl = null;
    }
  }

  private static void closeLoginPopUp() {

    if(loginPopUp != null){
      loginPopUp.close();
      loginPopUp = null;
      loginControl = null;
    }
  }

  private static Pair<Stage, Object> createWindow(Stage stage, String title, String fxmlFile,
      int width, int height, Modality modality)
      throws IOException {

    //load fxml file
    Parent root = FXMLLoader.load(Main.class.getResource("production.fxml"));

    // set title, scene, and modality
    stage.setTitle(title);
    stage.setScene(new Scene(root, width, height));

    if(modality != null){
      stage.initModality(modality);
    }

    return new Pair<>(stage, root);
  }
}

