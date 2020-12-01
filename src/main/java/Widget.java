/**
 * Represents a widget for testing the product class.
 *
 * @author Fernando Orellana
 */
public class Widget extends Product {

  /**
   * Creates a widget.
   *
   * @param name         the widget's name
   * @param type         the widget's type
   * @param manufacturer the widget's manufacturer
   */
  Widget(String name, String manufacturer, ItemType type) {
    super(name, manufacturer, type);
  }
}
