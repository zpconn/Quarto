package model;

/**
 * An adapter to the view from the model.
 * @author Zach
 *
 */
public interface IViewAdapter {
	/**
	 * Tells the view to display the new game state s.
	 * @param s The new state to be displayed on the board.
	 */
	public void updateGameState(State s);
	
	/**
	 * Displays a message on the view.
	 * @param s The message to announce.
	 */
	public void appendMessage(String s);
	
	/**
	 * Signals to the view that the game is over.
	 * @param nameOfWinner The name of the winner to be displayed in the message.
	 */
	public void signalGameOver(String nameOfWinner);
}
