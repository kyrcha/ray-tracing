import java.io.IOException;

/**
 * Render a scene using ray tracing. 
 * 
 * @author Kyriakos Chatzidimitriou
 */
public class RayTracing {

	/** Main entry point */
	public static void main(String[] args) throws IOException{
		// Check the number of arguments
		if(args.length != 2) {
			System.out.println("Incorrect number of arguments.");
			System.out.println("2 arguments required:");
			System.out.println("arg0 - input filename");
			System.out.println("arg1 - output filename");
			System.out.println("See README file for further instructions.");
			System.exit(1);
		}
		// Get the two filenames (input and output)
		String inFilename = args[0];
		String outFilename = args[1];
		// Parse the input file and create the models
		System.out.println("Loading scene...");
		Scene scene = new Scene(inFilename);
		// Create a render using ray tracing
		Renderer renderer = new Renderer();
		// Get the time at the beginning
		long begin = System.currentTimeMillis();
		// Render the scene
		System.out.println("Ray tracing scene...");
		renderer.renderScene(scene, outFilename);
		// Output render info
		long end = System.currentTimeMillis();
		System.out.println("Rendering finished after... " 
				+ ((double)(end - begin)/(double)1000) + " seconds");
		System.out.println(scene);
	}

}
