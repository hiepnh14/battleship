package edu.duke.hhn3.battleship;
/**
 * This class handles textual display of
 * a Board (i.e., converting it to a string to show
 * to the user).
 * It supports two ways to display the Board:
 * one for the player's own board, and one for the 
 * enemy's board.
 */
import java.util.function.Function;
public class BoardTextView {
  /**
   * The Board to display
   */
  private final Board<Character> toDisplay;
  /**
   * Constructs a BoardView, given the board it will display.
   * @param toDisplay is the Board to display
   * @throws IllegalArgumentException if the board is larger than 10x26
   */
  public BoardTextView(Board<Character> toDisplay) {
    this.toDisplay = toDisplay;
    if (toDisplay.getWidth() > 10 || toDisplay.getHeight() > 26) {
      throw new IllegalArgumentException(
                                         "Board must be no larger than 10x26, but is "+ toDisplay.getWidth() + "x" + toDisplay.getHeight());
    }
  }
  /**
   * @description: to display the board with ships
   * @param:
   * @return: String the board in text view
   **/
  public String displayMyOwnBoard() {
    return displayAnyBoard((c) -> toDisplay.whatIsAtForSelf(c));
  }
  public String displayEnemyBoard() {
    return displayAnyBoard((c) -> toDisplay.whatIsAtForEnemy(c));
  }
   protected String displayAnyBoard(Function<Coordinate, Character> getSquareFn) {
     StringBuilder ans = new StringBuilder("");
     String sep = "|";
     ans.append(makeHeader());
     for (int row = 0; row < toDisplay.getHeight(); row++) {
       StringBuilder row_string = new StringBuilder("");
       char numRow = (char) (row + 'A');
       // header of a row
       row_string.append(numRow);
       row_string.append(" ");
       // body
       for (int column = 0; column < toDisplay.getWidth(); column++) {
         if (column != 0) {
           row_string.append(sep);           
         }
         if (getSquareFn.apply(new Coordinate(row, column)) != null) {
           row_string.append(getSquareFn.apply(new Coordinate(row, column)));
           }
         else
           row_string.append(" ");
       }
       // tail
       row_string.append(" ");
       row_string.append(numRow);
       row_string.append("\n");
       ans.append(row_string);
     }
     ans.append(makeHeader());
     return ans.toString(); //this is a placeholder for the moment
 }
   /**
   * This makes the header line, e.g. 0|1|2|3|4\n
   * 
   * @return the String that is the header line for the given board
   */
  String makeHeader() {
    StringBuilder ans = new StringBuilder("  "); // README shows two spaces at
    String sep=""; //start with nothing to separate, then switch to | to separate
    for (int i = 0; i < toDisplay.getWidth(); i++) {
      ans.append(sep);
      ans.append(i);
      sep = "|";
    }
    ans.append("\n");
    return ans.toString();
  }
  public String displayMyBoardWithEnemyNextToIt(BoardTextView enemyView, String myHeader, String enemyHeader) {
    StringBuilder ans = new StringBuilder("");
    ans.append(" ".repeat(4)).append(myHeader);
    ans.append(" ".repeat(2*toDisplay.getWidth() + 19 - myHeader.length()));
    ans.append(enemyHeader);
    ans.append("\n");
    String myString = displayMyOwnBoard();
    String enemyString = enemyView.displayEnemyBoard();
    String [] myLines = myString.split("\n");
    String [] enemyLines = enemyString.split("\n");

    for (int i = 0; i < myLines.length; i++) {
      ans.append(myLines[i]);
      if (i == 0 || i == myLines.length - 1) {
        ans.append(" ".repeat(19));
      }
      else
        ans.append(" ".repeat(17));
      
      ans.append(enemyLines[i]).append("\n");
    }
    return ans.toString();
  }
}
