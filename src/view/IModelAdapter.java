package view;

import java.util.ArrayList;
import model.Move;

/**
 * This is an adapter to the model from the view.
 * @author Zach
 *
 */
public interface IModelAdapter {
	/**
	 * Sends the list of moves made by the player to the model.
	 * @param moves
	 */
	public void sendMoves(ArrayList<Move> moves);
}
