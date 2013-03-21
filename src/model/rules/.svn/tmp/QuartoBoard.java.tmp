package model.rules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import model.IPiece;
import model.IPosition;
import model.QuartoPiece;
import model.QuartoPosition;
import model.State;

public class QuartoBoard extends Board {
	public QuartoBoard() {
		super();
		
		installQuartoCommands();
	}
	
	/**
	 * This Quarto-specific function yields the initial state of the board for the game of Quarto.
	 * @return A State object specifying the initial state of the board in Quarto.
	 */
	@Override
	public State constructInitialState() {
		// At the beginning of the game, there are no pieces in play on the board, there is one "current piece",
		// and every other piece is a "useable piece."
		
		// NOTE: We arbitrarily select 1010 as the current piece.
		
		Map<IPosition, IPiece> pieceLocations = new HashMap<IPosition ,IPiece>();
		
		ArrayList<IPiece> useablePieces = new ArrayList<IPiece>();
		String[] pieceTypes = {"0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001",
				               /*"1010",*/ "1011", "1100", "1101", "1110", "1111"};
		
		for (String type : pieceTypes) {
			QuartoPiece piece = new QuartoPiece(QuartoPiece.class);
			piece.setType(type);
			useablePieces.add(piece);
		}
		
		QuartoPiece currentPiece = new QuartoPiece(QuartoPiece.class);
		currentPiece.setType("1010");
		pieceLocations.put(QuartoPosition.getOffboardPosition(), currentPiece);
		
		return new State(useablePieces, pieceLocations);
	}
	
	/**
	 * Checks if the board is in a winning position for the game of Quarto. The board is winning
	 * if there exists a row, column, or diagonal line of four pieces all of which share a property
	 * in common.
	 */
	@Override
	public boolean checkWinningCondition(State s) {
		// We enumerate lines (rows, columns, diagonals) of four cells, and for each such line
		// which is "full" (contains no empty cells) we check if there is some property common to
		// all four pieces.
		
		Map<IPosition, IPiece> pieceLocations = s.getPieceLocations();
		
		// -- Check rows.
		
		for (int row = 0; row < 4; ++row) {
			if (pieceLocations.containsKey(new QuartoPosition(0, row)) &&
		        pieceLocations.containsKey(new QuartoPosition(1, row)) &&
		        pieceLocations.containsKey(new QuartoPosition(2, row)) &&
		        pieceLocations.containsKey(new QuartoPosition(3, row))) {
				boolean win = checkLine(pieceLocations.get(new QuartoPosition(0, row)).getType(),
									    pieceLocations.get(new QuartoPosition(1, row)).getType(),
									    pieceLocations.get(new QuartoPosition(2, row)).getType(),
									    pieceLocations.get(new QuartoPosition(3, row)).getType());
						   
				if (win) {
					return true;
				}
			}
		}
		
		// -- Check columns.
		
		for (int col = 0; col < 4; ++col) {
			if (pieceLocations.containsKey(new QuartoPosition(col, 0)) &&
		        pieceLocations.containsKey(new QuartoPosition(col, 1)) &&
		        pieceLocations.containsKey(new QuartoPosition(col, 2)) &&
		        pieceLocations.containsKey(new QuartoPosition(col, 3))) {
				boolean win = checkLine(pieceLocations.get(new QuartoPosition(col, 0)).getType(),
									    pieceLocations.get(new QuartoPosition(col, 1)).getType(),
									    pieceLocations.get(new QuartoPosition(col, 2)).getType(),
									    pieceLocations.get(new QuartoPosition(col, 3)).getType());
						   
				if (win) {
					return true;
				}
			}
		}
		
		// -- Check diagonals.
		
		if (pieceLocations.containsKey(new QuartoPosition(0, 0)) &&
		    pieceLocations.containsKey(new QuartoPosition(1, 1)) &&
		    pieceLocations.containsKey(new QuartoPosition(2, 2)) &&
		    pieceLocations.containsKey(new QuartoPosition(3, 3))) {
	        boolean win = checkLine(pieceLocations.get(new QuartoPosition(0, 0)).getType(),
									pieceLocations.get(new QuartoPosition(1, 1)).getType(),
									pieceLocations.get(new QuartoPosition(2, 2)).getType(),
								    pieceLocations.get(new QuartoPosition(3, 3)).getType());
	        
	        if (win) {
	        	return true;
	        }
		}
		
		if (pieceLocations.containsKey(new QuartoPosition(3, 0)) &&
			    pieceLocations.containsKey(new QuartoPosition(2, 1)) &&
			    pieceLocations.containsKey(new QuartoPosition(1, 2)) &&
			    pieceLocations.containsKey(new QuartoPosition(0, 3))) {
		        boolean win = checkLine(pieceLocations.get(new QuartoPosition(3, 0)).getType(),
										pieceLocations.get(new QuartoPosition(2, 1)).getType(),
										pieceLocations.get(new QuartoPosition(1, 2)).getType(),
									    pieceLocations.get(new QuartoPosition(0, 3)).getType());
		        
		        if (win) {
		        	return true;
		        }
			}
		
		return false;
	}
	
