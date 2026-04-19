# NetScan — Java Multithreaded Port Scanner

A lightweight, multithreaded TCP port scanner written in Java. Built with clean OOP design — no external libraries required.

---

## Features

- **Multithreaded** — scans ports concurrently using a configurable thread pool
- **Banner grabbing** — passively reads service banners from open ports (SSH version, FTP welcome, etc.)
- **Service identification** — maps 60+ well-known ports to service names
- **Risk flagging** — highlights potentially sensitive open ports in the report
- **Configurable** — control host, port range, threads, and timeout via CLI args
- **No dependencies** — pure Java standard library only

---

## Project Structure

```
NetworkScanner/
└── src/
    ├── main/
    │   └── Main.java               ← CLI entry point & argument parsing
    └── scanner/
        ├── PortScanner.java        ← Core scanning engine (multithreaded)
        ├── ScanResult.java         ← Immutable data model per port result
        ├── ScanReport.java         ← Aggregates results & prints report
        ├── ServiceIdentifier.java  ← Port → service name lookup table
        └── BannerGrabber.java      ← Reads service banners from open sockets
```

---

## Usage

### Compile

```bash
cd src
javac main/Main.java scanner/*.java
```

### Run

```bash
java main.Main <host> [startPort] [endPort] [threads] [timeoutMs]
```

| Argument     | Description                      | Default |
|--------------|----------------------------------|---------|
| `host`       | Target hostname or IP (required) | —       |
| `startPort`  | First port to scan               | 1       |
| `endPort`    | Last port to scan                | 1024    |
| `threads`    | Concurrent thread count          | 50      |
| `timeoutMs`  | Socket timeout per port (ms)     | 200     |

### Examples

```bash
# Scan well-known ports on a public test host
java main.Main scanme.nmap.org

# Scan a local machine, full port range, 100 threads
java main.Main 192.168.1.1 1 65535 100 300

# Quick scan top 100 ports
java main.Main 10.0.0.5 1 100
```

---

## Sample Output

```
=========================================
  NetScan - Java Port Scanner
=========================================
  Target  : scanme.nmap.org
  Ports   : 1 - 1024
  Threads : 50
  Timeout : 200 ms
=========================================

[*] Resolved scanme.nmap.org → 45.33.32.156
[*] Scanning 1024 ports...

  [OPEN]  Port 22     SSH | SSH-2.0-OpenSSH_6.6.1p1
  [OPEN]  Port 80     HTTP

=========================================
  SCAN REPORT
=========================================
  Host       : scanme.nmap.org
  Timestamp  : 2025-09-01 14:32:11
  Ports scan : 1024 total
  Open ports : 2
  Elapsed    : 3.14 seconds
=========================================

  PORT     SERVICE                    BANNER
  ----------------------------------------------------------------------
  22       SSH                        SSH-2.0-OpenSSH_6.6.1p1
  80       HTTP                       -

  [OK] No obviously high-risk ports detected in open set.

  Scan complete.
=========================================
```

---

## OOP Design

| Class                | Responsibility                                    |
|----------------------|---------------------------------------------------|
| `Main`               | CLI argument parsing and program entry            |
| `PortScanner`        | Thread pool management and socket connection logic|
| `ScanResult`         | Immutable value object for a single port result   |
| `ScanReport`         | Result aggregation, formatting, and risk analysis |
| `ServiceIdentifier`  | Static lookup table (port → service name)         |
| `BannerGrabber`      | Passive banner reading from open sockets          |

---

## Legal & Ethical Notice

> Only scan hosts you own or have **explicit written permission** to test.  
> Unauthorized port scanning may violate laws including the Computer Fraud and Abuse Act (CFAA) and similar legislation in other countries.  
> The author assumes no liability for misuse of this tool.  
> For safe practice, use [scanme.nmap.org](http://scanme.nmap.org) — a host Nmap provides specifically for legal scan testing.

---

## Possible Extensions

- UDP port scanning support
- OS fingerprinting (TTL analysis)
- Export results to JSON or CSV
- GUI frontend (JavaFX)
- Ping sweep / host discovery before scanning
- CIDR range support (scan entire subnets)

---

## Requirements

- Java 11 or higher
- No external dependencies
