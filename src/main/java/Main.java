import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Main class of the Production application.
 *
 * @author Fernando Orellana
 */
public class Main extends Application {

  /**
   * Launches the application.
   *
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * The starting point for the application.
   *
   * <p>
   *   Loads the fxml file and creates the GUI
   * </p>
   * @param primaryStage the root JavaFX
   */
  @Override
  public void start(Stage primaryStage) throws IOException {

    Parent root = FXMLLoader.load(getClass().getResource("production.fxml"));
    Scene scene = new Scene(root);

    primaryStage.setScene(scene);
    primaryStage.setTitle("Production Project");
    primaryStage.show();

  }
}

