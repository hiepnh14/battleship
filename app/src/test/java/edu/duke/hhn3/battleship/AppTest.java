/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.hhn3.battleship;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;

class AppTest {
  //@Disabled
  @Test
  @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
  void test_main() throws IOException {
    test_main_helper("input.txt", "output.txt");
    test_main_helper("input2.txt", "output2.txt");
  }

  void test_main_helper(String inputFile, String outputFile) throws IOException {
    // Get input
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);
    // get an InputStream for the input.txt
    InputStream input = getClass().getClassLoader().getResourceAsStream(inputFile);
    assertNotNull(input);
    // As the ClassLoader to find input.txt and give us back an InputStream for it
    InputStream expectedStream = getClass().getClassLoader().getResourceAsStream(outputFile);
    assertNotNull(expectedStream);
    // Remember the current System.in and System.out
    InputStream oldIn = System.in;
    PrintStream oldOut = System.out;
    // Change to the new input from input.txt and output(printStream that writes into bytes and run App.main. Do try to ensure we restore System.in and System.out
    try {
      System.setIn(input);
      System.setOut(out);
      App.main(new String[0]);
    }
    finally {
      System.setIn(oldIn);
      System.setOut(oldOut);
    }
    String expected = new String(expectedStream.readAllBytes());
    String actual = bytes.toString();
    assertEquals(expected, actual);
  }
  @Test
  public void test_oneAttackingPhase() throws IOException {
    StringReader sr = new StringReader("\nB2h\nF\nB2\nF\nB3\nf\nB6\nf\nB4\nf\nb7\nf\nb8\nf\nc1\nf\nc2\nf\nc3");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(bytes, true);
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    StringReader sr2 = new StringReader("aa\nB2h\naa\nS\nF\nB2\nf\nB3\nf\nB6\nf\nB4\nf\nc1\nf\nc2\nf\nc3\nf\nc4\nf\nc6");
    Board<Character> b2 = new BattleShipBoard<Character>(10, 20, 'X');
    BoardTextView view1 = new BoardTextView(b1);
    BoardTextView view2 = new BoardTextView(b2);
    TextPlayer player1 = new TextPlayer("A", b1, new BufferedReader(sr), ps, new V1ShipFactory());
    TextPlayer player2 = new TextPlayer("B", b2, new BufferedReader(sr2), ps, new V1ShipFactory());
    player1.doOnePlacement("Destroyer", player1.shipCreationFns.get("Destroyer"));
    player2.doOnePlacement("Destroyer", player2.shipCreationFns.get("Destroyer"));
    App app = new App(player1, player2);

    app.doAttackingPhase(b1, b2, view1, view2);
    assertFalse(b1.isThePlayerLost());
    assertTrue(b2.isThePlayerLost());
  }
  @Test
  public void test_oneAttackingPhase2() throws IOException {
    StringReader sr = new StringReader("\nB2h\nF\nc2\nF\nc3\nF\nc6\nF\nF\nc4\nF\nb7\nF\nb8\nF\nc1\nF\nc2\nF\nc3");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(bytes, true);
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    StringReader sr2 = new StringReader("aa\nB2h\nF\nB2\nF\nB3\nF\nB6\nF\nB4\nS\nc1\nF\nc2\nF\nc3\nF\nc4\nF\nc6\nF");
    Board<Character> b2 = new BattleShipBoard<Character>(10, 20, 'X');
    BoardTextView view1 = new BoardTextView(b1);
    BoardTextView view2 = new BoardTextView(b2);
    TextPlayer player1 = new TextPlayer("A", b1, new BufferedReader(sr), ps, new V2ShipFactory());
    TextPlayer player2 = new TextPlayer("B", b2, new BufferedReader(sr2), ps, new V2ShipFactory());
    player1.doOnePlacement("Destroyer", player1.shipCreationFns.get("Destroyer"));
    player2.doOnePlacement("Destroyer", player2.shipCreationFns.get("Destroyer"));
    App app = new App(player1, player2);

    app.doAttackingPhase(b1, b2, view1, view2);
    assertTrue(b1.isThePlayerLost());
    assertFalse(b2.isThePlayerLost());
  }
  @Test
  public void test_setupPlayers() throws IOException {
    App app = new App();
    StringReader sr = new StringReader("C\np\nC");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(bytes, true);
    Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    Board<Character> b2 = new BattleShipBoard<Character>(10, 20, 'X');
    BoardTextView view1 = new BoardTextView(b1);
    BoardTextView view2 = new BoardTextView(b2);
    app.setupPlayers(b1, b2, view1, view2, new V2ShipFactory(), new BufferedReader(sr), ps);
    app.doPlacementPhase();
    app.doAttackingPhase(b1, b2, view1, view2);
    assertTrue(b1.isThePlayerLost() || b2.isThePlayerLost());
    assertEquals("Computer A", app.getFirstPlayerName());
    assertEquals("Computer B", app.getSecondPlayerName());
    App app2 = new App();
    StringReader sr2 = new StringReader("h\nh\na0v\na1\na1v\na2v\na3v\na4v\na5u\na7u\nc0u\nd2u\na8l\na0v\na1\na1v\na2v\na3v\na4v\na5u\na7u\nc0u\nd2u\na8l");
    app2.setupPlayers(b1, b2, view1, view2, new V2ShipFactory(), new BufferedReader(sr2), ps);
  }
    
}