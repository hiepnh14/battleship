package edu.duke.hhn3.battleship;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class TextPlayerTest {
  private TextPlayer createTextPlayer(int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
    V1ShipFactory shipFactory = new V1ShipFactory();
    return new TextPlayer("A", board, input, output, shipFactory);
  }
  @Test
   public void test_read_placement() throws IOException {
      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      TextPlayer player = createTextPlayer(10, 20, "B2V\nC8H\na4v\n", bytes);
      String prompt = "Please enter a location for a ship:";
      Placement[] expected = new Placement[3];
      expected[0] = new Placement(new Coordinate(1, 2), 'V');
      expected[1] = new Placement(new Coordinate(2, 8), 'H');
      expected[2] = new Placement(new Coordinate(0, 4), 'V');
      for (int i = 0; i < expected.length; i++) {
        Placement p = player.readPlacement(prompt);
        assertEquals(p, expected[i]); //did we get the right Placement back
        assertEquals(prompt + "\n", bytes.toString()); //should have printed prompt and newline
        bytes.reset(); //clear out bytes for next time around
      }
      TextPlayer player2 = createTextPlayer(10, 20, "\n", bytes);
      assertThrows(NullPointerException.class, () -> player2.readPlacement("Nothing"));
      assertThrows(NullPointerException.class, () -> player2.readCoordinate("Nothing"));
    }
  @Test
  void test_doOnePlacement() throws IOException {
      StringReader sr = new StringReader("B2V\nA9h");

      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      PrintStream ps = new PrintStream(bytes, true);
      Board<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
      TextPlayer player = new TextPlayer("A", b, new BufferedReader(sr), ps, new V1ShipFactory());
      String prompt = "Player A where do you want to place a Destroyer?";
      player.doOnePlacement("Destroyer", player.shipCreationFns.get("Destroyer"));
      assertEquals(b.whatIsAtForSelf(new Coordinate("B2")), 'd');
      //assertEquals(b.whatIsAt(new Coordinate("C8")), 's');
      //assertEquals(b.whatIsAt(new Coordinate("a4")), 's');
      assertEquals(prompt + "\n" + (new BoardTextView(b)).displayMyOwnBoard(), bytes.toString()); //should have printed prompt and newline
      bytes.reset(); //clear out bytes for next time around
      assertThrows(IllegalArgumentException.class, () -> player.doOnePlacement("Carrier",player.shipCreationFns.get("Carrier")));

    // Create a ByteArrayInputStream with the input
  }
  @Test
  void test_doOnePlacementPhase() throws IOException {
    String prompt = "--------------------------------------------------------------------------------\n" +

        "Player " + "A" + ": you are going to place the following ships (which are all\n" +
        "rectangular). For each ship, type the coordinate of the upper left\n" +
        "side of the ship, followed by either H (for horizontal) or V (for\n" +
        "vertical).  For example M4H would place a ship horizontally starting\n" +
        "at M4 and going to the right.  You have\n\n" + "2 \"Submarines\" ships that are 1x2\n"
        + "3 \"Destroyers\" that are 1x3\n" + "3 \"Battleships\" that are 1x4\n" + "2 \"Carriers\" that are 1x6\n"
        + "--------------------------------------------------------------------------------\n";
    StringReader sr = new StringReader("B2h\nA1h\nC1h\nD1h\nE1h\nF1h\nG2H\nH1h\nK1h\nL2h\nN4h");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(bytes, true);
    Board<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
    Board<Character> b3 = new BattleShipBoard<Character>(10, 20, 'X');
    Board<Character> b2 = new BattleShipBoard<Character>(10, 20, 'X');
    StringReader sr2 = new StringReader("B2h\nA1h\nC1h\nD1h\nE1h\nF1h\nG2H\nH1h\nK1h\nL2h\nN4h");
    String initialBoard = (new BoardTextView(b2)).displayMyOwnBoard();
    TextPlayer player = new TextPlayer("A", b, new BufferedReader(sr), ps, new V1ShipFactory());
    TextPlayer player2 = new TextPlayer("A", b2, new BufferedReader(sr2), ps, new V1ShipFactory());
    String checking = initialBoard + prompt;
    player.doPlacementPhase();
    String result = bytes.toString();
    bytes.reset();
    //assertEquals(b.whatIsAt(new Coordinate("B2")), 'd');
    for (String s: player.shipsToPlace) {
      player2.doOnePlacement(s, player2.shipCreationFns.get(s));
      checking = checking + "Player A where do you want to place a " + s + "?\n" + (new BoardTextView(b2)).displayMyOwnBoard();
    }
    
    assertEquals(checking, result);
    
    //should have printed prompt and newline
    StringReader sr3 = new StringReader("A1h\nH1h\nK1h\nL2h\nN4h");
    TextPlayer player3 = new TextPlayer("A", b3, new BufferedReader(sr3), ps, new V1ShipFactory());
    assertThrows(EOFException.class, () -> player3.doPlacementPhase());

    

  }
  @Test
  public void test_illegalArgumentException() {
    StringReader sr = new StringReader("B2h\nB2h\nA1h\nC1h\nD1h\nE1h\nF1h\nG2H\nH1h\nK1h\nL2h\nN4h");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(bytes, true);
    Board<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
    TextPlayer player = new TextPlayer("A", b, new BufferedReader(sr), ps, new V2ShipFactory());
    assertThrows(IllegalArgumentException.class, () -> player.doPlacementPhase());
  }
  @Test
    public void testReadCoordinateInvalidInput() throws IOException {
        Board<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
        String input = "invalid\n\n\n"; // simulate invalid input followed by valid input
        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(inContent));
        
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outContent);

        TextPlayer textPlayer = new TextPlayer("TestPlayer", b, inputReader, out, new V1ShipFactory());

        assertThrows(NullPointerException.class, () -> textPlayer.readCoordinate("Enter coordinate:"));
        assertThrows(NullPointerException.class, () -> textPlayer.readPlacement("Enter coordinate:"));
  }
  @Test
  public void test_sonarTurn() throws IOException {
      StringReader sr = new StringReader("B2V\nS\nB3\nB2\nZ0\n.8\nS\nB1\nS\nB0\nf\nb0");

      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      PrintStream ps = new PrintStream(bytes, true);
      Board<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
      TextPlayer player = new TextPlayer("A", b, new BufferedReader(sr), ps, new V1ShipFactory());
      player.doOnePlacement("Destroyer", player.shipCreationFns.get("Destroyer"));
      assertEquals(b.whatIsAtForSelf(new Coordinate("B2")), 'd');
      assertDoesNotThrow(() -> player.playOneTurn("A", b, new BoardTextView(b)));
      assertTrue(player.playSonarTurn(b));
      assertDoesNotThrow(() -> player.playOneTurn("A", b, new BoardTextView(b)));
      assertDoesNotThrow(() -> player.playOneTurn("A", b, new BoardTextView(b)));
      assertDoesNotThrow(() -> player.playOneTurn("A", b, new BoardTextView(b)));
      //assertThrows(IllegalArgumentException.class, () -> player.playSonarTurn(b));
  }
  @Test
  public void test_sonar_character_coverage1() {
    Ship<Character> s = new RectangleShip<Character>(new Coordinate("A0"), 's', '*');
    Ship<Character> c = new RectangleShip<Character>(new Coordinate("A1"), 'c', '*');
    Ship<Character> d = new RectangleShip<Character>(new Coordinate("A2"), 'd', '*');
    Ship<Character> b = new RectangleShip<Character>(new Coordinate("A3"), 'b', '*');
    Board<Character> board = new BattleShipBoard<Character>(10, 20, 'X');

    StringReader sr = new StringReader("B2V\nS\nA1\nS\nB1\nB1\nS\nB1\nS\nB1");

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(bytes, true);
    TextPlayer player = new TextPlayer("A", board, new BufferedReader(sr), ps, new V1ShipFactory());
    board.tryAddShip(d);
    board.tryAddShip(s);
    board.tryAddShip(b);
    board.tryAddShip(c);
    assertDoesNotThrow(() -> player.playOneTurn("A", board, new BoardTextView(board)));
    //player.playSonarTurn(board);
    //assertEquals(ps,);
    board.tryAddShip(new RectangleShip<Character>(new Coordinate("B0"), 's', '*'));
    board.tryAddShip(new RectangleShip<Character>(new Coordinate("B1"), 'd', '*'));
    board.tryAddShip(new RectangleShip<Character>(new Coordinate("B2"), 'c', '*'));
    board.tryAddShip(new RectangleShip<Character>(new Coordinate("c1"), 'b', '*'));
    assertDoesNotThrow(() -> player.playOneTurn("A", board, new BoardTextView(board)));
    board.tryAddShip(new RectangleShip<Character>(new Coordinate("c2"), 'm', '*'));
    assertThrows(IllegalArgumentException.class, () -> player.playOneTurn("A", board, new BoardTextView(board)));
  }
  @Test
  public void test_sonarTurn2() throws IOException, NullPointerException {
      StringReader sr = new StringReader("B2V\nS\nY3\nB2\nZ0\n.8\nS\nY1\nB1\nS\nB1");

      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      PrintStream ps = new PrintStream(bytes, true);
      Board<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
      TextPlayer player = new TextPlayer("A", b, new BufferedReader(sr), ps, new V1ShipFactory());
      player.doOnePlacement("Destroyer", player.shipCreationFns.get("Destroyer"));
      assertEquals(b.whatIsAtForSelf(new Coordinate("B2")), 'd');
      assertDoesNotThrow(() -> player.playOneTurn("A", b, new BoardTextView(b)));
      //assertDoesNotThrow(() -> player.playOneTurn("A", b, new BoardTextView(b)));
      //assertFalse(player.playSonarTurn(b));
      assertThrows(NullPointerException.class, () -> player.playSonarTurn(b));
  }
  @Test
  public void test_moveTurn() throws IOException {
      StringReader sr = new StringReader("B2V\nM\nB2\nB3V\n.8\nM\nB3\nB2v\nM\nb2\nb4v\ns\na0\ns\na0\ns\na1\nf\na0");

      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      PrintStream ps = new PrintStream(bytes, true);
      Board<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
      TextPlayer player = new TextPlayer("A", b, new BufferedReader(sr), ps, new V2ShipFactory());
      player.doOnePlacement("Destroyer", player.shipCreationFns.get("Destroyer"));
      //assertThrows(IllegalArgumentException.class, () -> player.playOneTurn("A", b, new BoardTextView(b)));
      assertDoesNotThrow(() -> player.playOneTurn("A", b, new BoardTextView(b)));
      assertDoesNotThrow(() -> player.playOneTurn("A", b, new BoardTextView(b)));
      assertDoesNotThrow(() -> player.playOneTurn("A", b, new BoardTextView(b)));
      assertDoesNotThrow(() -> player.playOneTurn("A", b, new BoardTextView(b)));
      assertDoesNotThrow(() -> player.playOneTurn("A", b, new BoardTextView(b)));
      assertDoesNotThrow(() -> player.playOneTurn("A", b, new BoardTextView(b)));
      assertDoesNotThrow(() -> player.playOneTurn("A", b, new BoardTextView(b)));
      //assertTrue(player.playSonarTurn(b));
      assertEquals(b.whatIsAtForSelf(new Coordinate("B4")), 'd');
      assertEquals(b.whatIsAtForSelf(new Coordinate("B2")), null);
      assertEquals(b.whatIsAtForSelf(new Coordinate("B2")), null);
      //assertThrows(IllegalArgumentException.class, () -> player.playSonarTurn(b));
  }
  @Test
  public void test_moveTurn2() throws IOException {
      StringReader sr = new StringReader("B2V\nC0V\nM\nx\nM\nA0\nM\nB2\nC0V\nM\nB2\nB3V\n.8");

      ByteArrayOutputStream bytes = new ByteArrayOutputStream();
      PrintStream ps = new PrintStream(bytes, true);
      Board<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
      TextPlayer player = new TextPlayer("A", b, new BufferedReader(sr), ps, new V2ShipFactory());
      player.doOnePlacement("Destroyer", player.shipCreationFns.get("Destroyer"));
      player.doOnePlacement("Destroyer", player.shipCreationFns.get("Destroyer"));
      //assertThrows(IllegalArgumentException.class, () -> player.playOneTurn("A", b, new BoardTextView(b)));
      assertDoesNotThrow(() -> player.playOneTurn("A", b, new BoardTextView(b)));
      //assertThrows(IllegalArgumentException.class, () -> player.playOneTurn("A", b, new BoardTextView(b)));
      //assertTrue(player.playSonarTurn(b));
      assertEquals(b.whatIsAtForSelf(new Coordinate("B3")), 'd');
      assertEquals(b.whatIsAtForSelf(new Coordinate("B2")), null);
   
  }
}
