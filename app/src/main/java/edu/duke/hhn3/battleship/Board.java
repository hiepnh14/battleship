package edu.duke.hhn3.battleship;

import java.util.HashSet;

public interface Board<T> {
  public Ship<T> getShipAt(Coordinate where);
  
  public int getWidth();

  public int getHeight();

  public HashSet<Coordinate> getEnemyMisses();

  public Ship<T> fireAt(Coordinate c);

  public T whatIsAtForSelf(Coordinate where);

  public T whatIsAtForEnemy(Coordinate where);
  
  public String tryAddShip(Ship<T> toAdd);
  
  public Boolean removeShip(Ship<T> ship);
  
  public boolean isThePlayerLost();
}
