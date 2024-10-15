package edu.duke.hhn3.battleship;

/**
 * Placement class, which contains
 * Coordinate 
 * and orientation
 */
public class Placement {
  final Coordinate where;
  final char orientation;

  /**
   * Construct Placement from the Coordinate and orientation
   * @param Coordinate where
   * @param char orientation
   */
  public Placement(Coordinate where, char orientation) {
    this.where = where;
    this.orientation = Character.toUpperCase(orientation);
  }
  /**
   * @description: Construct Placement from String descriptor
   * @param: String descriptor (3 chars, 2 coordinate, 1 orientation)
   * @return:
   **/
   public Placement(String descr) {
    if (descr.length() != 3) {
      throw new IllegalArgumentException("Length of Descriptor for Placement must be 3");
    }
    descr = descr.toUpperCase();
    String coordinate = descr.substring(0, 2);
    char orient = descr.charAt(2);
    if(orient != 'H' && orient != 'V' && orient != 'U' && orient != 'R' && orient != 'L' && orient != 'D'){
      throw new IllegalArgumentException("Placement's orientation must be V/H or U/R/D/L\n for Carrier or BattleShip");
    }

    this.where = new Coordinate(coordinate);
    this.orientation = orient;
  }
  // Getter
  public Coordinate getWhere() {
    return where;
  }
  public char getOrientation() {
    return orientation;
  }
  /**
   * Check if the passed Object has the same class and value with this
   * @param Object o
   * @return Boolean if it is equal to this
   */
  @Override
  public boolean equals(Object o) {
      if (o.getClass().equals(getClass())) {
          Placement p = (Placement) o;
          return where.equals(p.where) && orientation == p.orientation;
      }
      return false;
  }
  /**
     * @Description: Describe the placement
     * @Param:
     * @return:      Placement description
     **/

  @Override
  public String toString() {
    return "(" + where.toString() + ", " + orientation + ")";
  }
/**
     * @Description: Find the hashcode of this.toString()
     * @Param:
     * @return:      Int of hashcode
     **/

  @Override
  public int hashCode() {
    return toString().hashCode();
  }
  

}
