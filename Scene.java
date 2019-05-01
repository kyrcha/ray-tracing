import java.util.ArrayList;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Class containing all the necessary elements of a scene i.e.
 * lights, camera, objects, background. 
 * 
 * Author: Kyriakos Chatzidimitriou
 * 
 */
public class Scene {
	
	//============================================================
	// Constants.
	//============================================================
	
	private static final String LIGHT = "LIGHT";
	
	private static final String CAMERA = "CAMERA";
	
	private static final String SPHERE = "SPHERE";
	
	private static final String TRIANGLE = "TRIANGLE";
	
	private static final String AMBIENT = "AMBIENT";
	
	private static final String BACKGROUND = "BACKGROUND";
	
	private static final String MAX_DEPTH = "MAX_DEPTH";
	
	//============================================================
	// Scene definition variables.
	//============================================================
	
	private Camera cam;
	
	private ArrayList lights = new ArrayList();
	
	private ArrayList spheres = new ArrayList();
	
	private ArrayList triangles = new ArrayList();
	
	private Color amb;
	
	private Color bgd;
	
	/** The constructor method parses a scene given an input file */
	public Scene(String filename) throws IOException {
		// Read scene: If a line corresponds to one of the predefined
		// possible objects, parse it.
		ExternalFile extFile = new ExternalFile(filename);
		String line;
		int counter = 1;
		do {
            line = extFile.getLine();
            if(line.equalsIgnoreCase(AMBIENT)) {
            	line = extFile.getLine();
            	amb = parseColor(line);
            } else if(line.equalsIgnoreCase(BACKGROUND)) {
            	line = extFile.getLine();
            	bgd = parseColor(line);
            } else if(line.equalsIgnoreCase(LIGHT)) {
            	line = extFile.getLine();
            	Point p = parsePoint(line);
            	line = extFile.getLine();
            	Color c = parseColor(line);
            	Light light = new Light(p,c);
            	lights.add(light);
            } else if(line.equalsIgnoreCase(SPHERE)) {
            	line = extFile.getLine();
            	Point p = parsePoint(line);
            	line = extFile.getLine();
            	StringTokenizer data = new StringTokenizer(line, " ");
            	double r = Double.parseDouble(data.nextToken().trim());
            	line = extFile.getLine();
            	Color d = parseColor(line);
            	line = extFile.getLine();
            	Color s = parseColor(line);
            	line = extFile.getLine();
            	Color ref = parseColor(line);
            	line = extFile.getLine();
            	Color t = parseColor(line);
            	line = extFile.getLine();
            	data = new StringTokenizer(line, " ");
            	double eta = Double.parseDouble(data.nextToken().trim());
            	line = extFile.getLine();
            	data = new StringTokenizer(line, " ");
            	double sh = Double.parseDouble(data.nextToken().trim());
            	Sphere sphere = new Sphere(p, r, d, s, ref, t, sh, eta);
            	sphere.setId(counter);
            	sphere.setType(SceneObject.SPHERE);
            	counter++;
            	spheres.add(sphere);
            } else if(line.equalsIgnoreCase(TRIANGLE)) {
            	line = extFile.getLine();
            	Point p1 = parsePoint(line);
            	line = extFile.getLine();
            	Point p2 = parsePoint(line);
            	line = extFile.getLine();
            	Point p3 = parsePoint(line);
            	line = extFile.getLine();
            	Color d= parseColor(line);
            	line = extFile.getLine();
            	Color s = parseColor(line);
            	line = extFile.getLine();
            	Color r = parseColor(line);
            	line = extFile.getLine();
            	Color t = parseColor(line);
            	line = extFile.getLine();
            	StringTokenizer data = new StringTokenizer(line, " ");
            	double eta = Double.parseDouble(data.nextToken().trim());
            	line = extFile.getLine();
            	data = new StringTokenizer(line, " ");
            	double sh = Double.parseDouble(data.nextToken().trim());
            	Triangle triangle = new Triangle(p1, p2, p3, d, s, r, t, sh, eta);
            	triangle.setId(counter);
            	triangle.setType(SceneObject.TRIANGLE);
            	counter++;
            	triangles.add(triangle);
            } else if(line.equalsIgnoreCase(CAMERA)) {
            	line = extFile.getLine();
            	Point p = parsePoint(line);
            	line = extFile.getLine();
            	Vector u  = parseVector(line);
            	line = extFile.getLine();
            	Vector l = parseVector(line);
            	line = extFile.getLine();
            	StringTokenizer data = new StringTokenizer(line, " ");
            	double ha = Double.parseDouble(data.nextToken().trim());
            	double va = Double.parseDouble(data.nextToken().trim());
            	line = extFile.getLine();
            	data = new StringTokenizer(line, " ");
            	int nc = Integer.parseInt(data.nextToken().trim());
            	int nr = Integer.parseInt(data.nextToken().trim());
            	cam = new Camera(p, u, l, ha, va, nr, nc);
            } else if(line.equalsIgnoreCase(MAX_DEPTH)) {
            	line = extFile.getLine();
            	StringTokenizer data = new StringTokenizer(line, " ");
            	Renderer.depth = Integer.parseInt(data.nextToken().trim());
            }
        } while(!extFile.havehitEOF());
		extFile.close();
	}
	
