package model;

import visitors.AExtVisitorHost;

/**
 * Each piece is a host for an extended visitor. The visitor when acting on a piece determines the
 * set of possible moves that the piece can make and thereby produces for each such move a new
 * state of the game.
 * @author zpconn
 *
 */
public abstract class IPiece extends AExtVisitorHost<Class<?>, IPiece> {
	public IPiece(Class<?> idx) {
		super(idx);
	}

	/**
	 * Each piece has a string identifier. This is used by the view to isolate the appropriate art asset to
	 * render the piece in the GUI.
	 * @return
	 */
	public abstract String getType();
	
	@Override
	public boolean equals(Object oOther) {
		if(!(oOther instanceof IPiece)) {
			return false;
		}
		IPiece other = (IPiece) oOther;
		return getType().equals(other.getType());
	}
	
	@Override
	public int hashCode() {
		return (IPiece.class.toString()+getType()).hashCode();
	}
}
