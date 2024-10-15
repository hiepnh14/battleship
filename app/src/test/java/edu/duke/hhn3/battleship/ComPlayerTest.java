package edu.duke.hhn3.battleship;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class ComPlayerTest {
  @Test
  public void test_doPlacementPhase() throws IOException {
    StringReader sr = new StringReader("a0v\na1\na1v\na2v\na3v\na4v\na5u\na7u\nc0u\nd2u\na8l");
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(bytes, true);
    Board<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
    
    Player computer = new ComPlayer("Computer", b, new BufferedReader(sr), ps, new V2ShipFactory());
    String boardView = "  0|1|2|3|4|5|6|7|8|9\n" +
"A s|s|d|d|d|c| |c| |b A\n"+
"B s|s|d|d|d|c| |c|b|b B\n"+
"C  |b|d|d|d|c|c|c|c|b C\n"+
"D b|b|b|b| |c|c|c|c|  D\n"+
"E  | |b|b|b| |c| |c|  E\n"+
"F  | | | | | | | | |  F\n"+
"G  | | | | | | | | |  G\n"+
"H  | | | | | | | | |  H\n"+
"I  | | | | | | | | |  I\n"+
"J  | | | | | | | | |  J\n"+
"K  | | | | | | | | |  K\n"+
"L  | | | | | | | | |  L\n"+
"M  | | | | | | | | |  M\n"+
"N  | | | | | | | | |  N\n"+
"O  | | | | | | | | |  O\n"+
"P  | | | | | | | | |  P\n"+
"Q  | | | | | | | | |  Q\n"+
"R  | | | | | | | | |  R\n"+
"S  | | | | | | | | |  S\n"+
"T  | | | | | | | | |  T\n"+
      "  0|1|2|3|4|5|6|7|8|9\n";
    computer.doPlacementPhase();
    assertEquals(boardView, (new BoardTextView(b)).displayMyOwnBoard());
    assertDoesNotThrow(() -> computer.playOneTurn("A", b, new BoardTextView(b)));
    assertDoesNotThrow(() -> computer.playOneTurn("A", b, new BoardTextView(b)));
    assertDoesNotThrow(() -> computer.playOneTurn("A", b, new BoardTextView(b)));
    assertDoesNotThrow(() -> computer.playOneTurn("A", b, new BoardTextView(b)));
    assertDoesNotThrow(() -> computer.playOneTurn("A", b, new BoardTextView(b)));
    assertDoesNotThrow(() -> computer.playOneTurn("A", b, new BoardTextView(b)));
  }
  

}
