import java.io.*;

/**
 * Utility class for reading files.
 * 
 * @author Kyriakos Chatzidimitriou
 */
public class ExternalFile {
	
	private BufferedReader extFile;
    
	public ExternalFile(String aextFile) throws IOException {
		extFile = new BufferedReader(new InputStreamReader(new FileInputStream(aextFile)));
	}
    
	public String getLine() throws IOException {
		return extFile.readLine();
	}
    
	public boolean havehitEOF() throws IOException {
		return !extFile.ready();
	}

	public void close() throws IOException {
		extFile.close();
	}

}
