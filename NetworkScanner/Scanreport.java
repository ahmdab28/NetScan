package scanner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Aggregates scan results and produces a formatted terminal report.
 */
public class ScanReport {

    private final String           host;
    private final List<ScanResult> results;
    private final long             elapsedMs;

    public ScanReport(String host, List<ScanResult> results, long elapsedMs) {
        this.host      = host;
        this.results   = results;
        this.elapsedMs = elapsedMs;
    }

    /**
     * Prints the full scan report to stdout.
     */
    public void print() {
        List<ScanResult> openPorts = results.stream()
                .filter(ScanResult::isOpen)
                .collect(Collectors.toList());

        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        System.out.println();
        System.out.println("=========================================");
        System.out.println("  SCAN REPORT");
        System.out.println("=========================================");
        System.out.printf("  Host       : %s%n", host);
        System.out.printf("  Timestamp  : %s%n", timestamp);
        System.out.printf("  Ports scan : %d total%n", results.size());
        System.out.printf("  Open ports : %d%n", openPorts.size());
        System.out.printf("  Elapsed    : %.2f seconds%n", elapsedMs / 1000.0);
        System.out.println("=========================================\n");

        if (openPorts.isEmpty()) {
            System.out.println("  No open ports found in the scanned range.");
        } else {
            System.out.printf("  %-8s %-26s %s%n", "PORT", "SERVICE", "BANNER");
            System.out.println("  " + "-".repeat(70));

            for (ScanResult r : openPorts) {
                System.out.printf("  %-8d %-26s %s%n",
                        r.getPort(),
                        r.getService(),
                        r.getBanner().isEmpty() ? "-" : r.getBanner());
            }
        }

        System.out.println();
        printRiskSummary(openPorts);
        System.out.println("\n  Scan complete.");
        System.out.println("=========================================");
    }

    /**
     * Highlights potentially risky open ports found during the scan.
     */
    private void printRiskSummary(List<ScanResult> openPorts) {
        // Ports commonly associated with risk if exposed publicly
        int[] riskyPorts = {21, 23, 135, 137, 138, 139, 445, 3389, 5900,
                            2375, 4444, 31337, 1433, 3306, 5432, 27017};

        List<ScanResult> flagged = openPorts.stream()
                .filter(r -> isRisky(r.getPort(), riskyPorts))
                .collect(Collectors.toList());

        if (!flagged.isEmpty()) {
            System.out.println("  [!] SECURITY NOTICE — Potentially sensitive open ports:");
            for (ScanResult r : flagged) {
                System.out.printf("      Port %-6d (%s) — consider reviewing exposure%n",
                        r.getPort(), r.getService());
            }
        } else {
            System.out.println("  [OK] No obviously high-risk ports detected in open set.");
        }
    }

    private boolean isRisky(int port, int[] riskyPorts) {
        for (int rp : riskyPorts) {
            if (port == rp) return true;
        }
        return false;
    }

    /**
     * Returns only the open port results.
     */
    public List<ScanResult> getOpenPorts() {
        return results.stream()
                .filter(ScanResult::isOpen)
                .collect(Collectors.toList());
    }
}
