/**
 * Class containing information about the camera. 
 * 
 * @author Kyriakos Chatzidimitriou
 * 
 */
public class Camera {
	
	/** View reference point */
	private Point viewRefPoint;
	
	/** Up vector */
	private Vector up;

	/** Look at vector */
	private Vector lookAt;

	/** Horizontal angle */
	private double horAngle;

	/** Vertical angle */
	private double verAngle;

	/** Number of rows */
	private int nrows;

	/** Number of columns */
	private int ncols;
	
	public Camera(){}

	/** Create a camera, given the necessary information */
	public Camera(Point vrp, Vector u, Vector l, 
			double ha, double va, int nr, int nc) {
		viewRefPoint = vrp;
		up = u;
		lookAt = l;
		horAngle = ha;
		verAngle = va;
		nrows = nr;
		ncols = nc;
	}
	
	/** 
	 * Utility function for calculating the Projection Reference Point (PRP),
	 * given that our focal length is 1.
	 * 
	 * @return Point the PRP
	 */
	public Point getPRP() {
		if(!lookAt.isUnit())
			lookAt.normalize();
		return new Point(viewRefPoint.getX() - lookAt.getX(), 
				viewRefPoint.getY() - lookAt.getY(), 
				viewRefPoint.getZ() - lookAt.getZ());
	}
	
	
	//============================================================
	// From here on, get and set methods.
	//============================================================
	
	public int getNumOfRows() {
		return nrows;
	}
	
	public int getNumOfCols() {
		return ncols;
	}
	
	public double getHorAngle() {
		return horAngle;
	}
	
	public double getVerAngle() {
		return verAngle;
	}
	
	public Point getVRP() {
		return viewRefPoint;
	}

	public Vector getVPN() {
		return lookAt;
	}
	
	public Vector getVUP() {
		return up;
	}
	
}
