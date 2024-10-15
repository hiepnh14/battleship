
package edu.duke.hhn3.battleship;
import java.util.HashMap;
/**
 * @program: battleship
 * @description: RectangleShip class
 **/
public class RectangleShip<T> extends BasicShip<T> {
  private final String name;

  public String getName() {
    return name;
  }
  /**
   * @description: to construct a full, complete information of a {@link RectangleShip}
   */
  public RectangleShip(String name, Coordinate upperLeft, int width, int height, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
    super(makeCoords(upperLeft, width, height), myDisplayInfo, enemyDisplayInfo); //specify how to call parent class constructor
  this.name = name;
  }
  public RectangleShip(String name, Coordinate upperLeft, int width, int height, T data, T onHit) {
    this(name, upperLeft, width, height, new SimpleShipDisplayInfo<T>(data, onHit), new SimpleShipDisplayInfo<>(null, data));
  }
  /**
   * @description: to construct a minimal {@link RectangleShip} for testing
   */
  public RectangleShip(Coordinate upperLeft, T data, T onHit) {
    this("testship", upperLeft, 1, 1, data, onHit);
  }   
  /**
   * @description: to add Coordinate the the ship occupies
   * @param: Coordinate upperLeft, width and height of the ship
   * @return: HashSet<Coordinate> of the ship on the board
   **/

  static HashMap<Integer, Coordinate> makeCoords(Coordinate upperLeft, int width, int height) {
    HashMap<Integer, Coordinate> coordinates = new HashMap<>();
    int order = 0;
    for (int row = upperLeft.getRow(); row < upperLeft.getRow() + height; row++) {
      for (int col = upperLeft.getColumn(); col < upperLeft.getColumn() + width; col++) {
        coordinates.put(order++, new Coordinate(row, col));
      }
    }
    return coordinates;
  }

}
