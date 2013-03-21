package model;

import java.util.ArrayList;
import tree.IRulesAdapter;
import tree.SimpleTree;

import model.rules.Board;
import model.rules.QuartoBoard;
import model.rules.Rules;

public class Model {
	private IViewAdapter viewAdapter;
	private Rules rules;
	private State currentState;
	private SimpleTree<State> decisionTree;
	private int numSteps = 0;
	
	public Model(IViewAdapter viewAdapter) {
		this.viewAdapter = viewAdapter;
		
		Board quartoBoard = new QuartoBoard();
		rules = new Rules(quartoBoard);
	}
	
	public void start() {
		viewAdapter.appendMessage("The game has started.");
		currentState = rules.getBaseState();
		decisionTree = new SimpleTree<State>(currentState, new IRulesAdapter<State>() {

			@Override
			public Iterable<State> getFollowingStates(State baseState) {
				return rules.getNextValidStates(baseState);
			}

			@Override
			public Double rateState(State state, Integer player) {
				if (player == 1) {
					return rules.checkWinningCondition(state) ? -1.0 : 0.0;
				}
				else
					return rules.checkWinningCondition(state) ? 1.0 : 0.0;
			}

			@Override
			public Integer getNextPlayer(Integer player) {
				return 1 - player;
			}
			
		});
		
		viewAdapter.updateGameState(currentState);
	}
	
	public void receiveMoves(ArrayList<Move> moves) {
		
		IMoveToStateFactory fact = new QuartoMoveToStateFactory();
		
		State newState;
		
		for (Move move : moves) {
			newState = fact.makeState(currentState, move);
			currentState = newState;
		}
		
		if (rules.checkWinningCondition(currentState)) {
			viewAdapter.signalGameOver("You");
			viewAdapter.updateGameState(currentState);
			return;
		}
		
		int depth = 2 + Math.max(numSteps - 2, 0);
		
		decisionTree.updateTreeRoot(currentState);
		State aiMove = decisionTree.getBestMove(depth, 1);
		currentState = aiMove;
		decisionTree.updateTreeRoot(currentState);
		viewAdapter.updateGameState(aiMove);
		numSteps++;
		
		if (rules.checkWinningCondition(currentState)) {
			viewAdapter.signalGameOver("The AI");
		}
		else {
			viewAdapter.appendMessage("It's your turn.");
		}
	}
}
