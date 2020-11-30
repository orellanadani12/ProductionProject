import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Employee {

  private static final String EMAIL_SUF = "@oracleacademy.Test";
  private static final Pattern INVALID_PATTERN = Pattern.compile("[^a-z A-Z]+");
  private static final Pattern LOW_PATTERN = Pattern.compile("[a-z]+");
  private static final Pattern UPPER_PATTERN = Pattern.compile("[A-Z]+");
  private static final Pattern SPECIAL_PATTERN = Pattern.compile("[^a-z A-Z0-9]+");

  //Fields
  private String name;
  private String username;
  private String password;
  private String email;

  //Constructor

  Employee(String fullname, String password){
    if(checkName(fullname)) {
      setName(fullname);
    }else{
      this.name = fullname.trim();
      this.username = "default";
      this.email = "user" + EMAIL_SUF;
    }

    if(isValidPassword(password)){
      setPassword(password);
    }else{
      this.password = "pw";
    }
  }

  //Getters
  public String getName(){
    return name;
  }

  public String getUsername(){
    return username;
  }

  public String getEmail(){
    return email;
  }

  public String getPassword(){
    return password;
  }

  //Setters
  private void setUsername(String firstName, String lastName){

    this.username = firstName.toLowerCase().charAt(0) + lastName.toLowerCase();
  }

  public void setPassword(String password){

    if(!isValidPassword(password)) {
      throw new IllegalArgumentException("Invalid Password! It should contain:\n"
          + "a capital letter, a lowercase letter, and a special character");
    }
    this.password = password;
  }

  public void setEmail(String firstName, String lastName){

    this.email = firstName.toLowerCase() + "." + lastName.toLowerCase() + EMAIL_SUF;
  }

  public void setName(String fullName){

    if(!checkName(fullName)) {
      throw new IllegalArgumentException("Invalid Name. Format: FirstName LastName(Space in between)");
    }
    this.name = fullName.trim();

    String[] tokens = name.split(" ", 2);
    setUsername(tokens[0], tokens[1]);
    setEmail(tokens[0], tokens[1]);
  }

  //Methods
  public boolean checkName(String name){

    Matcher invalidMatch = INVALID_PATTERN.matcher(name);
    return name.contains(" ") && !invalidMatch.find();
  }

  public boolean isValidPassword(String password){

    Matcher upper = UPPER_PATTERN.matcher(password);
    Matcher lower = LOW_PATTERN.matcher(password);
    Matcher special = SPECIAL_PATTERN.matcher(password);

    return upper.find() && lower.find() && special.find();
  }

  @Override
  public String toString() {

    ProdController prod = new ProdController();
    Employee name = prod.getUserInDb(getName());
    Employee pw = prod.getUserInDb(getPassword());

    return "Employee Details: \nName: " + name + "\n" + "Username: " + username + "\n"
        + "Email: " + email + "\n" + "Password: " + pw;
  }
}
