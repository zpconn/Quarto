package view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import model.IPiece;
import model.IPosition;
import model.Move;
import model.QuartoPiece;
import model.QuartoPosition;
import model.State;

/*
 * @author Zach Conn
 */
public class View extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH  = 4;
	private static final int HEIGHT = 4;
	
	private IModelAdapter modelAdapter;

    private Image boardImage1;
    private Image boardImage2;
    
    private JPanel centerPanel = new JPanel();
    private JPanel southPanel  = new JPanel();
    private JPanel westPanel   = new JPanel();
    private JPanel eastPanel   = new JPanel();
    
    private JLabel[] centerLabels     = new JLabel[WIDTH * HEIGHT];
    private JLabel[] westLabels       = new JLabel[WIDTH * HEIGHT];
    private ImagePanel[] centerPanels = new ImagePanel[WIDTH * HEIGHT];
    private ImagePanel[] westPanels = new ImagePanel[WIDTH * HEIGHT];
    private JPanel currentPiecePanel;
    private JLabel currentPieceLabel = new JLabel();
    private final JTextArea txtrmessageCenter = new JTextArea();
    
    private Map<String, String> useablePieceMap        = new HashMap<String, String>();
    private Map<String, String> useablePieceMapInverse = new HashMap<String, String>();
    
    private String currentPieceType = "";
    
    private ArrayList<Move> moves = new ArrayList<Move>();
    
    private boolean canMove = true;

    public View(IModelAdapter modelAdapter) {
    	this.modelAdapter = modelAdapter;
    	

		try {
			this.boardImage1 = ImageIO.read(new File("./assets/bg1.png"));
	        this.boardImage2 = ImageIO.read(new File("./assets/bg2.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        createGUI();
    }
    
    /**
     * Adds a message to the message pane.
     * @param s The message to announce.
     */
    public void appendMessage(String s) {
    	txtrmessageCenter.append(s + "\n");
    }
    
    public void endGame() {
    	canMove = false;
    }
    
    /**
     * Updates the view to reflect the new state s provided by the model.
     * @param s The new state as per the choices of the AI in the model.
     */
    public void updateGameState(State s) {
    	clearBoard();
    	
    	// -- UPDATE USEABLE PIECES
    	Collection<IPiece> useablePieces = s.getUseablePieces();
    	
    	for (IPiece piece : useablePieces) {
    		Image img;
			try {
				img = ImageIO.read(new File("./assets/" + piece.getType() + ".png")).getScaledInstance(70, 70, 0);
				ImageIcon icon = new ImageIcon(img);
	    		
	    		addUseablePiece(icon, useablePieceMap.get(piece.getType()));
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	
    	// -- UPDATE THE PLAYING BOARD
    	Map<IPosition, IPiece> pieceLocations = s.getPieceLocations();
    	
    	for (IPosition pos : pieceLocations.keySet()) {
    		IPiece piece = pieceLocations.get(pos);
    		
    		Image img;
			try {
				img = ImageIO.read(new File("./assets/" + piece.getType() + ".png"));
				ImageIcon icon = new ImageIcon(img);
				
	    		QuartoPosition qpos = (QuartoPosition)pos;
				
	    		if (qpos.isOffboard()) {
	    			// This piece is the "current piece." So we need to place it on the current piece tile 
	    			// and remember its type.
	    			
	    			currentPieceType = piece.getType();
	    			setCurrentPiece(icon);
	    		}
	    		else {
	    			addPiece(icon, locationString(pos.getX() + 1, pos.getY() + 1));
	    		}
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	
    	// Now we need to make sure the "current piece" doesn't also appear in the list of useable pieces on the left.
    	
    	clearUseablePiece(useablePieceMap.get(currentPieceType));
    	
    	// Our turn.
    	
    	//canMove = true;
    }
    
    /**
     * Clears the main playing board of pieces.
     */
    private void clearBoard() {
    	clearPlayingBoard();
    	clearUseablePieces();
    }
    
    private void clearPlayingBoard() {
    	for (int i = 1; i <= 4; ++i) {
    		for (int j = 1; j <= 4; ++j) {
    			clearPiece(locationString(i, j));
    		}
    	}
    }
    
    private void clearUseablePieces() {
    	for (int i = 1; i <= 8; ++i) {
    		for (int j = 1; j <= 2; ++j) {
    			clearUseablePiece(locationString(i, j));
    		}
    	}
    }

    private void createGUI() {
        setTitle("Quarto");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addComponentsToPane(getContentPane());

        setSize(1045, 700);
        
        String[] assets = {"0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"};
        
        for (int row = 1; row <= 8; ++row) {
        	for (int col = 1; col <= 2; ++col) {
        		try {
					Image img = ImageIO.read(new File("./assets/" + assets[col - 1 + 2 * (row - 1)] + ".png")).getScaledInstance(70, 70, 0);
					ImageIcon icon = new ImageIcon(img);
					
					addUseablePiece(icon, locationString(col, row));
					
					useablePieceMap.put(assets[col - 1 + 2 * (row - 1)], locationString(col, row));
					useablePieceMapInverse.put(locationString(col, row), assets[col - 1 + 2 * (row - 1)]);
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
        
        setCurrentPiece(new ImageIcon("./assets/0110.png"));
        currentPieceType = "0110";
    }
    
    /**
     * Makes the GUI visible.
     */
    public void start() {
    	setVisible(true);
    }

    /**
     * Adds all the necessary components to the content pane of the JFrame.
     */
    private void addComponentsToPane(Container contentPane) {
        GridLayout gridLayout1 = new GridLayout(WIDTH, HEIGHT);
        GridLayout gridLayout2 = new GridLayout(8, 2);
        centerPanel.setLayout(gridLayout1);
        westPanel.setLayout(gridLayout2);

        addPanelsAndLabels();
        
        contentPane.add(centerPanel, BorderLayout.CENTER);
        contentPane.add(southPanel, BorderLayout.SOUTH);
        contentPane.add(westPanel, BorderLayout.WEST);
        
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        westPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        contentPane.add(eastPanel, BorderLayout.EAST);
        eastPanel.setLayout(new GridLayout(0, 1, 0, 0));
        txtrmessageCenter.setBackground(Color.LIGHT_GRAY);
        txtrmessageCenter.setEditable(false);
        
        eastPanel.add(txtrmessageCenter);
        
        try {
			currentPiecePanel = new ImagePanel(ImageIO.read(new File("./assets/bg1.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
        eastPanel.add(currentPiecePanel);
        currentPiecePanel.add(currentPieceLabel);
       
    }

    private void addPanelsAndLabels() {
        addPanelsAndImages();

        for (int i = 0; i < centerPanels.length; i++) {
            centerLabels[i] = new JLabel();
            centerLabels[i].setName(centerPanels[i].getName());
            
            centerPanels[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent arg0) {
                	if (!canMove) return;
                	
                	// We check if we have a nonempty "current piece" cell. If so, we move that piece to the location
                	// just clicked on.
                	
                	if (currentPieceLabel.getIcon() != null) { 
                		try {
                			String posStr = ((ImagePanel)arg0.getComponent()).getName();
                			
                			for (int s = 0; s < centerLabels.length; s++) {
                        		if (centerLabels[s].getName().equalsIgnoreCase(posStr)) {
                        			if (centerLabels[s].getIcon() != null) {
                        				return;
                        			}
                        		}
                    		}
                			
            				Image img = ImageIO.read(new File("./assets/" + currentPieceType + ".png"));
            				ImageIcon icon = new ImageIcon(img);
            	    		
            	    		addPiece(icon, posStr);
            	    		
            	    		Move move = new Move();
            	    		
            	    		QuartoPiece piece = new QuartoPiece(QuartoPiece.class);
            	    		piece.setType(currentPieceType);
            	    		
            	    		move.piece = piece;
            	    		
            	    		QuartoPosition pos = new QuartoPosition(Character.getNumericValue(posStr.charAt(0)) - 1, Character.getNumericValue(posStr.charAt(1)) - 1);
            	    		
            	    		move.newPosition = pos;
            	    		
            	    		// Remember the move.
            	    		moves.add(move);
            	    		
            	    		clearCurrentPiece();
                		}
                		catch (Exception e) {
                			e.printStackTrace();
                		}
                	}
                }
            });
            
            centerPanels[i].add(centerLabels[i]);
            centerPanel.add(centerPanels[i]);
        }
        
        for (int i = 0; i < westPanels.length; i++) {
            westLabels[i] = new JLabel();
            westLabels[i].setName(westPanels[i].getName());
            
            westPanels[i].add(westLabels[i]);
            westPanel.add(westPanels[i]);
            
            westPanels[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent arg0) {
                	if (!canMove) return;
                	
                	if (currentPieceLabel.getIcon() == null) {
                		// Take the piece just selected and make it the current piece.
                		
                		String posStr = ((ImagePanel)arg0.getComponent()).getName();
                		
                		String type = useablePieceMapInverse.get(posStr);
                		
                		// We need to make sure that the cell clicked on was nonempty.
                		
                    	for (int s = 0; s < westLabels.length; s++) {
                    		if (westLabels[s].getName().equalsIgnoreCase(posStr)) {
                    			if (westLabels[s].getIcon() != null) {
                    				// Only in this case do we actually make a move.
                    				
                    				clearUseablePiece(posStr);
                    				
                    				try {
                    					Image img = ImageIO.read(new File("./assets/" + type + ".png"));
                        				ImageIcon icon = new ImageIcon(img);
                        				
                        				setCurrentPiece(icon);
                        				currentPieceType = type;
                        				
                        				Move move = new Move();
                        				
                        				QuartoPiece piece = new QuartoPiece(QuartoPiece.class);
                        				piece.setType(type);
                        				move.piece = piece;
                        				
                        				//QuartoPosition pos = new QuartoPosition(posStr.charAt(0) - 1, posStr.charAt(1) - 1);
                        				QuartoPosition pos = QuartoPosition.getOffboardPosition();
                        				move.newPosition = pos;
                        				
                        				moves.add(move);
                        				
                        				// At this point, we should send off our moves to the model.
                        				
                        				modelAdapter.sendMoves(new ArrayList<Move>(moves));
                        				moves.clear();
                    				}
                    				catch (Exception e) {
                    					e.printStackTrace();
                    				}
                    			}
                    		}
                    	}
                	}
                }
            });
        }
    }

    private void addPanelsAndImages() {
        int count = 0;

        for (int row = 1; row <= WIDTH; row++) {
            for (int col = 1; col <= HEIGHT; col++) {
                if ((col + row) % 2 == 0) {
                    centerPanels[count] = new ImagePanel(boardImage1);
                } else {
                    centerPanels[count] = new ImagePanel(boardImage2);
                }

                centerPanels[count].setName(locationString(col, row));
                count++;
            }
        }
        
        count = 0;
        for (int row = 1; row <= 8; ++row) {
        	for (int col = 1; col <= 2; ++col) {
                if ((col + row) % 2 == 0) {
                    westPanels[count] = new ImagePanel(boardImage1);
                } else {
                    westPanels[count] = new ImagePanel(boardImage2);
                }

                westPanels[count].setName(locationString(col, row));
                count++;
        	}
        }
    }

    public void addPiece(ImageIcon img, String block) {
        for (int s = 0; s < centerLabels.length; s++) {
            if (centerLabels[s].getName().equalsIgnoreCase(block)) {
                centerLabels[s].setIcon(img);
            }
        }
    }
    
    private void addUseablePiece(ImageIcon img, String block) {
    	for (int s = 0; s < westLabels.length; s++) {
    		if (westLabels[s].getName().equalsIgnoreCase(block)) {
    			westLabels[s].setIcon(img);
    		}
    	}
    }
    
    private void setCurrentPiece(ImageIcon img) {
    	currentPieceLabel.setIcon(img);
    }
    
    private void clearPiece(String loc) {
    	addPiece(null, loc);
    }
    
    private void clearUseablePiece(String loc) {
    	addUseablePiece(null, loc);
    }
    
    private void clearCurrentPiece() {
    	setCurrentPiece(null);
    }

    private String locationString(int x, int y) {
    	return Integer.toString(x) + Integer.toString(y);
    }
    
    private class ImagePanel extends JPanel {
		private static final long serialVersionUID = 8607994476808041505L;
		private Image image;

        public ImagePanel(Image img) {
            image = img;
        }

        @Override
        protected void paintComponent(Graphics g) {
            g.drawImage(image, 0, 0, null);
        }
    }
}