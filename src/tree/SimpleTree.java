package tree;

import tree.node.DataNode;
import tree.node.ITreeNode;

public class SimpleTree<State> implements IDecisionTree<State> {
	
	private ITreeNode<State> root;
	private IRulesAdapter<State> rules;
	
	public SimpleTree(State baseState, IRulesAdapter<State> rules) {
		this.rules = rules;
		updateTreeRoot(baseState);
	}

	@Override
	public void updateTreeRoot(State state) {
		root = new DataNode<State>(state, rules);
	}

	@Override
	public State getBestMove(Integer depth, Integer player) {
		return root.getBestMove(depth, player, 0).getState();
	}

}
