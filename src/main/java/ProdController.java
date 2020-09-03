import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ProdController {

    @FXML
    private Label lblOutput;
    @FXML
    public void addProduct() {
        System.out.println("Product Added");

    }
    public void recordProduction() {
        System.out.println("Production Recorded");

    }

}