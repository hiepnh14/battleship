package edu.duke.hhn3.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V1ShipFactoryTest {

  private void checkShip(Ship<Character> testShip, String expectedName, char expectedLetter, Coordinate... expectedLocs) {
    assertEquals(testShip.getName(), expectedName);
    for (Coordinate expectedLoc : expectedLocs) {
      assertTrue(testShip.occupiesCoordinates(expectedLoc));
      assertEquals(testShip.getDisplayInfoAt(expectedLoc, true), expectedLetter);
    }
  }
  @Test
  public void test_makeShips() {
    V1ShipFactory f = new V1ShipFactory();
     Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
     Ship<Character> dst = f.makeDestroyer(v1_2);
     checkShip(dst, "Destroyer", 'd', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2));

     Placement v1_3 = new Placement(new Coordinate(1, 2), 'H');
     Ship<Character> dst2 = f.makeDestroyer(v1_3);
     checkShip(dst2, "Destroyer", 'd', new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(1, 4));

     Placement v1_4 = new Placement(new Coordinate(1, 2), 'V');
     Ship<Character> sub = f.makeSubmarine(v1_4);
     checkShip(sub, "Submarine", 's', new Coordinate(1, 2), new Coordinate(2, 2));

     Placement v1_41 = new Placement(new Coordinate(1, 2), 'U');
     assertThrows(IllegalArgumentException.class, () -> f.makeSubmarine(v1_41));
     
     Ship<Character> bts = f.makeBattleship(v1_2);
     checkShip(bts, "Battleship", 'b', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2), new Coordinate(4,2));

     Ship<Character> crr = f.makeCarrier(v1_2);
     checkShip(crr, "Carrier", 'c', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2), new Coordinate(4,2), new Coordinate(5,2), new Coordinate(6,2));

  }

}
