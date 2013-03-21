package model;

/**
 * A general interface for a factory which combines moves and states into new states, i.e.,
 * it applies a move to a game board to get the new state of the board.
 * @author zpconn
 *
 */
public interface IMoveToStateFactory {
	/**
	 * Applies move m to state s to get a new state of the game.
	 */
	public State makeState(State s, Move m);
}
