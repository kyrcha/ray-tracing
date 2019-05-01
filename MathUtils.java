/**
 * Class containing the necessary math function, mainly performing operations
 * between vectors and points in 3D. 
 * 
 * @author Kyriakos Chatzidimitriou
 * 
 */
public class MathUtils {
	
	/** Threshold fo precision errors */
	public static final double ERROR = 10E-14;
	
	/** 
	 * Cross product, aka outer product between two vectors, producing
	 * a third vector. 
	 */
	public static Vector crossProduct(Vector v1, Vector v2) {
		double x = (v1.getY() * v2.getZ()) - (v1.getZ() * v2.getY());
		double y = (v1.getZ() * v2.getX()) - (v1.getX() * v2.getZ());
		double z = (v1.getX() * v2.getY()) - (v1.getY() * v2.getX());
		return new Vector(x,y,z);
	}

	/** 
	 * Dot product, aka inner product between two vectors, producing
	 * a scalar. 
	 */
	public static double dotProduct(Vector v1, Vector v2) {
		return (v1.getX() * v2.getX()) +
			(v1.getY() * v2.getY()) +
			(v1.getZ() * v2.getZ());
	}
	
	/** 
	 * Subtract two points to produce a vector. 
	 */
	public static Vector subtractPoints(Point p1, Point p2) {
		double x = p1.getX() - p2.getX();
		double y = p1.getY() - p2.getY();
		double z = p1.getZ() - p2.getZ();
		return new Vector(x,y,z);
	}
	
	/** 
	 * Negate a vector.  
	 */
	public static Vector negateVector(Vector v) {
		return new Vector(-v.getX(), -v.getY(), -v.getZ());
	}
	
	/** 
	 * Scale a vector, by a factor.  
	 */
	public static Vector multiply(Vector v, double factor) {
		return new Vector(factor * v.getX(), factor * v.getY(), factor * v.getZ());
	}
	
	/** 
	 * Subtract two vectors to produce a thrid vector.  
	 */
	public static Vector subtractVectors(Vector v1, Vector v2) {
		double x = v1.getX() - v2.getX();
		double y = v1.getY() - v2.getY();
		double z = v1.getZ() - v2.getZ();
		return new Vector(x,y,z);
	}
	
	/** 
	 * Ray - Sphere intersection.
	 * 
	 * @return Distance from the point the ray originated to the intersection point.
	 * If there is no intersection MAX_VALUE is returned.
	 */
	public static double intersect(Sphere sphere, Ray ray) {
		Vector centerOrigin = MathUtils.subtractPoints(sphere.getCenter(), ray.getOrigin());
		double v = MathUtils.dotProduct(ray.getDirection(), centerOrigin);
		double disc = Math.pow(sphere.getRadius(), 2.0) -  
						(MathUtils.dotProduct(centerOrigin, centerOrigin) - Math.pow(v,2.0));
		// Checks whether there is an intersection or not.
		if(disc < 0) {
			return Double.MAX_VALUE;
		} else {
			// Checks whether inside or outside the sphere.
			if(!ray.getInside()) {
				return (v - Math.sqrt(disc));
			} else {
				return (v + Math.sqrt(disc));
			}
		}		
	}
	
	/** 
	 * Ray - Triangle intersection.
	 * 
	 * @return Distance from the point the ray originated to the intersection point.
	 * If there is no intersection MAX_VALUE is returned.
	 */
	public static double intersect(Triangle triangle, Ray ray) {
		double den = dotProduct(triangle.getNormal(), ray.getDirection());
		
		// Ray // with triangle ?
		if(den == 0) {
			return Double.MAX_VALUE;
		}
		
		// Step 1 : Calculate the Ray - Plane intersection point.
		double nom = -(dotProduct(triangle.getNormal(), subtractPoints(ray.getOrigin(), new Point(0,0,0)))
				+ triangle.getDConst());
		double s = nom / den;
		Point p = ray.calcIntersectPoint(s);
		
	    // Step 2 : Check whether it is inside the triangle.
		Vector w = subtractPoints(p, triangle.getPoint1());
		double sParam = dotProduct(triangle.getVPerp(), w) / triangle.getSDenom();
	    double tParam = dotProduct(triangle.getUPerp(), w) / triangle.getTDenom();
	    
	    if((sParam >= MathUtils.ERROR) && (tParam >= MathUtils.ERROR) && ((sParam  + tParam) <= 1)) {
	    	return s;
	    } else {
	    	return Double.MAX_VALUE;
	    }
	}
	
}
