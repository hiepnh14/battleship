
package edu.duke.hhn3.battleship;
import java.util.HashMap;
import java.util.HashSet;
/**
 * @program: battleship
 * @description: {@link NonRectangleShip} class
 **/
public class NonRectangleShip extends BasicShip<Character> {
  private final String name;
  private final Character data;
  public String getName() {
    return name;
  }
  /**
   * @description: to Constructs a full {@link NonRectangleShip} 
   */
  public NonRectangleShip(String name, Coordinate upperLeft, char orient, ShipDisplayInfo<Character> myDisplayInfo, ShipDisplayInfo<Character> enemyDisplayInfo, Character data) {
    super(makeCoordsV2(upperLeft, orient, data), myDisplayInfo, enemyDisplayInfo); //specify how to call parent class constructor
    this.name = name;
    this.data = data;
  }
  /**
   * @description: to construct a simple, minimal {@link NonRectangleShip}
   */
  public NonRectangleShip(String name, Coordinate upperLeft, char orient, Character data, Character onHit) {
    this(name, upperLeft, orient, new SimpleShipDisplayInfo<Character>(data, onHit), new SimpleShipDisplayInfo<>(null, data), data);
  }
  /**
   * @description: to add Coordinate the the ship occupies
   * @param: Coordinate upperLeft, width and height of the ship
   * @return: HashSet<Coordinate> of the ship on the board
   **/
  static HashMap<Integer, Coordinate> makeCoordsV2(Coordinate upperLeft, char orient, char letter) {
    if (letter == 'b')
      return makeBattleShipCoords(upperLeft, orient);
    else if (letter == 'c')
      return makeCarrierCoords(upperLeft, orient);
    else
      throw new IllegalArgumentException();
  }
  /**
   * @description: to add Coordinate the Carrier ship occupies
   * @param: Coordinate upperLeft, width and height of the ship
   * @return: HashSet<Coordinate> of the ship on the board
   **/
  static HashMap<Integer, Coordinate> makeCarrierCoords(Coordinate upperLeft, char orient) {
    HashMap<Integer, Coordinate> coordinates = new HashMap<>();
    int row = upperLeft.getRow();
    int column = upperLeft.getColumn();
    int left = 0;
    int right = 0;
    int up = 0;
    int down = 0;
    int order = 0;
    if (orient == 'U') {
      left = 4;
      right = 3;
    }
    else if (orient == 'D') {
      left = 3;
      right = 4;
    }
    else if (orient == 'L') {
      up = 3;
      down = 4;
    }
    else //(orient == 'R')
    {
      up = 4;
      down = 3;
    }
    for (int l = row; l < row + left; l++) {
      coordinates.put(order++, new Coordinate(l, column));
    }
    for (int r = row + 4; r > row +4 - right; r--) {
      coordinates.put(order++, new Coordinate(r, column + 1));
    }
    for (int u = column + 4; u > column + 4 - up; u--) {
      coordinates.put(order++, new Coordinate(row, u));
    }
    for (int d = column; d < column + down; d++){
      coordinates.put(order++, new Coordinate(row + 1, d));
    } 
    return coordinates;
  }

    
  /**
   * @description: to add Coordinate the the ship occupies
   * @param: Coordinate upperLeft, width and height of the ship
   * @return: HashSet<Coordinate> of the ship on the board
   **/
  static HashMap<Integer, Coordinate> makeBattleShipCoords(Coordinate upperLeft, char orient) {
    HashMap<Integer, Coordinate> coordinates = new HashMap<>();
    int row = upperLeft.getRow();
    int column = upperLeft.getColumn();
    int order = 0;
    if (orient == 'U') {
      coordinates.put(order++, new Coordinate(row, column + 1));
      for (int c = column; c < column + 3; c++) {
        coordinates.put(order++, new Coordinate(row + 1, c));
      }
    }
    else if (orient == 'D') {
      coordinates.put(order++, new Coordinate(row + 1, column + 1));
      for (int c = column; c < column + 3; c++) {
        coordinates.put(order++, new Coordinate(row, c));
      }
    }
    else if (orient == 'L') {
      coordinates.put(order++, new Coordinate(row + 1, column));
      for (int r = row; r < row + 3; r++) {
        coordinates.put(order++, new Coordinate(r, column + 1));
      }
    }
    else //(orient == 'R') //already ensure the Orientation is valid
      {
        coordinates.put(order++, new Coordinate(row + 1, column + 1));
      for (int r = row; r < row + 3; r++) {
        coordinates.put(order++, new Coordinate(r, column));
      }
    }        
    return coordinates;
  }

}
