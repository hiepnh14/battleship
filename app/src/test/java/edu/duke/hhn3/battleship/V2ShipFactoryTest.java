package edu.duke.hhn3.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V2ShipFactoryTest {
  private void checkShip(Ship<Character> testShip, String expectedName, char expectedLetter, Coordinate... expectedLocs) {
    assertEquals(testShip.getName(), expectedName);
    for (Coordinate expectedLoc : expectedLocs) {
      assertTrue(testShip.occupiesCoordinates(expectedLoc));
      assertEquals(testShip.getDisplayInfoAt(expectedLoc, true), expectedLetter);
    }
  }
  @Test
  public void test_makeShips() {
     V2ShipFactory f = new V2ShipFactory();
     Placement v1_2 = new Placement(new Coordinate(1, 2), 'V');
     Ship<Character> dst = f.makeDestroyer(v1_2);
     checkShip(dst, "Destroyer", 'd', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2));
     checkShip(f.makeShipFromType('d', v1_2), "Destroyer", 'd', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2));
     Placement v1_3 = new Placement(new Coordinate(1, 2), 'H');
     Ship<Character> dst2 = f.makeDestroyer(v1_3);
     checkShip(dst2, "Destroyer", 'd', new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(1, 4));

     Placement v1_4 = new Placement(new Coordinate(1, 2), 'V');
     Ship<Character> sub = f.makeSubmarine(v1_4);
     checkShip(sub, "Submarine", 's', new Coordinate(1, 2), new Coordinate(2, 2));
     checkShip(f.makeShipFromType('s', v1_4), "Submarine", 's', new Coordinate(1, 2), new Coordinate(2, 2));

     Placement v1_40 = new Placement(new Coordinate(1, 2), 'V');
     assertThrows(IllegalArgumentException.class, () -> f.makeCarrier(v1_40));

     Placement v1_41 = new Placement(new Coordinate(1, 2), 'U');
     assertThrows(IllegalArgumentException.class, () -> f.makeSubmarine(v1_41));

     
     Ship<Character> bts = f.makeBattleship(new Placement("A0U"));
     checkShip(bts, "Battleship", 'b', new Coordinate(0, 1), new Coordinate(1, 0), new Coordinate(1, 1), new Coordinate(1,2));
     checkShip(f.makeShipFromType('b', new Placement("A0U")), "Battleship", 'b', new Coordinate(0, 1), new Coordinate(1, 0), new Coordinate(1, 1), new Coordinate(1,2));

     Ship<Character> bts1 = f.makeBattleship(new Placement("A0R"));
     checkShip(bts1, "Battleship", 'b', new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(2, 0), new Coordinate(1,1));

     Ship<Character> bts2 = f.makeBattleship(new Placement("A0D"));
     checkShip(bts2, "Battleship", 'b', new Coordinate(0, 1), new Coordinate(0, 0), new Coordinate(0, 2), new Coordinate(1,1));

     Ship<Character> bts3 = f.makeBattleship(new Placement("A0L"));
     checkShip(bts3, "Battleship", 'b', new Coordinate(0, 1), new Coordinate(1, 0), new Coordinate(1, 1), new Coordinate(2,1));
     
     Ship<Character> crr = f.makeCarrier(new Placement("A0U"));
     checkShip(crr, "Carrier", 'c', new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(2, 0), new Coordinate(3,0), new Coordinate(2,1), new Coordinate(3,1), new Coordinate(4,1));
     checkShip(f.makeShipFromType('c', new Placement("A0U")), "Carrier", 'c', new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(2, 0), new Coordinate(3,0), new Coordinate(2,1), new Coordinate(3,1), new Coordinate(4,1));

     Ship<Character> crr1 = f.makeCarrier(new Placement("A0R"));
     checkShip(crr1, "Carrier", 'c', new Coordinate(0, 1), new Coordinate(0, 2), new Coordinate(0, 3), new Coordinate(0,4), new Coordinate(1,0), new Coordinate(1,1), new Coordinate(1,2));

     Ship<Character> crr2 = f.makeCarrier(new Placement("A0D"));
     checkShip(crr2, "Carrier", 'c', new Coordinate(0, 0), new Coordinate(1, 0), new Coordinate(2, 0), new Coordinate(1,1), new Coordinate(2,1), new Coordinate(3,1), new Coordinate(4,1));
     
     Ship<Character> crr3 = f.makeCarrier(new Placement("A0L"));
     checkShip(crr3, "Carrier", 'c', new Coordinate(1, 3), new Coordinate(0, 2), new Coordinate(0, 3), new Coordinate(0,4), new Coordinate(1,0), new Coordinate(1,1), new Coordinate(1,2));

     assertThrows(IllegalArgumentException.class, () -> new NonRectangleShip("Test", new Coordinate(1,0), 'L', 'p', '*'));
     assertThrows(IllegalArgumentException.class, () -> f.makeShipFromType('n', v1_41));
  }

}
