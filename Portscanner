package scanner;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * PortScanner performs multithreaded TCP port scanning on a target host.
 * Each port is checked concurrently using a fixed thread pool.
 */
public class PortScanner {

    private final String host;
    private final int    startPort;
    private final int    endPort;
    private final int    threadCount;
    private final int    timeoutMs;

    // Thread-safe list to collect results from multiple threads
    private final List<ScanResult> results = Collections.synchronizedList(new ArrayList<>());

    public PortScanner(String host, int startPort, int endPort, int threadCount, int timeoutMs) {
        this.host        = host;
        this.startPort   = startPort;
        this.endPort     = endPort;
        this.threadCount = threadCount;
        this.timeoutMs   = timeoutMs;
    }

    /**
     * Starts the scan and returns all results (open and closed ports).
     * Blocks until all ports have been checked.
     */
    public List<ScanResult> scan() {
        // Resolve host first
        InetAddress address;
        try {
            address = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            System.err.println("[ERROR] Cannot resolve host: " + host);
            return Collections.emptyList();
        }

        System.out.printf("[*] Resolved %s → %s%n", host, address.getHostAddress());
        System.out.printf("[*] Scanning %d ports...%n%n", (endPort - startPort + 1));

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        for (int port = startPort; port <= endPort; port++) {
            final int currentPort = port;
            executor.submit(() -> checkPort(address, currentPort));
        }

        executor.shutdown();
        try {
            // Wait up to 5 minutes for all tasks to finish
            executor.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("[WARN] Scan interrupted before completion.");
        }

        // Sort results by port number for clean output
        results.sort((a, b) -> Integer.compare(a.getPort(), b.getPort()));
        return new ArrayList<>(results);
    }

    /**
     * Attempts a TCP connection to a single port.
     * Records the result (open/closed) with banner grabbing if open.
     */
    private void checkPort(InetAddress address, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(address, port), timeoutMs);

            // Port is open — try to grab a banner
            String banner = BannerGrabber.grab(socket, timeoutMs);
            String service = ServiceIdentifier.identify(port);

            ScanResult result = new ScanResult(port, true, service, banner);
            results.add(result);

            System.out.printf("  [OPEN]  Port %-6d %s%s%n",
                    port,
                    service,
                    banner.isEmpty() ? "" : " | " + banner);

        } catch (IOException e) {
            // Port is closed or filtered — still record it
            results.add(new ScanResult(port, false, ServiceIdentifier.identify(port), ""));
        }
    }
}
