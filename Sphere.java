/**
 * Class modeling spheres. 
 * 
 * @author Kyriakos Chatzidimitriou
 * 
 */
public class Sphere extends SceneObject {
	
	/** Center of the sphere.*/
	private Point center;
	
	/** Radius of the sphere.*/
	private double radius;
	
	public Sphere(){}
	
	/** Create a sphere. */
	public Sphere(Point acenter, double r, Color d, Color s, 
			Color ref, Color trans, double ashininess, double aeta) {
		center = acenter;
		radius = r;
		diffuse = d;
		specular = s;
		reflection = ref;
		translucent = trans;
		shininess = ashininess;
		eta = aeta;
	}
	
	/** 
	 * Calculate the normal of the sphere givena point (on the sphere)
	 * and whether it is inside or outside.
	 * 
	 * @return The normal. 
	 */
	public Vector normal(Point p, boolean inside) {
		if(!inside) {
			double x = (p.getX() - center.getX()) / radius;
			double y = (p.getY() - center.getY()) / radius;
			double z = (p.getZ() - center.getZ()) / radius;
			return new Vector(x,y,z);
		} else {
			double x = (center.getX() - p.getX()) / radius;
			double y = (center.getY() - p.getY()) / radius;
			double z = (center.getZ() - p.getZ()) / radius;
			return new Vector(x,y,z);
		}
	}
	
	//============================================================
	// From here on, get and set methods.
	//============================================================
	
	public double getRadius() {
		return radius;
	}

	public Point getCenter() {
		return center;
	}
	
}
