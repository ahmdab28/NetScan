package scanner;

/**
 * Immutable data model representing the result of scanning a single port.
 */
public class ScanResult {

    private final int     port;
    private final boolean open;
    private final String  service;
    private final String  banner;

    public ScanResult(int port, boolean open, String service, String banner) {
        this.port    = port;
        this.open    = open;
        this.service = service != null ? service : "unknown";
        this.banner  = banner  != null ? banner  : "";
    }

    public int getPort() {
        return port;
    }

    public boolean isOpen() {
        return open;
    }

    public String getService() {
        return service;
    }

    public String getBanner() {
        return banner;
    }

    @Override
    public String toString() {
        return String.format("Port %d [%s] - %s%s",
                port,
                open ? "OPEN" : "CLOSED",
                service,
                banner.isEmpty() ? "" : " | " + banner);
    }
}
