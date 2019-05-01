import java.util.ArrayList;
import java.io.*;
import java.awt.image.BufferedImage;

/**
 * Class for doing rendering using ray tracing.
 * 
 * @author Kyriakos Chatzidimitriou
 * 
 */
public class Renderer {
	
	/** Prespecified depth, in case it is not defined in the input file.*/
	public static int depth = 3;
	
	/** Render a scene and output to an image file */
	public void renderScene(Scene scene, String filename) throws IOException {
		
		// Get the number of rows
		int nrows = scene.getNumOfRows();
		int ncols = scene.getNumOfCols();
	
		// Find field of view limits
		double widthFrom = Math.tan(Math.toRadians(scene.getHorAngle()/2.0));
		double widthTo = -widthFrom;
		double heightFrom = Math.tan(Math.toRadians(scene.getVerAngle()/2.0));
		double heightTo = -heightFrom;
		double widthRes = (widthFrom - widthTo) / (double) ncols;
		double heightRes = (heightFrom - heightTo) / (double) nrows;
		// Calculate the camera axes
		Vector vpn = scene.getVPN().normalize();
		Vector vup = scene.getVUP().normalize();
		Vector u = MathUtils.crossProduct(vup, vpn).normalize();
		Vector v = MathUtils.crossProduct(vpn, u).normalize();
		Color[][] pixels = new Color[nrows][ncols];
		
		// Variables for keeping track of the progress
		int progress = 0;
		int total = nrows * ncols;
		int step = (int)(0.1 * (double)total);
		int output = 10;
		System.out.print("Progress:");
		
		// For each pixel
		for(int i = 0; i < nrows; i++) {
			double b = heightFrom - (double)i * heightRes;
			for(int j = 0; j < ncols; j++) {
				// Find the points in 3D of the pixels
				double a = widthFrom - (double)j * widthRes;	
				Point pixel = calcPixelPoint(scene.getVRP(), u, v, a, b);
				// Find direction and create ray
				Vector direction = MathUtils.subtractPoints(pixel, scene.getPRP());
				Ray ray = new Ray(pixel, direction.normalize());
				// Cast and trace the ray
				pixels[i][j] = traceRay(ray, scene);	
				progress++;
				if(progress % step == 0) {
					System.out.print("." + output + "%");
					output +=10;
				}
			}
		}
		System.out.print(".Done\n");
		
		//  Output image in Portable Bitmap Format and PNG
        BufferedImage bi = new BufferedImage(ncols, nrows, BufferedImage.TYPE_INT_RGB);
		File file = new File(filename);
		FileWriter writer = new FileWriter(file + ".ppm");
        File filePNG = new File(file + ".png");
		writer.write("P3 " + ncols + " " + nrows + " 255\n");
		for(int i = 0; i < nrows; i++) {
			for(int j = 0; j < ncols; j++) {
				writer.write(pixels[i][j].toPPM());
				int r = pixels[i][j].getScaledRed();
				int g = pixels[i][j].getScaledGreen();
				int b = pixels[i][j].getScaledBlue();
				java.awt.Color c = new java.awt.Color(r,g,b);
				bi.setRGB(j, i, (c.getRGB() & 0x00ffffff));
				if(j < (ncols - 1)) {
					writer.write(" ");
				}
			}
			writer.write("\n");			
		}
		writer.close();
		javax.imageio.ImageIO.write(bi, "PNG", filePNG);
	}
	
