import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class WebWorker extends Thread {
/*
  This is the core web/download i/o code...
 		InputStream input = null;
		StringBuilder contents = null;
		try {
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();
		
			// Set connect() to throw an IOException
			// if connection does not succeed in this many msecs.
			connection.setConnectTimeout(5000);
			
			connection.connect();
			input = connection.getInputStream();

			BufferedReader reader  = new BufferedReader(new InputStreamReader(input));
		
			char[] array = new char[1000];
			int len;
			contents = new StringBuilder(1000);
			while ((len = reader.read(array, 0, array.length)) > 0) {
				contents.append(array, 0, len);
				Thread.sleep(100);
			}
			
			// Successful download if we get here
			
		}
		// Otherwise control jumps to a catch...
		catch(MalformedURLException ignored) {}
		catch(InterruptedException exception) {
			// YOUR CODE HERE
			// deal with interruption
		}
		catch(IOException ignored) {}
		// "finally" clause, to close the input stream
		// in any case
		finally {
			try{
				if (input != null) input.close();
			}
			catch(IOException ignored) {}
		}

*/
	
}
