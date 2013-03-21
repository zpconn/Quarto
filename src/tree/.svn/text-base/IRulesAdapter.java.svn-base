package tree;

/**
 * Rule information that the tree needs to operate
 * @author Salvatore Testa
 *
 * @param <State>
 */
public interface IRulesAdapter<State> {
	Iterable<State> getFollowingStates(State baseState);
	Double rateState(State state, Integer player);
	Integer getNextPlayer(Integer player);
}
