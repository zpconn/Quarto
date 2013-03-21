package model;

/**
 * This is the representation of board positions specifically for the game of Quarto.
 * @author zpconn
 *
 */
public class QuartoPosition implements IPosition {
	private int x;
	private int y;
	private boolean isOffboard = false;
	private boolean isInUseablePool = false;
	
	private static QuartoPosition offboardPosition = makeOffboardPosition();
	
	public QuartoPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	public boolean isOffboard() { return isOffboard; }
	public boolean isInUseablePool() { return isInUseablePool; }
	
	/**
	 * Always use this for producing the offboard position.
	 */
	private static QuartoPosition makeOffboardPosition() {
		QuartoPosition offboard = new QuartoPosition(-1, -1);
		offboard.isOffboard = true;
		
		return offboard;
	}
	
	public static QuartoPosition getOffboardPosition() {
		return offboardPosition;
	}
	
	public QuartoPosition getUseablePoolPosition() {
		QuartoPosition useable = new QuartoPosition(-1, -1);
		useable.isInUseablePool = true;
		
		return useable;
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof QuartoPosition)) {
			return false;
		}
		QuartoPosition qOther = (QuartoPosition) other;
		if (this.isOffboard && qOther.isOffboard()) {
			return true;
		}
		
		return (x == qOther.getX()) && (y == qOther.getY());
	}
	
	@Override
	public int hashCode() {
		String uniqueString = this.getClass().toString()+Integer.toString(x)+Integer.toString(y)+Boolean.toString(isInUseablePool)+Boolean.toString(isOffboard);
		return uniqueString.hashCode();
	}
}
