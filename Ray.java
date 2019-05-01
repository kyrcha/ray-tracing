/**
 * Class containing information about a ray. 
 * 
 * @author Kyriakos Chatzidimitriou
 * 
 */
public class Ray {
	
	/** Point of origin. */
	private Point origin;

	/** Direction of the ray. */
	private Vector direction;

	/** The color the ray "sees". */
	private Color currentColor;

	/** Point of intersection of the ray with an object. */
	private Point intersectionPoint;

	/** The object that the ray intersected. */
	private SceneObject obj = null;
	
	/** Keeps track of the depth of the recursion. */ 
	private int step = 1;

	/** Keeps track whether ray inside or outside an object. */
	private boolean inside = false;
	
	public Ray(){}
	
	/** Creates a ray given a point of origin and a direction. */ 
	public Ray(Point aorigin, Vector adirection) {
		origin = aorigin;
		direction = adirection;
	}
	
	/** 
	 * Calculates the intersection point given a distance
	 * and sets it for that specific ray.
	 */
	public void calcAndSetIntersectPoint(double t) {
		double x = origin.getX() + t * direction.getX();
		double y = origin.getY() + t * direction.getY();
		double z = origin.getZ() + t * direction.getZ();
		intersectionPoint  = new Point(x,y,z);
	}
	
	/** 
	 * Calculates an intersection point for the specific ray.
	 */
	public Point calcIntersectPoint(double t) {
		double x = origin.getX() + t * direction.getX();
		double y = origin.getY() + t * direction.getY();
		double z = origin.getZ() + t * direction.getZ();
		return new Point(x,y,z);
	}
	
	//============================================================
	// From here on, get and set methods.
	//============================================================
	
	public Vector getDirection() {
		return direction;
	}
	
	public Point getOrigin() {
		return origin;
	}
	
	public Color getColor() {
		return currentColor;
	}
	
	public void setColor(Color acolor) {
		currentColor = acolor;
	}
	
	public int getStep() {
		return step;
	}
	
	public void setStep(int astep) {
		step = astep;
	}
	
	public boolean getInside() {
		return inside;
	}
	
	public void setInside(boolean ainside) {
		inside = ainside;
	}
	
	public void setIntersectPoint(Point apoint) {
		intersectionPoint = apoint;
	}
	
	public void setSceneObject(SceneObject object) {
		obj = object;
	}
	
	public SceneObject getSceneObject() {
		return obj;
	}
	
	public Point getIntersectPoint() {
		return intersectionPoint;
	}

}
