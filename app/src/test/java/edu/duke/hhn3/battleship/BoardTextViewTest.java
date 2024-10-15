package edu.duke.hhn3.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody) {
    Board<Character> b1 = new BattleShipBoard<Character>(w, h, 'X');
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
    assertEquals(expected, view.displayEnemyBoard());
  }

  @Test
  public void test_display_empty_2by2() {
    Board<Character> b1 = new BattleShipBoard<Character>(2, 2, 'X');
    BoardTextView view = new BoardTextView(b1);
    String expectedHeader = "  0|1\n";
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader +
        "A  |  A\n" +
        "B  |  B\n" +
        expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  @Test
  public void test_invalid_board_size() {
    Board<Character> wideBoard = new BattleShipBoard<Character>(11, 20, 'X');
    Board<Character> tallBoard = new BattleShipBoard<Character>(10, 27, 'X');
    // you should write two assertThrows here
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
  }

  @Test
  public void test_display_empty_3by5() {
    String expectedHeader = "  0|1|2\n";
    String expectedBody = "A  | |  A\n"
        + "B  | |  B\n"
        + "C  | |  C\n"
        + "D  | |  D\n"
        + "E  | |  E\n";
    emptyBoardHelper(3, 5, expectedHeader, expectedBody);

  }

  @Test
  public void test_display_empty_3by2() {
    String expectedHeader = "  0|1|2\n";
    String expectedBody = "A  | |  A\n"
        + "B  | |  B\n";
    emptyBoardHelper(3, 2, expectedHeader, expectedBody);
  }
  @Test
  public void test_display_2_boards() {
    String header0 = "    Your ocean" + " ".repeat(2*3+19-10) + "Player B's ocean\n";
    String expectedHeader = "  0|1|2" + " ".repeat(19) + "  0|1|2\n";
    String expectedBody = "A  | |  A" + " ".repeat(17) +"A  | |  A\n"
      + "B  | |  B" + " ".repeat(17) +"B  | |  B\n";
    Board<Character> b1 = new BattleShipBoard<Character>(3, 2, 'X');
    BoardTextView view = new BoardTextView(b1);
    assertEquals(header0 + expectedHeader + expectedBody + expectedHeader, view.displayMyBoardWithEnemyNextToIt(view, "Your ocean", "Player B's ocean"));
    
  }
  @Test
  public void test_display_3by2() {
    String expectedHeader = "  0|1|2\n";
    String expectedBody = "A  |s|  A\n"
        + "B  | |  B\n";
    
    Board<Character> b1 = new BattleShipBoard<Character>(3, 2, 'X');
    b1.tryAddShip(new RectangleShip<Character>(new Coordinate(0, 1), 's', '*'));
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());

  }
  @Test
  public void test_display_3by4() {
    String expectedHeader = "  0|1|2|3\n";
    String expectedBody = "A  |s| |  A\n"
        + "B  | | |  B\n"
        + "C  | | |s C\n";
    
    Board<Character> b1 = new BattleShipBoard<Character>(4, 3, 'X');
    b1.tryAddShip(new RectangleShip<Character>(new Coordinate(0, 1), 's', '*'));
    b1.tryAddShip(new RectangleShip<Character>(new Coordinate(2, 3), 's', '*'));
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());

  }

}
