package edu.duke.hhn3.battleship;

/**
 * Interface for SimpleShipDisplayInfo
 */
public interface ShipDisplayInfo<T> {
  public T getInfo(Coordinate where, boolean hit);
  public T getMyData();
}
