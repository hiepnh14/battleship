
package edu.duke.hhn3.battleship;
/**
 * Class NoCollisionRuleChecker, extends PlacementRuleChecker
 * to make false if there is a collision between Ships
 */
public class NoCollisionRuleChecker<T> extends PlacementRuleChecker<T> {

  public NoCollisionRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }
  /**
   * @description: to check whether the Ship is collapse with other ship on the Board
   * @param: Ship, Board
   * @return: true if no collision, false if collision
   */
  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    Iterable<Coordinate> coords = theShip.getCoordinates();
    for (Coordinate coord : coords) {
      if (theBoard.whatIsAtForSelf(coord) != null) {
        return "That placement is invalid: the ship overlaps another ship.";
      }
    }
    return null;
  }

}
