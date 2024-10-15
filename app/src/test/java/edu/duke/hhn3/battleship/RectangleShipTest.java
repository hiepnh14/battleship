package edu.duke.hhn3.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
public class RectangleShipTest {
  @Test
  public void test_makeCoords() {
    // Test case 1: Rectangle with width = 1, height = 3
    
    Coordinate upperLeft1 = new Coordinate(1, 2);
    int width1 = 1;
    int height1 = 3;
    RectangleShip<Character> ship1 = new RectangleShip<Character>("test", upperLeft1, width1, height1, 's', '*');
    Set<Coordinate> expectedCoords1 = Set.of(new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2));
    Set<Coordinate> expected = Set.of();
    HashMap<Integer, Coordinate> actualCoords1 = ship1.makeCoords(upperLeft1, width1, height1);
    assertEquals(expectedCoords1, new HashSet<Coordinate>(actualCoords1.values()));
    assertEquals(ship1.getName(), "test");

        // Test case 2: Rectangle with width = 2, height = 2
    Coordinate upperLeft2 = new Coordinate(3, 4);
    int width2 = 2;
    int height2 = 2;
    RectangleShip<Character> ship2 = new RectangleShip<Character>("testship", upperLeft2, width1, height2, 's', '*');
    Set<Coordinate> expectedCoords2 = Set.of(new Coordinate(3, 4), new Coordinate(3, 5), new Coordinate(4, 4), new Coordinate(4, 5));
    Collection<Coordinate> actualCoords2 = ship2.makeCoords(upperLeft2, width2, height2).values();
    assertEquals(expectedCoords2, new HashSet<>(actualCoords2));
    
    // Test case 3: Rectangle with width = 3, height = 1
    Coordinate upperLeft3 = new Coordinate(5, 6);
    int width3 = 3;
    int height3 = 1;
    RectangleShip<Character> ship3 = new RectangleShip<Character>("testship", upperLeft3, width3, height3, 's', '*');
    Set<Coordinate> expectedCoords3 = Set.of(new Coordinate(5, 6), new Coordinate(5, 7), new Coordinate(5, 8));
    Collection<Coordinate> actualCoords3 = ship3.makeCoords(upperLeft3, width3, height3).values();
    assertEquals(expectedCoords3, new HashSet<>(actualCoords3));

    // Test case 4: Rectangle with width = 0, height = 0 (a single point)
    Coordinate upperLeft4 = new Coordinate(0, 0);
    int width4 = 0;
    int height4 = 0;
    RectangleShip<Character> ship4 = new RectangleShip<Character>("testship", upperLeft4, width4, height4, 's', '*');
    Set<Coordinate> expectedCoords4 = Set.of();
    Collection<Coordinate> actualCoords4 = ship4.makeCoords(upperLeft4, width4, height4).values();
    assertEquals(expectedCoords4, new HashSet<>(actualCoords4));
  }
}
