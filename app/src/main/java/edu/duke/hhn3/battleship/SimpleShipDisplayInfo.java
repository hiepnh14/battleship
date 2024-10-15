package edu.duke.hhn3.battleship;

public class SimpleShipDisplayInfo<T> implements ShipDisplayInfo<T> {
  private T myData;
  private T onHit;

  /**
   * @description: construct SimpleshipDisplayInfo
   * @param: Ts myData and onHit
   **/
  public SimpleShipDisplayInfo(T myData, T onHit){
    this.myData = myData;
    this.onHit = onHit;
  }
   /**
   * @description: to get Info of the ship to display
   * @param: {@link Coordinate} where, {@link Boolean} hit (whether was hit or not)
   **/
  @Override
  public T getInfo(Coordinate where, boolean hit) {
    if (hit) {
      return onHit;
    }
    else {
      return myData;
      }
  }
  /**
   * @description: to get the data/type of the ship
   * @param: 
   **/
  public T getMyData() {
    return myData;
  }

}
