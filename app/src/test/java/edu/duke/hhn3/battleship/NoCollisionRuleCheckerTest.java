package edu.duke.hhn3.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NoCollisionRuleCheckerTest {
  @Test
  public void test_noCollision() {
    BattleShipBoard<Character> board = new BattleShipBoard<Character>(10, 10, 'X');
    V1ShipFactory factory = new V1ShipFactory();
    Placement p = new Placement(new Coordinate(2, 2), 'V');
    Ship<Character> carrier = factory.makeCarrier(p);
    NoCollisionRuleChecker<Character> rule = new NoCollisionRuleChecker<>(null);
    assertNull(rule.checkMyRule(carrier, board));
    board.tryAddShip(carrier);

    Ship<Character> submarine = factory.makeSubmarine(p);
    assertEquals(rule.checkMyRule(submarine, board), "That placement is invalid: the ship overlaps another ship.");

  }

}
