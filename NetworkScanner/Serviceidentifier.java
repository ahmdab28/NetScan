package scanner;

import java.util.HashMap;
import java.util.Map;

/**
 * Maps well-known port numbers to their associated service names.
 * Based on IANA service name and transport protocol port number registry.
 */
public class ServiceIdentifier {

    private static final Map<Integer, String> SERVICE_MAP = new HashMap<>();

    static {
        // --- Application Layer ---
        SERVICE_MAP.put(20,   "FTP (data)");
        SERVICE_MAP.put(21,   "FTP (control)");
        SERVICE_MAP.put(22,   "SSH");
        SERVICE_MAP.put(23,   "Telnet");
        SERVICE_MAP.put(25,   "SMTP");
        SERVICE_MAP.put(53,   "DNS");
        SERVICE_MAP.put(67,   "DHCP (server)");
        SERVICE_MAP.put(68,   "DHCP (client)");
        SERVICE_MAP.put(69,   "TFTP");
        SERVICE_MAP.put(80,   "HTTP");
        SERVICE_MAP.put(88,   "Kerberos");
        SERVICE_MAP.put(110,  "POP3");
        SERVICE_MAP.put(119,  "NNTP");
        SERVICE_MAP.put(123,  "NTP");
        SERVICE_MAP.put(135,  "MS RPC");
        SERVICE_MAP.put(137,  "NetBIOS Name");
        SERVICE_MAP.put(138,  "NetBIOS Datagram");
        SERVICE_MAP.put(139,  "NetBIOS Session");
        SERVICE_MAP.put(143,  "IMAP");
        SERVICE_MAP.put(161,  "SNMP");
        SERVICE_MAP.put(162,  "SNMP Trap");
        SERVICE_MAP.put(194,  "IRC");
        SERVICE_MAP.put(389,  "LDAP");
        SERVICE_MAP.put(443,  "HTTPS");
        SERVICE_MAP.put(445,  "SMB");
        SERVICE_MAP.put(465,  "SMTPS");
        SERVICE_MAP.put(514,  "Syslog");
        SERVICE_MAP.put(587,  "SMTP (submission)");
        SERVICE_MAP.put(636,  "LDAPS");
        SERVICE_MAP.put(993,  "IMAPS");
        SERVICE_MAP.put(995,  "POP3S");

        // --- Database ---
        SERVICE_MAP.put(1433, "MS SQL Server");
        SERVICE_MAP.put(1521, "Oracle DB");
        SERVICE_MAP.put(3306, "MySQL");
        SERVICE_MAP.put(5432, "PostgreSQL");
        SERVICE_MAP.put(5984, "CouchDB");
        SERVICE_MAP.put(6379, "Redis");
        SERVICE_MAP.put(9042, "Cassandra");
        SERVICE_MAP.put(27017,"MongoDB");

        // --- Remote Access & Virtualization ---
        SERVICE_MAP.put(3389, "RDP");
        SERVICE_MAP.put(5900, "VNC");
        SERVICE_MAP.put(5901, "VNC (display 1)");

        // --- Web & Proxies ---
        SERVICE_MAP.put(8080, "HTTP (alt)");
        SERVICE_MAP.put(8443, "HTTPS (alt)");
        SERVICE_MAP.put(8888, "HTTP (dev)");
        SERVICE_MAP.put(9090, "Prometheus / HTTP (alt)");
        SERVICE_MAP.put(3000, "Node.js / React dev");
        SERVICE_MAP.put(4200, "Angular dev");
        SERVICE_MAP.put(5000, "Flask / dev server");

        // --- DevOps & Containers ---
        SERVICE_MAP.put(2375, "Docker (unsecured)");
        SERVICE_MAP.put(2376, "Docker (TLS)");
        SERVICE_MAP.put(2379, "etcd (client)");
        SERVICE_MAP.put(2380, "etcd (peer)");
        SERVICE_MAP.put(6443, "Kubernetes API");
        SERVICE_MAP.put(10250,"Kubernetes kubelet");

        // --- Mail ---
        SERVICE_MAP.put(25,   "SMTP");
        SERVICE_MAP.put(2525, "SMTP (alt)");

        // --- File Transfer ---
        SERVICE_MAP.put(2049, "NFS");

        // --- Misc ---
        SERVICE_MAP.put(179,  "BGP");
        SERVICE_MAP.put(500,  "IKE / IPSec");
        SERVICE_MAP.put(1194, "OpenVPN");
        SERVICE_MAP.put(1701, "L2TP");
        SERVICE_MAP.put(1723, "PPTP");
        SERVICE_MAP.put(4444, "Metasploit default");
        SERVICE_MAP.put(31337,"Back Orifice (trojan)");
    }

    /**
     * Returns the service name for a given port number,
     * or "unknown" if the port is not in the registry.
     */
    public static String identify(int port) {
        return SERVICE_MAP.getOrDefault(port, "unknown");
    }

    /**
     * Returns true if the port has a known associated service.
     */
    public static boolean isKnown(int port) {
        return SERVICE_MAP.containsKey(port);
    }
}
