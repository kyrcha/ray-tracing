/**
 * Class modeling triangles. 
 * 
 * @author Kyriakos Chatzidimitriou
 * 
 */
public class Triangle extends SceneObject {

	/** First vertex */
	private Point v1;

	/** Second vertex */
	private Point v2;

	/** Third vertex */
	private Point v3;
	
	/** Normal of the plane the triangle resides.*/
	private Vector normal;
	
	/** First triangle edge (v2-v1). */
	private Vector u;

	/** First triangle edge (v3-v1).*/
	private Vector v;

	/** Vector perpendicular to u.*/
	private Vector uPerp;

	/** Vector perpendicular to v.*/
	private Vector vPerp;
	
	//============================================================
	// Precomputed constants for faster ray-triangle intersection.
	//============================================================
	
	private double dConst;
	
	private double sDenom;
	
	private double tDenom;
	
	public Triangle(){}
	
	/** Create a triangle */
	public Triangle(Point av1, Point av2, Point av3, Color d, Color s, 
			Color ref, Color trans, double ashininess, double aeta) {
		v1 = av1;
		v2 = av2;
		v3 = av3;
		diffuse = d;
		specular = s;
		reflection = ref;
		translucent = trans;
		shininess = ashininess;
		eta = aeta;
		// Preprocessing
		u = MathUtils.subtractPoints(v2, v1);
		v = MathUtils.subtractPoints(v3, v1);
		normal = MathUtils.crossProduct(u, v).normalize();
		dConst = -MathUtils.dotProduct(normal, MathUtils.subtractPoints(v1, new Point(0,0,0)));
		uPerp = MathUtils.crossProduct(normal, u);
		vPerp = MathUtils.crossProduct(normal, v);
		sDenom = MathUtils.dotProduct(vPerp, u);
		tDenom = MathUtils.dotProduct(uPerp, v);
	}
	
	public Vector normal(Point p, boolean inside) {
		return normal;
	}
	
	//============================================================
	// From here on, get and set methods.
	//============================================================
	
	public Vector getNormal() {
		return normal;
	}
	
	public Vector getV() {
		return v;
	}
	
	public Vector getU() {
		return u;
	}
	
	public Vector getVPerp() {
		return vPerp;
	}
	
	public Vector getUPerp() {
		return uPerp;
	}
	
	public Point getPoint1() {
		return v1;
	}
	
	public double getDConst() {
		return dConst;
	}
	
	public double getSDenom() {
		return sDenom;
	}
	
	public double getTDenom() {
		return tDenom;
	}
	
}
