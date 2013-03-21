package controller;

import java.awt.EventQueue;
import java.util.ArrayList;
import model.IViewAdapter;
import model.Model;
import model.Move;
import model.State;
import view.IModelAdapter;
import view.View;

public class Controller {
	private static Model model;
	private static View view;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				(new Controller()).start();
			}
		});
	}

	public Controller() {
		init();
	}

	/**
	 * Creates the view and model and creates adapters between them.
	 */
	private static void init() {
		model = new Model(new IViewAdapter() {

			@Override
			public void updateGameState(State s) {
				view.updateGameState(s);
			}

			@Override
			public void appendMessage(String s) {
				view.appendMessage(s);
			}

			@Override
			public void signalGameOver(String nameOfWinner) {
				view.appendMessage("The game is over!");
				view.appendMessage(nameOfWinner + " won.");
				view.endGame();
			}

		});

		view = new View(new IModelAdapter() {

			@Override
			public void sendMoves(final ArrayList<Move> moves) {
				view.appendMessage("The AI is thinking...");
				(new Thread(){
					public void run() {
						model.receiveMoves(moves);
					}
				}).start();
			}

		});
	}

	/**
	 * Starts the program's MVC
	 */
	public void start() {
		view.start();
		model.start();
	}
}
