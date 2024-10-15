package edu.duke.hhn3.battleship;
/**
   * Class BasicShip that has Ship<T> as interface
   * must be part of the ship.
   * 
   * @param Coordinate where is the coordinate to return information
   */

import java.util.HashMap;

public abstract class BasicShip<T> implements Ship<T> {

  protected final HashMap<Coordinate, Boolean> myPieces;
  protected final HashMap<Integer, Coordinate> myOrderedPieces;
  protected ShipDisplayInfo<T> myDisplayInfo;    // <character, *>
  protected ShipDisplayInfo<T> enemyDisplayInfo;  //<null, character>
  /** 
   * @description: construct {@link BasicShip}
   * @param: Iterable<Coordinate>
   * @return: None
   **/
  public BasicShip(HashMap<Integer, Coordinate> orderedPieces, ShipDisplayInfo<T> myDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
    this.myOrderedPieces = orderedPieces;
    myPieces = new HashMap<>();
    for (Coordinate c : orderedPieces.values()) {
      myPieces.put(c, false);
    }
    this.myDisplayInfo = myDisplayInfo;
    this.enemyDisplayInfo = enemyDisplayInfo;
  }
  
  /**
   * @description: check coornidate in this ship
   * @param: {@link Coordinate}
   */
  protected void checkCoordinateInThisShip(Coordinate c) {
    if (!myPieces.containsKey(c)) {
      throw new IllegalArgumentException("Coordinate not part of this ship");
    }
  }
  /**
   * @description: to check if the ship is occupied at a {@link Coordinate}
   * @param: {@link Coordinate} where
   * @return: {@link Boolean} true if occupies
   */
  @Override
  public boolean occupiesCoordinates(Coordinate where) {
    
    return myPieces.containsKey(where);
  }

  @Override
  public boolean isSunk() {
    for (boolean isHit: myPieces.values()) {
      if (!isHit) {
        return false;
      }
    }
    return true;
  }

  @Override
  public void recordHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    myPieces.put(where, true);
  }

  @Override
  public boolean wasHitAt(Coordinate where) {
    checkCoordinateInThisShip(where);
    return myPieces.get(where);
  }
  /**
   * @description: display the info at a coordinate
   * @param: Coordinate
   * @return: char: type of the ship
   **/
  @Override
  public T getDisplayInfoAt(Coordinate where, boolean myShip) {
    if (myShip){
      if (this.wasHitAt(where))
        return myDisplayInfo.getInfo(where, true);
      return myDisplayInfo.getInfo(where, false);
    }
    else
      if (this.wasHitAt(where))
        return enemyDisplayInfo.getInfo(where, true);
      else
        return enemyDisplayInfo.getInfo(where, false);
  }

   /**
   * Get all of the Coordinates that this Ship occupies.
   * @return An Iterable with the coordinates that this Ship occupies
   */
  public Iterable<Coordinate> getCoordinates(){
    return myPieces.keySet();
  }
  
   /**
   * Get all of the information of damage of pieces that this Ship occupies.
   * @return A HashMap with the coordinates that this Ship occupies
   */
  @Override
  public HashMap<Coordinate, Boolean> getMyPieces() {
    return myPieces;
  }
  @Override
  public HashMap<Integer, Coordinate> getMyOrderedPieces() {
    return myOrderedPieces;
  }
  @Override
  public ShipDisplayInfo<T> getMyDisplayInfo() {
    return myDisplayInfo;
  }
  @Override
  public T getMyType() {
    return myDisplayInfo.getMyData();
  }
  /**
   * To transfer the damage from the moved Ship to a new one.
   * @param: Ship<T>
   * @return: 
   */
  @Override
  public void transferDamage(Ship<T> oldShip) {
    for (int key: myOrderedPieces.keySet()) {
      Coordinate c = myOrderedPieces.get(key);
      Coordinate c1 = oldShip.getMyOrderedPieces().get(key);
      myPieces.put(c, (oldShip.getMyPieces()).get(c1));
    }
  }
  
}
