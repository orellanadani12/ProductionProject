import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents an employee for the production GUI.
 *
 * @author Fernando Orellana
 */
public class Employee {

  /**
   * The suffix for the user email addresses.
   */
  private static final String EMAIL_SUF = "@oracleacademy.Test";

  /**
   * Pattern for an invalid name.
   */
  private static final Pattern INVALID_PATTERN = Pattern.compile("[^a-z A-Z]+");

  /**
   * Pattern for any lowercase letters.
   */
  private static final Pattern LOW_PATTERN = Pattern.compile("[a-z]+");

  /**
   * Pattern for any uppercase letters.
   */
  private static final Pattern UPPER_PATTERN = Pattern.compile("[A-Z]+");

  /**
   * Pattern for any special characters.
   */
  private static final Pattern SPECIAL_PATTERN = Pattern.compile("[^a-z A-Z0-9]+");

  /**
   * The user's full name(First and Last Name).
   */
  private String name;

  /**
   * The user's username.
   */
  private String username;

  /**
   * The user's password.
   */
  private String password;

  /**
   * The user's email.
   */
  private String email;

  /**
   * Creates an employee(user) with credentials.
   *
   * @param fullname user's first and last name with space in between
   * @param password user's password containing(one upper, one lower, and one special character)
   */
  Employee(String fullname, String password) {

    if (checkName(fullname)) {

      setName(fullname);

    } else {

      this.name = fullname.trim();
      this.username = "default";
      this.email = "user" + EMAIL_SUF;
    }

    if (isValidPassword(password)) {

      setPassword(password);

    } else {

      this.password = "pw";
    }
  }

  /**
   * Gets the user's name.
   *
   * @return the full name
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the user's username.
   *
   * @return the username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Gets the user's email.
   *
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Gets the user's password.
   *
   * @return the password
   */
  public String getPassword() {
    return password;
  }

  /**
   * Set the user's username(all lowercase) based on the user's name.
   *
   * @param firstName the user's first name
   * @param lastName  the user's last name
   */
  private void setUsername(String firstName, String lastName) {

    this.username = firstName.toLowerCase().charAt(0) + lastName.toLowerCase();
  }

  /**
   * Set the user's password.
   *
   * @param password the user's password
   */
  public void setPassword(String password) {

    if (!isValidPassword(password)) {

      throw new IllegalArgumentException("Invalid Password! It should contain:\n"
          + "a capital letter, a lowercase letter, and a special character");
    }
    this.password = password;
  }

  /**
   * Set the user's email(all lowercase) based on the user's name and email suffix.
   *
   * @param firstName the user's first name
   * @param lastName  the user's last name
   */
  public void setEmail(String firstName, String lastName) {

    this.email = firstName.toLowerCase() + "." + lastName.toLowerCase() + EMAIL_SUF;
  }

  /**
   * Set the user's full name with space in between.
   *
   * @param fullName the user's first and last name
   */
  public void setName(String fullName) {

    if (!checkName(fullName)) {
      throw new IllegalArgumentException(
          "Invalid Name. Format: FirstName LastName(Space in between)");
    }
    this.name = fullName.trim();

    String[] tokens = name.split(" ", 2);
    setUsername(tokens[0], tokens[1]);
    setEmail(tokens[0], tokens[1]);
  }

  /**
   * Checks if the user's name is valid.
   *
   * @param name the user's name
   * @return the name if valid
   */
  public boolean checkName(String name) {

    Matcher invalidMatch = INVALID_PATTERN.matcher(name);
    return name.contains(" ") && !invalidMatch.find();
  }

  /**
   * Checks if the user's password is valid.
   *
   * @param password the user's password
   * @return the password if valid
   */
  public boolean isValidPassword(String password) {

    Matcher upper = UPPER_PATTERN.matcher(password);
    Matcher lower = LOW_PATTERN.matcher(password);
    Matcher special = SPECIAL_PATTERN.matcher(password);

    return upper.find() && lower.find() && special.find();
  }

  /**
   * Creates a ProdController and Employee objects to be return in a String.
   *
   * @return the data in a String
   */
  @Override
  public String toString() {

    ProdController prod = new ProdController();
    Employee name = prod.getUserInDb(getName());
    Employee pw = prod.getUserInDb(getPassword());

    return "Employee Details: \nName: " + name + "\n" + "Username: " + username + "\n"
        + "Email: " + email + "\n" + "Password: " + pw;
  }
}
