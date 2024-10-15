package edu.duke.hhn3.battleship;

/**
 * @program: battleship
 * @description: this class is the rule to be checked
 */
public abstract class PlacementRuleChecker<T> {
  private final PlacementRuleChecker<T> next;

  /**
   * @description: construct a PlacementRuleChecker
   * @param: next
   */
  public PlacementRuleChecker(PlacementRuleChecker<T> next) {
    this.next = next;
  }
  /**
   * @description: to check the abstract rule from inherited classes
   * @param: Ship, Board
   * @return: true if valid, false if not valid
   */
  protected abstract String checkMyRule(Ship<T> theShip, Board<T> theBoard);

  /**
   * @description: to check all the rules from inherited classes
   * @param: Ship, Board
   * @return: true if valid, false if not valid
   */
  public String checkPlacement(Ship<T> theShip, Board<T> theBoard) {
    // if we fail our own rule: stopt he placement is not legal
    if (checkMyRule(theShip, theBoard) != null) {
      return checkMyRule(theShip, theBoard);
    }
    //otherwise, ask for the rest of the chain
    if (next != null) {
      return next.checkPlacement(theShip, theBoard);
    }
    //if there are no more rules, then the placement is legal
    return null;
  }
}
