/**
 * Class modelling point light sources. 
 * 
 * @author Kyriakos Chatzidimitriou
 * 
 */
public class Light {
	
	/** Light position */
	private Point position;
	
	/** Light color */
	private Color color;
	
	public Light(){}
	
	/** Create a light source */
	public Light(Point aposition, Color acolor) {
		position = aposition;
		color = acolor;
	}
	
	//============================================================
	// From here on, get and set methods.
	//============================================================
	
	public Point getPosition() {
		return position;
	}
	
	public Color getColor() {
		return color;
	}

}
