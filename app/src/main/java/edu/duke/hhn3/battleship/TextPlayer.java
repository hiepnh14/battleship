package edu.duke.hhn3.battleship;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;
/**
 * @program: battleship
 * @class: TextPlayer
 */
public class TextPlayer implements Player {
  protected final Board<Character> theBoard;
  protected final BoardTextView view;
  protected final BufferedReader inputReader;
  protected final PrintStream out;
  protected final AbstractShipFactory<Character> shipFactory;
  protected final String name;
  final ArrayList<String> shipsToPlace;
  final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;
  protected int moveTurns;
  protected int sonarTurns;
  /**
   * @description" constructs {@link TextPlayer}
   * @param: name, Board, input, output, and shipFactory
   * @return: none
   */
  public TextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out, AbstractShipFactory<Character> shipFactory) {
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = inputSource;
    this.out = out;
    this.shipFactory = shipFactory;
    this.name = name;
    this.shipsToPlace = new ArrayList<>();
    this.shipCreationFns = new HashMap<>();
    this.moveTurns = 3;
    this.sonarTurns = 3;
    setupShipCreationMap();
    setupShipCreationList();
  }
  @Override
  public String getName() {
    return name;
  }
  /**
   * @description: to add function to create the ships to shipCreatinFns
   * @param:
   **/
  protected void setupShipCreationMap() {
    shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
    shipCreationFns.put("Battleship", (p) -> shipFactory.makeBattleship(p));
    shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
  }
