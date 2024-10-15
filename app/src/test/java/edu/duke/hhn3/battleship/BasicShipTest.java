package edu.duke.hhn3.battleship;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class BasicShipTest {
  @Test
  public void test_recordHitAt_IsSunk_wasHitAt() {
    RectangleShip<Character> ship = new RectangleShip<Character>(new Coordinate(1, 1), 's', '*');

    ship.recordHitAt(new Coordinate(1, 1));
    assertTrue(ship.wasHitAt(new Coordinate(1, 1)));

    assertThrows(IllegalArgumentException.class, () -> ship.recordHitAt(new Coordinate(2, 3)));
    assertTrue(ship.isSunk());
  }
  @Test void test_notIsSunk() {
    RectangleShip<Character> ship = new RectangleShip<Character>("test", new Coordinate(1, 1), 1, 2,'s', '*');
    ship.recordHitAt(new Coordinate(1,1));
    assertFalse(ship.isSunk());
  }

  @Test
  void test_getCoordinates() {
    RectangleShip<Character> ship = new RectangleShip<Character>("test", new Coordinate(1, 1), 1, 1,'s', '*');
    assertEquals(ship.getCoordinates(), Set.of(new Coordinate(1, 1)));
  }
  @Test
  void test_getDisplayInfo() {
    RectangleShip<Character> ship = new RectangleShip<Character>("test", new Coordinate(1, 1), 1, 1,'s', '*');
    assertNull(ship.getDisplayInfoAt(new Coordinate(1,1), false));
  }
  private void check_transferDamage(Ship<Character> ship1, Ship<Character> ship2) {
    for (int key: ship1.getMyOrderedPieces().keySet()) {
      Coordinate c = ship1.getMyOrderedPieces().get(key);
      Coordinate c2 = ship2.getMyOrderedPieces().get(key);
      assertEquals(ship1.getMyPieces().get(c), (ship2.getMyPieces()).get(c2));
    }
  }
  @Test
  void test_transferDamage() {
    V2ShipFactory factory = new V2ShipFactory();
    Board<Character> b = new BattleShipBoard<Character>(10, 20, 'X'); 
    Ship<Character> ship1 = factory.makeCarrier(new Placement("A0u"));
    Ship<Character> ship2 = factory.makeCarrier(new Placement("B2u"));

    b.tryAddShip(ship1);
    b.tryAddShip(ship2);
    b.fireAt(new Coordinate("A0"));
    b.fireAt(new Coordinate("C0"));
    assertDoesNotThrow(() -> ship2.transferDamage(ship1));
    check_transferDamage(ship2, ship1);
    assertEquals('c', ship1.getMyDisplayInfo().getInfo(new Coordinate("A0"), false));
  }
}
