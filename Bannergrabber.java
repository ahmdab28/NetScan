package scanner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Attempts to grab a service banner from an already-connected socket.
 * A banner is the initial message many services send upon connection
 * (e.g. SSH version string, FTP welcome message, HTTP server header).
 *
 * This is a passive, read-only operation — no data is sent to the server.
 */
public class BannerGrabber {

    private static final int MAX_BANNER_LENGTH = 256;

    /**
     * Reads the first line of data from the socket's input stream.
     *
     * @param socket    An already-connected open socket
     * @param timeoutMs How long to wait for data before giving up
     * @return The banner string, or an empty string if none is available
     */
    public static String grab(Socket socket, int timeoutMs) {
        try {
            socket.setSoTimeout(timeoutMs);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            String line = reader.readLine();
            if (line == null || line.isBlank()) {
                return "";
            }

            // Sanitize: strip non-printable characters
            line = line.replaceAll("[^\\x20-\\x7E]", "").trim();

            // Truncate very long banners
            if (line.length() > MAX_BANNER_LENGTH) {
                line = line.substring(0, MAX_BANNER_LENGTH) + "...";
            }

            return line;

        } catch (Exception e) {
            // Timeout or no banner — that's normal, just return empty
            return "";
        }
    }
}
