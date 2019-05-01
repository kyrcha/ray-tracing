/**
 * Class maintaining color information using the RGB model. 
 * 
 * @author Kyriakos Chatzidimitriou
 * 
 */
public class Color {
	
	/** Maximum possible value for a color */
	private final static double MAX = 255.0;
	
	/** The red component */
	private double red;

	/** The green component */
	private double green;
	
	/** The blue component */
	private double blue;
	
	public Color() {}

	/** Create a color given R, G and B */
	public Color(double r, double g, double b) {
		red = r;
		green = g;
		blue = b;
	}

	/** Create a color given another color */
	public Color(Color a) {
		red = a.getRed();
		green = a.getGreen();
		blue = a.getBlue();
	}
	
	/** Method used for printing the color components in the console */
	public String toString() {
		return red + " " + green + " " + blue;  
	}
	
	/** 
	 * Method used for outputing the components to a PBF file format, 
	 * after scaling.
	 */
	public String toPPM() {
		return getScaledRed() + " " + getScaledGreen() + " " + getScaledBlue();  
	}
	
	/** 
	 * Adding color.
	 */
	public static Color add(Color a, Color b) {
		return new Color(a.getRed() + b.getRed(),
				a.getGreen() + b.getGreen(),
				a.getBlue() + b.getBlue());
	}

	/** 
	 * Scaling a color, with different factors for RGB respectivelly.
	 */
	public static Color scale(Color a, Color b) {
		return new Color(b.getRed() * a.getRed(),
				b.getGreen() * a.getGreen(),
				b.getBlue() * a.getBlue());
	}
	
	/** 
	 * Scaling a color, with the same factor for RGB.
	 */
	public static Color scale(Color a, double b) {
		return new Color(b * a.getRed(),
				b * a.getGreen(),
				b * a.getBlue());
	}
	
	/** 
	 * Checking whether a color has all its components to zero.
	 */
	public boolean isZero() {
		if((red + green + blue) == 0.0)
			return true;
		return false;
	}
	
	//============================================================
	// From here on, get and set methods.
	// If a value is above 1.0 (i.e. 255 after scaling) a cut-off
	// applies equal to MAX (in our case 255).
	//============================================================
	
	public void setRed(double r) {
		red = r;
	}
	
	public double getRed() {
		return red;
	}
	
	public int getScaledRed() {
		return (int) Math.min(MAX, red * MAX);
	}
	
	public void setGreen(double g) {
		green = g;
	}
	
	public double getGreen() {
		return green;
	}
	
	public int getScaledGreen() {
		return (int) Math.min(MAX, green * MAX);
	}
	
	public void setBlue(double b) {
		blue = b;
	}
	
	public double getBlue() {
		return blue;
	}
	
	public int getScaledBlue() {
		return (int) Math.min(MAX, blue * MAX);
	}

}
