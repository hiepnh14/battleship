package edu.duke.hhn3.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacementTest {
  @Test
  public void test_Placement() {
    Coordinate c1 = new Coordinate(1,2);
    Coordinate c2 = new Coordinate(1,2);
    Coordinate c3 = new Coordinate(2,2);
    Placement p3 = new Placement(c1, 'H');
    Placement p1 = new Placement(c1, 'v');
    Placement p2 = new Placement(c2, 'V');
    assertNotEquals(p2, p3);
    assertNotEquals(p2, new Placement(c3, 'V'));
    assertEquals(p1, p2);
    Placement p4 = new Placement("B2v");
    assertEquals(p4, p2);
    assertNotEquals(p4, c1);
    assertEquals(p1.toString(), "((1, 2), V)");
    assertEquals(p1.hashCode(), p2.hashCode());
  }
  @Test
  public void test_invalidPlacement() {
    assertThrows(IllegalArgumentException.class, () -> new Placement("eee"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A3i"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A3ie3"));
    
  }
  @Test
  public void test_getters() {
    Placement p1 = new Placement("A0v");
    assertEquals(new Coordinate("A0"), p1.getWhere());
    assertEquals('V', p1.getOrientation());
  }

}