	/**
	 * Utility function that parses a line containing color information.
	 */
	private Color parseColor(String line) {
    	StringTokenizer data = new StringTokenizer(line, " ");
    	double r,g,b;
    	r = Double.parseDouble(data.nextToken().trim());
    	g = Double.parseDouble(data.nextToken().trim());
    	b = Double.parseDouble(data.nextToken().trim());
    	return new Color(r,g,b);
	}
	
	/**
	 * Utility function that parses a line containing point information.
	 */
	private Point parsePoint(String line) {
    	StringTokenizer data = new StringTokenizer(line, " ");
    	double x,y,z;
    	x = Double.parseDouble(data.nextToken().trim());
    	y = Double.parseDouble(data.nextToken().trim());
    	z = Double.parseDouble(data.nextToken().trim());
    	return new Point(x,y,z);
	}
	
	/**
	 * Utility function that parses a line containing vector information.
	 */
	private Vector parseVector(String line) {
    	StringTokenizer data = new StringTokenizer(line, " ");
    	double x,y,z;
    	x = Double.parseDouble(data.nextToken().trim());
    	y = Double.parseDouble(data.nextToken().trim());
    	z = Double.parseDouble(data.nextToken().trim());
    	return new Vector(x,y,z);
	}
	
	/**
	 * Displays scene info to console.
	 */
	public String toString() {
		String info = "Scene Info:\n";
		info += "Number of triangles: " + triangles.size() + "\n";
		info += "Number of spheres: " + spheres.size() + "\n";
		info += "Number of light sources: " + lights.size();
		return info;
	}
	
	//============================================================
	// From here on, get and set methods.
	//============================================================
	
	public int getNumOfRows() {
		return cam.getNumOfRows();
	}
	
	public int getNumOfCols() {
		return cam.getNumOfCols();
	}
	
	public double getHorAngle() {
		return cam.getHorAngle();
	}
	
	public double getVerAngle() {
		return cam.getVerAngle();
	}

	public Point getPRP() {
		return cam.getPRP();
	}
	
	public Vector getVPN() {
		return cam.getVPN();
	}
	
	public Vector getVUP() {
		return cam.getVUP();
	}
	
	public Point getVRP() {
		return cam.getVRP();
	}
	
	public Color getBgdColor() {
		return bgd;
	}
	
	public Color getAmbColor() {
		return amb;
	}
	
	public int getNumOfObjects() {
		return triangles.size() + spheres.size();
	}
	
	public ArrayList getSpheres() {
		return spheres;
	}
	
	public ArrayList getTriangles() {
		return triangles;
	}
	
	public ArrayList getLights() {
		return lights;
	}
	
}
