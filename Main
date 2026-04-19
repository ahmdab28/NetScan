package main;

import scanner.PortScanner;
import scanner.ScanReport;
import scanner.ScanResult;

import java.util.List;

/**
 * NetScan - A multithreaded Java port scanner
 * Usage: java Main <host> [startPort] [endPort] [threads] [timeoutMs]
 */
public class Main {

    private static final int DEFAULT_START_PORT = 1;
    private static final int DEFAULT_END_PORT   = 1024;
    private static final int DEFAULT_THREADS    = 50;
    private static final int DEFAULT_TIMEOUT_MS = 200;

    public static void main(String[] args) {
        if (args.length < 1) {
            printUsage();
            System.exit(1);
        }

        String host       = args[0];
        int    startPort  = args.length > 1 ? parseInt(args[1], DEFAULT_START_PORT)  : DEFAULT_START_PORT;
        int    endPort    = args.length > 2 ? parseInt(args[2], DEFAULT_END_PORT)    : DEFAULT_END_PORT;
        int    threads    = args.length > 3 ? parseInt(args[3], DEFAULT_THREADS)     : DEFAULT_THREADS;
        int    timeoutMs  = args.length > 4 ? parseInt(args[4], DEFAULT_TIMEOUT_MS)  : DEFAULT_TIMEOUT_MS;

        if (startPort < 1 || endPort > 65535 || startPort > endPort) {
            System.err.println("[ERROR] Invalid port range. Ports must be between 1 and 65535.");
            System.exit(1);
        }

        System.out.println("=========================================");
        System.out.println("  NetScan - Java Port Scanner");
        System.out.println("=========================================");
        System.out.printf("  Target  : %s%n", host);
        System.out.printf("  Ports   : %d - %d%n", startPort, endPort);
        System.out.printf("  Threads : %d%n", threads);
        System.out.printf("  Timeout : %d ms%n", timeoutMs);
        System.out.println("=========================================\n");

        PortScanner scanner = new PortScanner(host, startPort, endPort, threads, timeoutMs);

        long start = System.currentTimeMillis();
        List<ScanResult> results = scanner.scan();
        long elapsed = System.currentTimeMillis() - start;

        ScanReport report = new ScanReport(host, results, elapsed);
        report.print();
    }

    private static int parseInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.err.printf("[WARN] Invalid number '%s', using default: %d%n", value, defaultValue);
            return defaultValue;
        }
    }

    private static void printUsage() {
        System.out.println("Usage: java main.Main <host> [startPort] [endPort] [threads] [timeoutMs]");
        System.out.println();
        System.out.println("  host       : Target hostname or IP address (required)");
        System.out.println("  startPort  : First port to scan (default: 1)");
        System.out.println("  endPort    : Last port to scan  (default: 1024)");
        System.out.println("  threads    : Thread pool size   (default: 50)");
        System.out.println("  timeoutMs  : Socket timeout ms  (default: 200)");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  java main.Main scanme.nmap.org");
        System.out.println("  java main.Main 192.168.1.1 1 65535 100 300");
    }
}
