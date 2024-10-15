package edu.duke.hhn3.battleship;

import java.io.IOException;
/**
 * @description: interface for {@link ComPlayer} and {@link TextPlayer}
 */
public interface Player {

  public void playOneTurn(String enemyName, Board<Character> enemyBoard, BoardTextView enemyTextView) throws IOException;

  public void doPlacementPhase() throws IOException;

  public String getName();
}
