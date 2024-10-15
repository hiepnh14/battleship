package edu.duke.hhn3.battleship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Function;
/**
 * @program: Battleship
 * @description: Class of BattleShipBoard with Board as interface
 **/
public class BattleShipBoard<T> implements Board<T> {
  /**
   * private field of Board
   * int width of the board
   * int height of the board
   * {@link java.util.ArrayList} Ships
   */
  private final int width;
  private final int height;
  private final ArrayList<Ship<T>> myShips;
  private final PlacementRuleChecker<T> placementChecker;
  private final HashSet<Coordinate> enemyMisses; // To store and show on enemy's board
  private final HashMap<Coordinate, T> enemyHits; // To store and show on enemy's board
  private final T missInfo; 
  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }
  public HashSet<Coordinate> getEnemyMisses() {
    return enemyMisses;
  }
  public Boolean removeShip(Ship<T> ship) {
    return myShips.remove(ship);
  }
  /**
   * Constructs a BattleShipBoard with the specified width
   * and height
   * 
   * @param int weight is the width of the newly constructed board.
   * @param int height is the height of the newly constructed board.
   * @throws IllegalArgumentException if the width or height are less than or
   *                                  equal to zero.
   */  
  public BattleShipBoard(int width, int height, T missInfo) {
    if (width <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's width must be positive but is " + width);
    }
    if (height <= 0) {
      throw new IllegalArgumentException("BattleShipBoard's height must be positive but is " + height);
    }
    this.width = width;
    this.height = height;
    this.myShips = new ArrayList<>();
    NoCollisionRuleChecker<T> noCollision = new NoCollisionRuleChecker<T>(null);
    this.placementChecker = new InBoundsRuleChecker<T>(noCollision);
    this.enemyMisses = new HashSet<>();
    this.enemyHits = new HashMap<>();
    this.missInfo = missInfo;
  }
  
  /**
   * fireAt
   * @param: {@link Coordinate} c
   * @return: Ship<T>
   */
  public Ship<T> fireAt(Coordinate c) {
    for (Ship<T> s: myShips) {
      if (s.occupiesCoordinates(c)){
        s.recordHitAt(c);
        enemyMisses.remove(c);
        enemyHits.put(c, s.getDisplayInfoAt(c, false));
        return s;
      }
    }
    //when it misses
    enemyHits.remove(c);
    enemyMisses.add(c);
    return null;  
    
  }
  /**
   * Add Ship
   * 
   * @param Ship to be added
   * @throws IllegalArgumentException if the width or height are less than or
   *                                 equal to zero.
   * @return null if adding the ship is successful
   */
  public String tryAddShip(Ship<T> toAdd) {
    if (placementChecker.checkPlacement(toAdd, this) != null) {
      return placementChecker.checkPlacement(toAdd, this);
    }
    myShips.add(toAdd);
    return null;
  }

  /**
   * Find what is at a Coordinate for Self
   * 
   * @param Coordinate
   * @return the type of ship at that location
   */
  public T whatIsAtForSelf(Coordinate where) {
    //return whatIsAt(where, true);
    for (Ship<T> s: myShips) {
        if (s.occupiesCoordinates(where)){
          return s.getDisplayInfoAt(where, true);
        }
    }
    return null;
  }
  /**
   * Find what is at a Coordinate for enemy
   * 
   * @param Coordinate
   * @return the type of ship at that location
   */
  public T whatIsAtForEnemy(Coordinate where) {
    if (enemyHits.containsKey(where))
      return enemyHits.get(where);
    else if (enemyMisses.contains(where))
      return missInfo;
    else
      return null;
    //return whatIsAt(where, false);
  }
  /**
   * Find what is at a Coordinate
   * 
   * @param Coordinate, boolean itself
   * @return the type of ship at that location
   */
  public Ship<T> getShipAt(Coordinate where) {
    for (Ship<T> s: myShips) {
      if (s.occupiesCoordinates(where)) {
        return s;
      }
    }
    return null;
  }
/**
   * Find what is at a Coordinate
   * 
   * @param Coordinate, boolean itself
   * @return the type of ship at that location
   */
  /**
  protected T whatIsAt(Coordinate where, boolean itSelf) {
    if(itSelf) {
      for (Ship<T> s: myShips) {
        if (s.occupiesCoordinates(where)){
          return s.getDisplayInfoAt(where, itSelf);
        }
      }
    }
    else {
      if (enemyMisses.contains(where)) {
        return missInfo;
      }
      for (Ship<T> s: myShips) {
        if (s.occupiesCoordinates(where)){
          if (s.wasHitAt(where))
            return s.getDisplayInfoAt(where, false);

        }
      }
    }
    return null;
  }
  */
  /**
   * To check if the player is LOST
   * 
   * @param 
   * @return true if all the ships are Sunk, false if not
   */
  public boolean isThePlayerLost() {
    for (Ship<T> s: myShips) {
      if (!s.isSunk())
        return false;
    }
    return true;
  }
}
