package model;

public interface IPosition {
	public int getX();
	public int getY();
	
	public boolean isInUseablePool();
	
	public IPosition getUseablePoolPosition();
}