	/**
	 * Checks if the four pieces contain a property in common. Each piece is represented by a string
	 * of four bits.
	 */
	private boolean checkLine(String s1, String s2, String s3, String s4) {
		for (int i = 0; i < 4; ++i) {
			if (s1.charAt(i) == s2.charAt(i) &&
			    s2.charAt(i) == s3.charAt(i) &&
			    s3.charAt(i) == s4.charAt(i)) {
				// We've found a property which all four pieces have in common.
				return true;
			}
		}
		
		// The pieces do not contain any single property in common.
		return false;
	}
	
	/**
	 * This is the Quarto-specific implementation of the board visitor. This makes the visitor
	 * aware of how the pieces in the game of Quarto can move.
	 */
	private void installQuartoCommands() {
		// We override the default command, because in Quarto, in fact, all pieces are "the same" from a
		// functional viewpoint.
		
		visitor.setDefaultCmd(new PieceHandler<Collection<State>, IPiece, IPosition>() {
			@Override
			public Collection<State> apply(Class<?> index, IPiece host,
					IPosition... params) {
				// This will store all the new possible states.
				ArrayList<State> nextStates = new ArrayList<State>();
				
				
				if (!((QuartoPosition)params[0]).isOffboard()) {
					return nextStates;
				}
				
				// We enumerate the new states as follows. First, our piece may move to any nonempty cell.
				// Second, for each such move, we may select any useable piece as the "current piece" which
				// the player must then place on his turn.
				
				Map<IPosition, IPiece> pieceLocations = currentState.getPieceLocations();
				Collection<IPiece>     useablePieces  = currentState.getUseablePieces();
				
				// The useable pieces are the ones which we can place into squares not occupied by any of the pieces
				// contained inside of the pieceLocations map.
				
				// We must therefore first find the complement of the set of occupied locations in the board itself.
				
				ArrayList<IPosition> unoccupiedPositions = new ArrayList<IPosition>();
				
				for (int i = 0; i < 4; ++i) {
					for (int j = 0; j < 4; ++j) {
						// Let's check if this location is occupied.
						QuartoPosition testPos = new QuartoPosition(i, j);
						
						boolean contains = false;
						for (IPosition pos : pieceLocations.keySet()) {
							if (testPos.equals((QuartoPosition)pos)) {
								contains = true;
							}
						}
						
						if (!contains) {
							unoccupiedPositions.add(testPos);
						}
					}
				}
				
				// Now we may move our host piece to any unoccupied position.
				
				for (IPosition pos : unoccupiedPositions) {
					for (IPiece useablePiece : useablePieces) {
						// We need to make new copies of the collections here to package up
						// into a new state object.
						
						Map<IPosition, IPiece> pieceLocationsCopy = new HashMap<IPosition, IPiece>(pieceLocations);
						
						// And for each such position we may select any other useable piece as the "current" piece.
						
						pieceLocationsCopy.put((QuartoPosition)pos, host);
						pieceLocationsCopy.remove((QuartoPosition)params[0]);
						pieceLocationsCopy.put((QuartoPosition)params[0], useablePiece);
						
						ArrayList<IPiece> useablePiecesCopy = new ArrayList<IPiece>(useablePieces);
						useablePiecesCopy.remove(useablePiece);
						
						State newState = new State(useablePiecesCopy, pieceLocationsCopy);
						nextStates.add(newState);
					}
				}
				
				return nextStates;
			}
		});
	}

	@Override
	public IPosition getUseablePoolPosition() {
		return (new QuartoPosition(-1, -1)).getUseablePoolPosition();
	}
}
