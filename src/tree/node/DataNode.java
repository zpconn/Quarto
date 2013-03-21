package tree.node;

import java.util.ArrayList;
import tree.IRulesAdapter;

/**
 * Recursive tree node that contains the a scenario states and keeps track of its children
 * @author Salvatore Testa
 *
 * @param <State>
 */
public class DataNode<State> implements ITreeNode<State> {

	private State state;
	private IRulesAdapter<State> rules;
	private ArrayList<ITreeNode<State>> children;
	
	public DataNode(State state, IRulesAdapter<State> rules) {
		this.state = state;
		this.rules = rules;
	}

	@Override
	public Iterable<ITreeNode<State>> getChildren() {
		// Lazily evaluate the children
		if(children == null) {
			children = new ArrayList<ITreeNode<State>>();
			for(State newState: rules.getFollowingStates(state)) {
				children.add(new DataNode<State>(newState, rules));
			}
		}
		return children;
	}

	@Override
	public ITreeNode<State> getBestMove(Integer depth, Integer player, int step) {
		if(depth > 0) {
			ITreeNode<State> best = this;
			Double bestRanking = Double.MAX_VALUE;
			for(ITreeNode<State> child: getChildren()) {
				// If it's the player's turn, and this child node represents a winning move, then we do *not*
				// explore the tree further.
				
				Double nextRanking = Double.MAX_VALUE;

				if (! (player == 0 && rules.rateState(child.getState(), 0) == 1)) {
					ITreeNode<State> relevantSolution = child.getBestMove(depth - 1, rules.getNextPlayer(player), step + 1);
					
					double x = rules.rateState(relevantSolution.getState(), player);
					nextRanking = x + (1.0 - x) / 2.0; // This biases the ranking in favor of winning moves that occur
					  								   // sooner in the tree.
				}
				if(nextRanking < bestRanking){
					bestRanking = nextRanking;
					best = child;
				}
			}
			return best;
		} else {
			return this;
		}
	}

	@Override
	public State getState() {
		return state;
	}


}
