package edu.duke.hhn3.battleship;

/**
 * @description: V1ShipFactory class, inherits from RectangleShip
 *               to make specific types of ships: submarines, battleships,
 *               carriers, destroyers
 */
public class V1ShipFactory implements AbstractShipFactory<Character> {
  /**
   * @description: to create a {@link RectangleShip} from Placement, height,
   *               width, name and type
   * @param: {@link Placement} where
   * @param: width, height, letter (type of the ship), name
   **/
  protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name)
      throws IllegalArgumentException {
    if (where.getOrientation() == 'V')
      return new RectangleShip<Character>(name, where.getWhere(), w, h, letter, '*');
    else if (where.getOrientation() == 'H')
      return new RectangleShip<Character>(name, where.getWhere(), h, w, letter, '*');
    else
      throw new IllegalArgumentException("Placement's orientation must be V or H");
  }

  /**
   * @description: construct a submarine
   * @param: {@link Placement} where
   * @return: {@link Ship} a submarine
   **/
  @Override
  public Ship<Character> makeSubmarine(Placement where) {
    return createShip(where, 1, 2, 's', "Submarine");
  }

  /**
   * @description: construct a BattleShip
   * @param: {@link Placement} where
   * @return: {@link Ship} a Battleship
   **/
  @Override
  public Ship<Character> makeBattleship(Placement where) {
    return createShip(where, 1, 4, 'b', "Battleship");
  }

  /**
   * @description: construct a Carrier
   * @param: {@link Placement} where
   * @return: {@link Ship} a Carrier
   **/
  @Override
  public Ship<Character> makeCarrier(Placement where) {
    return createShip(where, 1, 6, 'c', "Carrier");
  }

  /**
   * @description: construct a Destroyer
   * @param: {@link Placement} where
   * @return: {@link Ship} a destroyer
   **/
  @Override
  public Ship<Character> makeDestroyer(Placement where) {
    return createShip(where, 1, 3, 'd', "Destroyer");
  }

}
