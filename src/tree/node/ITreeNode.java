package tree.node;
/**
 * Individual representation of each scenario with the possible following scenarios as its children
 * @author Salvatore Testa
 *
 * @param <Scenario>
 */
public interface ITreeNode<Scenario> {
	Iterable<ITreeNode<Scenario>> getChildren();
	ITreeNode<Scenario> getBestMove(Integer depth, Integer player, int step);
	Scenario getState();
}
