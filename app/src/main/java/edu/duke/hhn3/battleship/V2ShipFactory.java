package edu.duke.hhn3.battleship;
/**
 * @program: battleship
 * @class: Ship Factory version 2 with non-rectangle ship
 */
public class V2ShipFactory implements AbstractShipFactory<Character> {

  /**
   * @description: construct a {@link RectangleShip}
   * @param: {@link Placement}, width, height, letter of type, and name of the ship
   * @return: {@link Ship} a {@link RectangleShip}
   **/
  protected Ship<Character> createRectangleShip(Placement where, int w, int h, char letter, String name) throws IllegalArgumentException {
    if (where.getOrientation() == 'V')
      return new RectangleShip<Character>(name, where.getWhere(), w, h, letter, '*');
    else if (where.getOrientation() == 'H')
     return new RectangleShip<Character>(name, where.getWhere(), h, w, letter, '*');
    else
      throw new IllegalArgumentException("Placement's orientation must be V or H");
  }
  /**
   * @description: construct a {@link NonRectangleShip}
   * @param: {@link Placement}, letter of type, and name of the ship
   * @return: {@link Ship} a {@link NonRectangleShip}
   **/
  protected Ship<Character> createNonRectangleShip(Placement where, char letter, String name) throws IllegalArgumentException {
    char orient = where.getOrientation();
    if (orient != 'U' && orient != 'D' && orient != 'R' && orient != 'L')
      throw new IllegalArgumentException("Placement's orientation must be U/D/R or L");
    else
      return new NonRectangleShip(name, where.getWhere(), orient, letter, '*');
  }
  /**
   * @description: construct a submarine
   * @param: {@link Placement} where
   * @return: {@link Ship} a submarine
   **/
  @Override
  public Ship<Character> makeSubmarine(Placement where) {
     return createRectangleShip(where, 1, 2, 's', "Submarine");
  }
  /**
   * @description: construct a destroyer
   * @param: {@link Placement} where
   * @return: {@link Ship} a destroyer
   **/
  @Override
  public Ship<Character> makeDestroyer(Placement where) {
    return createRectangleShip(where, 1, 3, 'd', "Destroyer");
  }
  
  /**
   * @description: construct a battleship
   * @param: {@link Placement} where
   * @return: {@link Ship} a battleship
   **/
  @Override
  public Ship<Character> makeBattleship(Placement where) {
    return createNonRectangleShip(where, 'b', "Battleship");
  }
  /**
   * @description: construct a carrier
   * @param: {@link Placement} where
   * @return: {@link Ship} a carrier
   **/
  @Override
  public Ship<Character> makeCarrier(Placement where) {
    return createNonRectangleShip(where, 'c', "Carrier");
  }
  /**
   * @description: construct a ship from its type
   * @param: {@link Placement} newPlacement
   * @param: {@link Character} type
   * @return: {@link Ship} of given type
   **/
  public Ship<Character> makeShipFromType(Character type, Placement newPlacement) {
    V2ShipFactory factory = new V2ShipFactory();
    if (type == 's')
      return factory.makeSubmarine(newPlacement);
    else if (type == 'c')
      return factory.makeCarrier(newPlacement);
    else if (type == 'b')
      return factory.makeBattleship(newPlacement);
    else if (type == 'd')
      return factory.makeDestroyer(newPlacement);
    else
      throw new IllegalArgumentException();
  }
  

}
