/**
 * Class modeling 3D points. 
 * 
 * @author Kyriakos Chatzidimitriou
 * 
 */
public class Point {
	
	/** x coordinate */
	private double x;

	/** y coordinate */
	private double y;

	/** z coordinate */
	private double z;
	
	public Point(){}

	/** Instantiate a point */
	public Point(double ax, double ay, double az) {
		x = ax;
		y = ay;
		z = az;
	}
	
	/** For printing the point's coordinates to the console */
	public String toString() {
		return x + " " + y + " " +z;  
	}
	
	//============================================================
	// From here on, get and set methods.
	//============================================================

	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
}
