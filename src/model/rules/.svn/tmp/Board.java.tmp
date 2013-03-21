package model.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import model.IPiece;
import model.IPosition;
import model.State;
import visitors.AExtVisitor;

/**
 * The Board class contains the game-dependent portion of the algorithm for computing the next valid states
 * of the game from the current state. Specific games provide derived subclasses of Board. The derived
 * subclass is responsible for filling in the game-specific visitor commands and providing an implementation
 * of the constructInitialState() method, which yields the initial state of the game.
 * @author zpconn
 *
 */
public abstract class Board {
	/**
	 * This is the visitor which does all the magic.
	 */
	protected AExtVisitor<Collection<State>, Class<?>, IPosition, IPiece> visitor;
	
	/**
	 * This is the current state of the game.
	 */
	protected State currentState;
	
	/**
	 * This instantiates the visitor and just fills the visitor with a default command handler.
	 */
	public Board() {
		visitor = new AExtVisitor<Collection<State>, Class<?>, IPosition, IPiece>(
				  new PieceHandler<Collection<State>, IPiece, IPosition>() {
					  @Override
					  public Collection<State> apply(Class<?> index, IPiece host, IPosition... position) {
						  // Return an empty list.
						  return new ArrayList<State>();
					  }
		}){};
	}
	
	public Collection<State> getAvailableMoves(IPiece piece, IPosition position, State state) {
		currentState = state;
		
		return piece.execute(visitor, position);
	}
	
	/**
	 * This Board class should be derived to provide game-specific implementations of this method.
	 * @return The initial state of the game.
	 */
	public State constructInitialState() {
		// Give out an empty board.
		return new State(new ArrayList<IPiece>(), new HashMap<IPosition, IPiece>());
	}
	
	/**
	 * Determines whether or not the provided state is in a winning condition.
	 * @param s The state to check.
	 * @return True if winning, false if not winning.
	 */
	public boolean checkWinningCondition(State s) {
		return false;
	}
	
	public abstract IPosition getUseablePoolPosition();
}
