package tree;

/**
 * Model a game tree to represent the best possibilities
 * @author Salvatore Testa
 *
 * @param <State> Representation of the scenario we're optimizing
 */
public interface IDecisionTree<State>{
	/**
	 * Update the current situation the model is in
	 * @param state
	 */
	void updateTreeRoot(State state);
	/**
	 * Get the optimal next state from this one
	 * @param depth how deep down the tree that should be explored
	 * @param player which player we are getting the optimal move for
	 * @return optimal state
	 */
	State getBestMove(Integer depth, Integer player);
}
