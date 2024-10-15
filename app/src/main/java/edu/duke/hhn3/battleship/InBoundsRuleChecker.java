package edu.duke.hhn3.battleship;
/**
 * @program: battleship
 * @class: InBoundRuleChecker to check if the ship is inside the board
 * extends from PlacementRuleChecker
 */
public class InBoundsRuleChecker<T> extends PlacementRuleChecker<T> {

  public InBoundsRuleChecker(PlacementRuleChecker<T> next) {
    super(next);
  }
  /**
   * @description: to check if all coordinate of the ship are inside the board
   * @param: Ship, Board
   * @return: true if inside, false if outside
   */
  @Override
  protected String checkMyRule(Ship<T> theShip, Board<T> theBoard) {
    // TODO Auto-generate method stub
    for (Coordinate coord : theShip.getCoordinates()) {
      if (coord.getRow() < 0)
        return "That placement is invalid: the ship goes off the top of the board.";
      if (coord.getRow() >= theBoard.getHeight())
        return "That placement is invalid: the ship goes off the bottom of the board.";
      if (coord.getColumn() < 0)
        return "That placement is invalid: the ship goes off the left of the board.";
      if (coord.getColumn() >= theBoard.getWidth())
        return "That placement is invalid: the ship goes off the right of the board.";
      
    }
    return null;
  }
}
