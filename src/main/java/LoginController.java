import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

  @FXML
  private TextField fldLoginUser;

  @FXML
  private PasswordField fldLoginPass;

  @FXML
  private Button btnLogin;

  @FXML
  private Label lblLoginMsg;

  public void initialize(){

    // clear message label
    lblLoginMsg.setText("");
  }
  public void setUserFld(String username){

    fldLoginUser.setText(username);
  }

  @FXML
  void btnLoginAction(ActionEvent event) {

    try{
      // Makes sure the username is not empty
      String userName = fldLoginUser.getText().trim();
      if(userName.isEmpty()) {
        throw new IllegalArgumentException("Please type a username");
      }

      // Makes sure the password is not empty
      String pw = fldLoginPass.getText().trim();
      if(pw.isEmpty()) {
        throw new IllegalArgumentException("Please type a password");
      }

      // Gets the user credentials
      Employee user = ProdController.getUserInDb(fldLoginUser.getText());
      if(user == null) {
        throw new IllegalArgumentException("Invalid username");
      }

      // Makes sure the password is correct
      if(!user.getPassword().equals(pw)){
        throw new IllegalArgumentException("Invalid password");
      }

      // Log in
      Main.productionWindow(user);

    } catch(IllegalStateException exception){

    }
  }

}

