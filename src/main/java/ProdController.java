import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProdController {

    @FXML
    private Label lblOutput;
    @FXML
    public void sayHello() {
        lblOutput.setText("Hello FXML!");

    }

}