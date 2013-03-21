package view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;

public class NewView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5080116497498312269L;
	private JPanel contentPane;
	private JPanel centerPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	
	/**
	 * Create the frame.
	 */
	public NewView() {
		setTitle("Quarto");
		initGUI();
	}
	
	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 468, 306);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		centerPanel = new JPanel();
		contentPane.add(centerPanel);
		centerPanel.setLayout(new GridLayout(1, 0, 0, 0));
		
		leftPanel = new JPanel();
		contentPane.add(leftPanel, BorderLayout.WEST);
		
		rightPanel = new JPanel();
		contentPane.add(rightPanel, BorderLayout.EAST);
	}
	
	public void start() {
		this.setVisible(true);
	}

}
