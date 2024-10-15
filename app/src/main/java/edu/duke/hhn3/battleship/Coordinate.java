package edu.duke.hhn3.battleship;

import java.io.EOFException;
import java.io.IOException;

/* Coordinate class
 * have private final int for row and for column
 */
public class Coordinate {
  private final int row;
  private final int column;
  // getters for both fields
  public  int getRow() {
    return row;
  }
  public int getColumn() {
    return column;
  }
  /* Construct Coordinate from int of row and column
   */
  public Coordinate(int row, int column) {
    this.row = row;
    this.column = column;
  }

  /**
   * @description: Construct Coordinate from String
   * @param: String descriptor (2 characters)
   */
  public Coordinate(String descr)  {
    if (descr == null )
      throw new NullPointerException("Null String or End of File, play again");
    if (descr.length() != 2) {
      throw new IllegalArgumentException("Input String must have a length of 2, but it is " + descr.length());
    }
    descr = descr.toUpperCase();
    int rowLetter = descr.charAt(0);
    int column = descr.charAt(1) - '0';
    if (rowLetter < 'A' || rowLetter > 'Z') {
      throw new IllegalArgumentException("Input character must be from A to Z, but it is " + rowLetter);
    }
    if (column < 0 || column > 9){
      throw new IllegalArgumentException("Input descriptor integer must be from 0 to 9");
    }
    this.row = rowLetter - 'A';
    this.column = column;
  }

  @Override
  public boolean equals(Object o) {
    if (o.getClass().equals(getClass())) {
      Coordinate c = (Coordinate) o;
      return row == c.row && column == c.column;
    }
    return false;
  }
  @Override
  public String toString() {
    return "("+row+", " + column+")";
  }
  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
