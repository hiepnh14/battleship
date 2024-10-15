package edu.duke.hhn3.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SimpleShipDisplayInfoTest {
  @Test
  public void test_getInfo() {
    // Test case 1: Ship not hit
        SimpleShipDisplayInfo<String> shipInfo1 = new SimpleShipDisplayInfo<>("U", "X");
        Coordinate where1 = new Coordinate(1, 1);
        boolean hit1 = false;
        assertEquals("U", shipInfo1.getInfo(where1, hit1));
        assertEquals("U", shipInfo1.getMyData());

        // Test case 2: Ship hit
        SimpleShipDisplayInfo<Integer> shipInfo2 = new SimpleShipDisplayInfo<>(0, 1);
        Coordinate where2 = new Coordinate(2, 2);
        boolean hit2 = true;
        assertEquals(1, shipInfo2.getInfo(where2, hit2));
  }

}
