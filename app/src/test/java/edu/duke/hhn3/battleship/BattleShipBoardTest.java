
package edu.duke.hhn3.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {
  private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expected){
    for (int i = 0; i < b.getHeight(); i++) {
      for (int j = 0; j < b.getWidth(); j++) {
        assertEquals(b.whatIsAtForSelf(new Coordinate(i, j)), expected[i][j]);
      }
    }
  }
  @Test
  public void test_width_and_height() {
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }
    @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20, 'X'));
  }
  private void test_ships(int width, int height) {
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(width, height, 'X');
    Character[][] expected = new Character[height][width];
    for(int i = 0; i < b.getHeight(); i++) {
      Arrays.fill(expected[i], null);
    }
    checkWhatIsAtBoard(b, expected);
    for (int i = 0; i < b.getHeight(); i++) {
      for (int j = 0; j < b.getWidth(); j++) {
        b.tryAddShip(new RectangleShip<Character>(new Coordinate(i, j), 's', '*'));
        expected[i][j] = 's';
        checkWhatIsAtBoard(b, expected);
      }
    }
    assertEquals(b.tryAddShip(new RectangleShip<Character>(new Coordinate(-1, 0), 's', '*')), "That placement is invalid: the ship goes off the top of the board.");
    assertEquals(b.tryAddShip(new RectangleShip<Character>(new Coordinate(0, 0), 's', '*')), "That placement is invalid: the ship overlaps another ship.");
  }
  @Test
  public void test_addingShip() {
    test_ships(3, 5);
    test_ships(1, 2);
  }

  @Test
  public void test_fireAt() {
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
    RectangleShip<Character> ship = new RectangleShip<Character>(new Coordinate(1, 1), 's', '*');
    RectangleShip<Character> ship2 = new RectangleShip<Character>(new Coordinate(1, 1), 's', '*');
    b.tryAddShip(ship);
    assertNull(b.fireAt(new Coordinate(2, 1)));
    assertSame(ship, b.fireAt(new Coordinate(1,1)));
    HashSet<Coordinate> set = new HashSet<>();
    set.add(new Coordinate(2,1));
    assertEquals(set, b.getEnemyMisses());
    assertTrue(ship.isSunk());
    assertTrue(b.removeShip(ship));
    assertFalse(b.removeShip(ship2));
  }
  @Test
  public void test_whatIsAtForEnemy() {
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
    RectangleShip<Character> ship = new RectangleShip<Character>(new Coordinate(1, 1), 's', '*');
    RectangleShip<Character> ship2 = new RectangleShip<Character>(new Coordinate(2, 1), 's', '*');
    b.tryAddShip(ship);
    b.tryAddShip(ship2);
    assertSame(ship, b.getShipAt(new Coordinate(1,1)));
    assertNull(b.getShipAt(new Coordinate (20,20)));
    b.fireAt(new Coordinate(10,10));
    b.fireAt(new Coordinate(1,1));
    assertFalse(b.isThePlayerLost());
    assertEquals('X', b.whatIsAtForEnemy(new Coordinate(10,10)));
    assertTrue(b.getEnemyMisses().contains(new Coordinate(10,10)));
    assertEquals('s', b.whatIsAtForEnemy(new Coordinate(1, 1)));
    assertEquals('*', b.whatIsAtForSelf(new Coordinate(1, 1)));
    assertNull(b.whatIsAtForEnemy(new Coordinate(3,1)));
    assertNull(b.whatIsAtForEnemy(new Coordinate(2,1)));
    b.fireAt(new Coordinate(2,1));
    assertTrue(b.isThePlayerLost());
    assertFalse(b.getEnemyMisses().contains(new Coordinate(2,1)));
    assertEquals('s', b.whatIsAtForEnemy(new Coordinate(2,1)));
    assertEquals('s', ship2.getDisplayInfoAt(new Coordinate(2,1), false));
  }
}