/**
   * @description: to add the number of ships to be created
   * 2 submarines, 2 carriers, 3 destroyers, 3 battleships  
   * @param: 
   **/
  protected void setupShipCreationList() {
     shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
     shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
     shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));
     shipsToPlace.addAll(Collections.nCopies(3, "Battleship"));
  }
  
  /**
   * @description: to read {@link Placement}
   * @param: String prompt of placement
   * @return: {@link Placement}
   */
  public Placement readPlacement(String prompt) throws IOException {
    out.println(prompt);
    try {
      return new Placement(inputReader.readLine());
      //} catch (EOFException e) {
      //out.println("End of File, the provided input is not sufficient");
      //throw e; 
    } catch (NullPointerException e) {
      out.println("Fail to read input, problem with reading for Placement");
      throw e;
    } catch (IllegalArgumentException e) {
      out.println(e.getMessage());
      return readPlacement(prompt);
    }
  }
  /**
   * @description: to read {@link Coordinate}
   * @param: {@link String} prompt of coorinate
   * @return: {@link Coordinate}
   */
  public Coordinate readCoordinate(String prompt) throws IOException {
    out.println(prompt);
    
    try {
      return new Coordinate(inputReader.readLine());
      //} catch (EOFException e) {
      //out.println("End of File, the provided input is not sufficient!");
      //throw e;
    } catch (NullPointerException e) {
      //out.println("Fail to read input, problem with reading for Coordinate");
      throw e;
    } catch (IllegalArgumentException e) {
      out.println(e.getMessage());
      return readCoordinate(prompt);
      //throw e;
    }
  }
  /**
   * @Description: this function to make one placement
   * it will prompt input from users
   * @param: 
   * @throw: IOException
   * @return: None, print the board
   **/
  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException {
    //Prompt the user to entor the location for the ship
    Placement placement = readPlacement("Player " + name + " where do you want to place a " + shipName + "?");
    // Create a basic ship based on the location
    Ship<Character> addingShip = createFn.apply(placement);
    // Add ship to the Board
    String temp = theBoard.tryAddShip(addingShip);
    if (temp != null){
      out.println(temp);
      throw new IllegalArgumentException(temp);
    }
    // Print out the board
    //BoardTextView viewBoard = new BoardTextView(theBoard);
    out.print(view.displayMyOwnBoard());
    
  }
  /**
   * @description: to do Placement phase
   * @param:
   * @return: none
   */
  @Override
  public void doPlacementPhase() throws IOException {
    // Display the starting empty board
    out.print(view.displayMyOwnBoard());
    String instruction = "--------------------------------------------------------------------------------\n" +

        "Player " + name + ": you are going to place the following ships (which are all\n" +
        "rectangular). For each ship, type the coordinate of the upper left\n" +
        "side of the ship, followed by either H (for horizontal) or V (for\n" +
        "vertical).  For example M4H would place a ship horizontally starting\n" +
        "at M4 and going to the right.  You have\n\n" + "2 \"Submarines\" ships that are 1x2\n"
        + "3 \"Destroyers\" that are 1x3\n" + "3 \"Battleships\" that are 1x4\n" + "2 \"Carriers\" that are 1x6\n"
        + "--------------------------------------------------------------------------------\n";
    out.print(instruction);
    for (String shipName: shipsToPlace) {
      //if (inputReader.readLine() == null)
      // throw new EOFException("EOF: input is insufficient.");
      // To handle error case when there is not enough input
      try {
        doOnePlacement(shipName, shipCreationFns.get(shipName));
      }
      catch (NullPointerException e) {
        throw new EOFException("EOF: input is insufficient.");
      } catch (IllegalArgumentException e) {
        //out.print(e.getMessage());
        doOnePlacement(shipName, shipCreationFns.get(shipName));
      }
    }
  }
  /**
   * @description: to display options to choose for {@link TextPlayer}
   * @param:
   **/
  private void displayActions() {
    out.print("---------------------------------------------------------------------------\n" + 
              "Possible actions for Player " + name + ":\n\n" +

              " F Fire at a square\n");
    
    if (moveTurns > 0 )
      out.print(" M Move a ship to another square ("+ moveTurns + " remaining)\n");
    if (sonarTurns > 0 )
      out.print(" S Sonar scan (" + sonarTurns + " remaining)\n\n");
    out.print("Player " + name + ", what would you like to do?\n" +
              "---------------------------------------------------------------------------\n");
  }
  /**
   * @description: to prompt users to input to choose for action
   * M: move
   * F: fire
   * S: sonar scan
   * @param: 
   * @return: type of the chosen action
   **/
  private String makeAction() throws IOException, NullPointerException {
    while (true) {
      String choice = inputReader.readLine().toUpperCase();
      
      if (choice.equals("M"))
           return "M";
      if (choice.equals("S"))
           return "S";
      if (choice.equals("F"))
           return "F";       
        out.println("Choose possible actions again:");
      
    }
    
  }
  /**
   * @description: to get the ship from a coordinate for move action
   * @param: {@link Coordinate} of the ship
   * @return: {@link Ship} to choose 
   **/
  private Ship<Character> chooseShip(Coordinate coord) throws IOException {
    Ship<Character> ship = theBoard.getShipAt(coord);
    if (ship == null) {
      throw new IllegalArgumentException("The Coordinate is not occupied by ship");
    }
    return ship;
  }
  /**
   * To move the ship to a new placement  
   * @param: {@link Ship} ship
   * @return: none
   */
  private Boolean moveShip(Ship<Character> ship, Coordinate chosenCoordinate, Placement newPlacement) {
    // try removing the ship from the board
    theBoard.removeShip(ship); 
      //return false;
    V2ShipFactory factory = new V2ShipFactory();
    Character type = ship.getMyDisplayInfo().getInfo(chosenCoordinate, false);
    Ship<Character> newShip = factory.makeShipFromType(type, newPlacement);
    // check for validation of adding proccess
    String message = theBoard.tryAddShip(newShip);
    if (message != null){
      theBoard.tryAddShip(ship);
      throw new IllegalArgumentException(message);
    }
    newShip.transferDamage(ship);
    return true;
  }
  /**
   * play move turn
   * @param: 
   * @return:
   */
  protected Boolean playMove() throws IOException, EOFException {
    Coordinate toMoveCoordinate = null;
    //try {
    toMoveCoordinate = readCoordinate("Input the to move ship's coordinate:");
      //} catch (NullPointerException e) {
      //out.println(e.getMessage());
      //return false;
    Ship<Character> shipToMove = null;
    try {
      shipToMove= chooseShip(toMoveCoordinate);
    } catch (IllegalArgumentException e) {
      out.println(e.getMessage());
      return false;
    }
    Placement newPlacement = readPlacement("Enter new Placement for the chosen ship:");
    try {
      moveShip(shipToMove, toMoveCoordinate, newPlacement);
    } catch (IllegalArgumentException e) {
      out.println(e.getMessage());
      return false;
    }
    return true;
  }
  /**
   * To print string the result of sonar scanning
   */
  private String scannedResult(ArrayList<Character> list) throws IOException {
    StringBuilder result = new StringBuilder("---------------------------------------------------------------------------\n");
    int s = 0;
    int c = 0;
    int b = 0;
    int d = 0;
    for (Character ch: list) {
      if (ch == 's')
        s++;
      else if (ch == 'c')
        c++;
      else if (ch == 'b')
        b++;
      else if (ch == 'd')
        d++;
      else
        throw new IllegalArgumentException("Ship's type invailid" + ch);
    }
    result.append("Submarines occupy " + s).append(s == 1 ? " square\n" : " squares\n");
    result.append("Destroyers occupy " + d).append(d == 1 ? " square\n" : " squares\n");
    result.append("Battleships occupy " + b).append(b == 1 ? " square\n" : " squares\n");
    result.append("Carriers occupy " + c).append(c == 1 ? " square\n" : " squares\n");
    result.append("---------------------------------------------------------------------------\n");
    return result.toString();
  }
  /**
   * Select one ship, then move it to the new location
   * @param: {@link Board} enemyBoard
   * @return: {@link Boolean}
   */
  protected Boolean playSonarTurn(Board<Character> enemyBoard) throws IOException {
    Coordinate scanCoordinate;
    try {
      scanCoordinate = readCoordinate("Enter the center coordinate of the sonar scan:");
    } catch (NullPointerException e) {
      out.println(e.getMessage());
      throw e;
    }
    if (scanCoordinate.getRow() >= enemyBoard.getHeight() || scanCoordinate.getRow() < 0 || scanCoordinate.getColumn() <0 || scanCoordinate.getColumn() >= enemyBoard.getWidth())
      return false;
    ArrayList<Character> list = sonarScan(enemyBoard, scanCoordinate);
    
    out.print(scannedResult(list));
    return true;
  }
  /**
   * scan for enemy ships around a location
   * @param: {@link Board} enemyBoard, {@link Coordinate} scanning location
   * @return: list of character
   */
  private ArrayList<Character> sonarScan(Board<Character> enemyBoard,Coordinate location) {
    ArrayList<Character> listChars = new ArrayList<Character>();
    int n = 4;
    // for loop to scan a diamond shape
    int x = location.getColumn();
    int y = location.getRow();
    for (int row = y - n+1; row < y + n; row++) {
      for (int column = x - n+1; column < x + n; column++) {
        // TO extract diamond shape of squares
        if ((Math.abs(row - y) + Math.abs(column - x) < n) && (row < enemyBoard.getHeight()) && (row >= 0) && (column < enemyBoard.getWidth()) && (column >= 0)) {
          Ship<Character> ship = enemyBoard.getShipAt(new Coordinate(row, column));
          if (ship != null)
            listChars.add(ship.getMyType());
        }
      }
    } 
    return listChars;
  }
   /**
   * Play one turn: take in the enemy's Board
   * 
   * @param enemy's {@link Board}, {@link BoardTextView}
   * @return None
   */
  @Override
  public void playOneTurn(String enemyName, Board<Character> enemyBoard, BoardTextView enemyTextView) throws IOException, NullPointerException {
    String choice = "F";
    String enemyHeader = "Player " + enemyName + "'s ocean";
    out.print(view.displayMyBoardWithEnemyNextToIt(enemyTextView, "Your ocean", enemyHeader));
    if (moveTurns == 0 && sonarTurns == 0)
      choice = "F";
    else {
      displayActions();
    
      choice = makeAction();
      }
    if (choice.equals("F"))
      fireTurn(enemyName, enemyBoard, enemyTextView);
    else if (choice.equals("M")) {
      Boolean moveCheck = playMove();
      if (!moveCheck)
        playOneTurn(enemyName, enemyBoard, enemyTextView);
      else
        moveTurns--;
    }
    else {
      Boolean sonarCheck = playSonarTurn(enemyBoard);
      if (!sonarCheck)
        playOneTurn(enemyName, enemyBoard, enemyTextView);
      else
        sonarTurns--;
    }
  }
  /**
   * Play fire turn: take in the enemy's Board
   * 
   * @param enemy's {@link Board}, {@link BoardTextView}
   * @return None
   */
  protected void fireTurn(String enemyName, Board<Character> enemyBoard, BoardTextView enemyTextView) throws IOException {
    out.print("---------------------------------------------------------------------------\n" +
              "Player " + name + "'s fire turn:\n");
    Coordinate attackingCoordinate = readCoordinate("Input Coordinate to fire:");
    //try {
    //attackingCoordinate = readCoordinate("");
      //} catch (IllegalArgumentException e) {
      //out.println(e.getMessage());
      //attackingCoordinate = readCoordinate("");
      //}
    Ship<Character> ship = enemyBoard.fireAt(attackingCoordinate); 
    if (ship != null) {
      out.print("---------------------------------------------------------------------------\n" + 
                "You hit a " + ship.getName() + "\n" +
                "---------------------------------------------------------------------------\n");
    }
    else
      out.print("---------------------------------------------------------------------------\n" +
                "You missed!\n" +
                "---------------------------------------------------------------------------\n");
                
  }
}
