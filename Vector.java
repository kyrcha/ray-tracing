/**
 * Class modeling 3D vectors. 
 * 
 * @author Kyriakos Chatzidimitriou
 * 
 */
public class Vector {
	
	/** x coordinate */
	private double x;
	
	/** y coordinate */
	private double y;
	
	/** z coordinate */
	private double z;
	
	public Vector(){}
	
	/** Create a vector */
	public Vector(double ax, double ay, double az) {
		x = ax;
		y = ay;
		z = az;
	}
	
	/** Normalize a vector i.e. make it unit. */
	public Vector normalize() {
		double magnitude = Math.sqrt(Math.pow(x,2.0) + Math.pow(y,2.0) + Math.pow(z, 2.0));
		return new Vector(x/magnitude, y/magnitude, z/magnitude);
	}

	/** Get the magnitude of a vector. */
	public double getMagnitude() {
		return Math.sqrt(Math.pow(x,2.0) + Math.pow(y,2.0) + Math.pow(z, 2.0));
	}

	/** Check whether the vector is unit, given a precision error. */
	public boolean isUnit() {
		double magnitude = Math.sqrt(Math.pow(x,2.0) + Math.pow(y,2.0) + Math.pow(z, 2.0));
		if(magnitude < (1.0 + MathUtils.ERROR) 
				&& magnitude > (1.0 - MathUtils.ERROR))
			return true;
		return false;
	}
	
	/** For printing the vector's coordinates to the console */
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
