/**
 * Class containing information about a scene object. 
 * 
 * @author Kyriakos Chatzidimitriou
 * 
 */
public class SceneObject {
	
	/** Kd */
	protected Color diffuse;
	
	/** Ks */
	protected Color specular;

	/** Kr */
	protected Color reflection;
	
	/** Kt */
	protected Color translucent;
	
	/** Phong factor.*/
	protected double shininess;
	
	/** Refraction coefficient. */
	protected double eta;
	
	/** Id of the object. */
	protected int id;
	
	/** Type 0 for spheres, 1 for triangles. */
	protected int type;
	
	public static final int SPHERE = 0;
	
	public static final int TRIANGLE = 1;
	
	public Vector normal(Point p, boolean inside) {
		return null;
	};
	
	//============================================================
	// From here on, get and set methods.
	//============================================================
	
	public Color getDiffuse() {
		return diffuse;
	}
	
	public Color getSpecular() {
		return specular;
	}
	
	public Color getTrans() {
		return translucent;
	}
	
	public Color getRef() {
		return reflection;
	}
	
	public double getPhong() {
		return shininess;
	}
	
	public double getEta() {
		return eta;
	}
	
	public void setId(int aid) {
		id = aid;
	}
	
	public int getId() {
		return id;
	}
	
	public void setType(int atype) {
		type = atype;
	}
	
	public int getType() {
		return type;
	}

}
