import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  //private LoginController loginControl;
  //private static ProdController mainControl;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {

    Parent root = FXMLLoader.load(getClass().getResource("production.fxml"));
    Scene scene = new Scene(root);

    primaryStage.setScene(scene);
    primaryStage.setTitle("Production Project");
    primaryStage.show();

  }
}

