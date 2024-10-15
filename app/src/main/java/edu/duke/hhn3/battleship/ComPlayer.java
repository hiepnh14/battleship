package edu.duke.hhn3.battleship;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Random;
/**
 * @description: this Computer Player class inherits from Text Player class
 * to represent a computer that plays the game vs human or another computer
 */
public class ComPlayer extends TextPlayer {
  private PrintStream computerOut;
  /**
   * to construct {@link ComPlayer}, set the {@link PrintStream} output to computerOut,
   * instead of same out stream as TextPlayer since we don't need to show the board and request for computer
   */ 
  public ComPlayer(String name, Board<Character> theBoard, BufferedReader placementSetup, PrintStream out, AbstractShipFactory<Character> shipFactory) {
    super(name, theBoard, placementSetup, new PrintStream(new ByteArrayOutputStream(), true), shipFactory);
    this.computerOut = out;
  }
  /**
   * For the computer to play one turn
   * the computer only fires, it cannot use move or scan functions
   */
  @Override
  public void playOneTurn(String enemyName, Board<Character> enemyBoard, BoardTextView enemyTextView) throws IOException {
   fireTurn(enemyName, enemyBoard, enemyTextView);
  }
  /**
   * Fucntion to randomly generate a Coordinate for the computer to fire
   * @param: none
   * @return: {@link Coordinate} to fire
   */
  private Coordinate randomCoordinate() {
    Random random = new Random();
    int row = random.nextInt(theBoard.getHeight());
    int column = random.nextInt(theBoard.getWidth());
    return new Coordinate(row, column);
  }
  /**
   * Play fire turn: take in the enemy's Board, print what is showing to other player
   * 
   * @param enemy's {@link Board}, {@link BoardTextView}
   * @return None
   */
  @Override
  protected void fireTurn(String enemyName, Board<Character> enemyBoard, BoardTextView enemyTextView) throws IOException {
    computerOut.print("---------------------------------------------------------------------------\n" +
              name + "'s turn:\n");
        
    Coordinate attackingCoordinate = randomCoordinate();
    
    Ship<Character> ship = enemyBoard.fireAt(attackingCoordinate); 
    if (ship != null) {
      computerOut.print("---------------------------------------------------------------------------\n" + 
                "The " + name + " hit your " + ship.getName() + " at " + attackingCoordinate.toString() + ".\n" +
                "---------------------------------------------------------------------------\n");
    }
    else
      computerOut.print("---------------------------------------------------------------------------\n" +
                "The " + name + " missed at " + attackingCoordinate.toString() + "!\n" +
                "---------------------------------------------------------------------------\n");
                
  }
  
  

}
