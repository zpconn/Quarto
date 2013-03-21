package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A factory for applying moves to game boards in the game of Quarto.
 * @author zpconn
 *
 */
public class QuartoMoveToStateFactory implements IMoveToStateFactory {

	@Override
	public State makeState(State s, Move m) {
		IPiece piece = m.piece;
		IPosition newPosition = m.newPosition;
		
		ArrayList<IPiece> useablePiecesCopy = new ArrayList<IPiece>(s.getUseablePieces());
		Map<IPosition, IPiece> pieceLocationsCopy = new HashMap<IPosition, IPiece>(s.getPieceLocations());
		
		// We first need to find piece inside the state.
		
		// First search the useable pieces.
		
		for (IPiece useablePiece : s.getUseablePieces()) {
			if (piece.getType().equals(useablePiece.getType())) {
				useablePiecesCopy.remove(useablePiece);
				pieceLocationsCopy.put(newPosition, piece);
				
				return new State(useablePiecesCopy, pieceLocationsCopy);
			}
		}
		
		// Now search the live piece locations.
		
		for (IPosition pos :pieceLocationsCopy.keySet()) {
			IPiece activePiece = pieceLocationsCopy.get(pos);
			
			if (activePiece.getType().equals(piece.getType())) {
				pieceLocationsCopy.remove(pos);
				pieceLocationsCopy.put(newPosition, piece);
				
				return new State(useablePiecesCopy, pieceLocationsCopy);
			}
		}
		
		// This somehow wasn't a valid move...
		return null;
	}

}
