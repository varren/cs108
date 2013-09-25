import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WebWorker extends Thread {
    private String urlString;
    private int rowToUpdate;
    private WebFrame frame;
    private String resultStatus;
    private static final String ERROR = "err";
    private static final String INTERRUPTED = "interrupted";

    public WebWorker(String urlString, int rowToUpdate, WebFrame frame) {
        this.urlString = urlString;
        this.rowToUpdate = rowToUpdate;
        this.frame = frame;
    }

    public void run() {
        download();
        frame.releaseWorker(resultStatus, rowToUpdate);
    }

    public void download() {
        //This is the core web/download i/o code...

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

            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            char[] array = new char[1000];
            long startTime = System.currentTimeMillis();


            int size = 0;
            int len;
            contents = new StringBuilder(1000);
            while ((len = reader.read(array, 0, array.length)) > 0) {
                contents.append(array, 0, len);
                Thread.sleep(100);
                size += len;
            }
            long endTime = System.currentTimeMillis();


            // Successful download if we get here

            resultStatus = new SimpleDateFormat("HH:mm:ss").format(new Date(startTime))
                    + "   " + (endTime - startTime)
                    + "ms   " + size + "bytes";
        }
        // Otherwise control jumps to a catch...
        catch (MalformedURLException ignored) {
            resultStatus = ERROR;
        } catch (InterruptedException exception) {
            resultStatus = INTERRUPTED;
        } catch (IOException ignored) {
            resultStatus = ERROR;
        }
        // "finally" clause, to close the input stream
        // in any case
        finally {
            try {
                if (input != null) input.close();
            } catch (IOException ignored) {
            }
        }
    }
}
