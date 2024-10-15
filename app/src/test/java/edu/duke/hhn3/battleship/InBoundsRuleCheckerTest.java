package edu.duke.hhn3.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {
  @Test
  public void test_checkMyRule() {
    RectangleShip<Character> ship = new RectangleShip<Character>("Test", new Coordinate(1, 1), 1, 1, 's', '*');
    RectangleShip<Character> ship2 = new RectangleShip<Character>("right", new Coordinate(1, 100), 1, 4, 's', '*');
    RectangleShip<Character> ship3 = new RectangleShip<Character>("left", new Coordinate(1, -1), 1, 4, 's', '*');
    RectangleShip<Character> ship4 = new RectangleShip<Character>("top", new Coordinate(-1, 1), 1, 4, 's', '*');
    RectangleShip<Character> ship5 = new RectangleShip<Character>("bottom", new Coordinate(60, 1), 1, 4, 's', '*');
    
    InBoundsRuleChecker<Character> rule = new InBoundsRuleChecker<>(null);
    InBoundsRuleChecker<Character> rule2 = new InBoundsRuleChecker<>(rule);
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(10, 10, 'X');
    String statement = "That placement is invalid: ";
    assertNull(rule.checkMyRule(ship, b));
    assertEquals(rule.checkMyRule(ship2, b), statement + "the ship goes off the right of the board.");
    assertNull(rule.checkPlacement(ship, b));
    assertEquals(rule.checkPlacement(ship2, b), statement + "the ship goes off the right of the board.");
    assertEquals(rule.checkPlacement(ship3, b), statement + "the ship goes off the left of the board.");
    assertEquals(rule.checkPlacement(ship4, b), statement + "the ship goes off the top of the board.");
    assertEquals(rule.checkPlacement(ship5, b), statement + "the ship goes off the bottom of the board.");
    
    assertNull(rule2.checkPlacement(ship, b));
    assertEquals(rule2.checkPlacement(ship2, b), statement + "the ship goes off the right of the board.");
  }
  //public viod test_checkRule() {
    //V1ShipFactory factory = new V1ShipFactory();

}