	/** 
	 * Method for tracing a ray given an instantiated ray and a scene. 
	 * 
	 * @return The color the ray is "seeing".
	 */
	private Color traceRay(Ray ray, Scene scene) {
		
		// Base case: test if max number of depth has been reached
		if(ray.getStep() > depth)
			// If yes return background color (maybe black, not sure which is the best)
			//return scene.getBgdColor();
			return new Color(0,0,0);
		
		// Intersection test
		double t = intersect(ray, scene, 0);
		// Outcome of the intersection
		if(t ==  Double.MAX_VALUE) {
			// If no intersection return background color
			return scene.getBgdColor();
		} else {
			
			// Else
			ray.calcAndSetIntersectPoint(t);
			// Normal at the intersection point
			Vector normal = ray.getSceneObject().normal(ray.getIntersectPoint(), ray.getInside()); 
			Vector v = MathUtils.negateVector(ray.getDirection());
			// Angle of incidence
			double cosAi = MathUtils.dotProduct(normal, v);

			
			// First do recursion steps
			Color reflected = null;
			Color refracted = null;
			// Get the coefficients of reflection and refraction
			Color trans = ray.getSceneObject().getTrans();
			Color ref = ray.getSceneObject().getRef();
			
			// REFLECTION
			if(!ref.isZero()) {	
				// Get the reflected direction and create the ray
				Vector incidence = MathUtils.subtractVectors(
						MathUtils.multiply(normal, 2.0 * cosAi),
						v).normalize();
				Ray reflectedRay = new Ray(ray.getIntersectPoint(), incidence);
				//  Add step
				reflectedRay.setStep(ray.getStep() + 1);
				//  Ray maintains previous state
				reflectedRay.setInside(ray.getInside());
				// Recurse
				reflected = traceRay(reflectedRay, scene);
			}

			// REFRACTION
			if(!trans.isZero() && cosAi > 0) {
				// Assume that the ray always travel in the air
				// i.e. all the objects are hollow.
				double etai = 1.0;
				double etat = ray.getSceneObject().getEta();
				double alpha = etai / etat;
				double beta = alpha * cosAi 
					- Math.sqrt(1 + Math.pow(alpha, 2.0) * (Math.pow(cosAi, 2.0) - 1));
				// Get the refracted direction and create the ray
				Vector refraction = MathUtils.subtractVectors(MathUtils.multiply(ray.getDirection(), alpha),
						MathUtils.negateVector(MathUtils.multiply(normal, beta)));
				Ray refractedRay = new Ray(ray.getIntersectPoint(), refraction);
				//  Add step
				refractedRay.setStep(ray.getStep() + 1);
				//  Ray is inside the object
				refractedRay.setInside(!ray.getInside());
				// Recurse
				refracted = traceRay(refractedRay, scene);
			}
			
			// Add ambient
			Color pixelColor = Color.scale(scene.getAmbColor(), ray.getSceneObject().getDiffuse());
			
			// Add diffuse / specular / shadows
			// for every light source
			for(int i = 0; i < scene.getLights().size(); i++) {
				Light light = (Light)scene.getLights().get(i);
				Point pos = light.getPosition();
				Vector l  = MathUtils.subtractPoints(pos, ray.getIntersectPoint()).normalize();
				
				// SHADOWS
				// Get the point - light distance
				double magnitude = MathUtils.subtractPoints(pos, ray.getIntersectPoint()).getMagnitude();
				// Get the first intersection using a ray
				Ray lightRay = new Ray(ray.getIntersectPoint(), l);
				double s = intersect(lightRay, scene, ray.getSceneObject().getId());
				// Check whether there is an object in between
				if(s < Double.MAX_VALUE) {
					if(((s > MathUtils.ERROR) && (s < magnitude)) && (lightRay.getSceneObject().getId() != ray.getSceneObject().getId())) {
						continue;	
					}
				}
				
				// DIFFUSE
				// Standard diffuse procedure
				double cosTheta = MathUtils.dotProduct(normal, l);
				if(cosTheta > 0) { // If < 0 the light is behind
					Color diffuse = Color.scale(light.getColor(), cosTheta);
					diffuse = Color.scale(diffuse, ray.getSceneObject().getDiffuse());
					pixelColor = Color.add(pixelColor, diffuse);
				}
				
				// SPECULAR WITH PHONG
				// Standard specular procedure
				Vector r = MathUtils.subtractVectors(
						MathUtils.multiply(normal, 2.0 * MathUtils.dotProduct(normal, l)),
						l);
				double cosPhi = MathUtils.dotProduct(r,v);
				if(cosPhi > 0 && cosTheta > 0) {
					double phong = Math.pow(cosPhi, ray.getSceneObject().getPhong());
					Color specular = Color.scale(light.getColor(), phong);
					specular = Color.scale(specular, ray.getSceneObject().getSpecular());
					pixelColor = Color.add(pixelColor, specular);
				}
			}
			// Add reflection color
			if(reflected != null) {
				reflected = Color.scale(reflected, ray.getSceneObject().getSpecular());
				reflected = Color.scale(reflected, ref);
				pixelColor = Color.add(pixelColor, reflected);
			}
			// Add refraction color
			if(refracted != null) {
				refracted = Color.scale(refracted, trans);
				pixelColor = Color.add(pixelColor, refracted);
			}
			// Finally return the color of the pixel
			return pixelColor;
		}
	}
	
	/** 
	 * Utility function to map a point from view plane coordinates 
	 * to 3D world coordinates.
	 */
	private Point calcPixelPoint(Point p, Vector u, Vector v, double a, double b) {
		double x = p.getX() + a * u.getX() + b * v.getX();
		double y = p.getY() + a * u.getY() + b * v.getY();
		double z = p.getZ() + a * u.getZ() + b * v.getZ();
		return new Point(x,y,z);
	}
	
	/**
	 * Wrapper function for checking whether a ray
	 * intersects with spheres or triangles.
	 */
	private double intersect(Ray ray, Scene scene, int id) {
		double t = Double.MAX_VALUE;
		// SPHERES
		ArrayList spheres = scene.getSpheres();
		// Browse through all the spheres
		for(int i = 0; i < spheres.size(); i++) {
			Sphere sphere = (Sphere)spheres.get(i);
			double result = MathUtils.intersect(sphere, ray);
			// NB: sphere.getId() != id
			// A hack for avoiding problems when checking for intersection
			// when dealing with shadows.
			if(result < t && result > MathUtils.ERROR && sphere.getId() != id) {
				t = result;
				ray.setSceneObject(sphere);
			}
		}
		// TRIANGLES
		ArrayList triangles = scene.getTriangles();
		// Browse through all the triangles
		for(int i = 0; i < triangles.size(); i++) {	
			Triangle triangle = (Triangle)triangles.get(i);
			double result = MathUtils.intersect(triangle, ray);
			// NB: triangle.getId() != id
			// A hack for avoiding problems when checking for intersection
			// when dealing with shadows.
			if(result < t && result > MathUtils.ERROR && triangle.getId() != id) {
				t = result;
				ray.setSceneObject(triangle);
			}
		}		
		return t;
	}

}
