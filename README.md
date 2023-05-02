# NetworkTrafficCatcher

NetworkTrafficCatcher is a Java-based packet capturing and analysis tool, similar to Wireshark. 
The application captures network packets using the pcap4j library and provides insights into the network traffic. 
It currently supports Ethernet, IP, TCP, and UDP protocols.

## Requirements

- Java 8 or higher
- Maven
- [Npcap](https://nmap.org/npcap/) (Windows) or [libpcap](https://www.tcpdump.org/) (Linux/Mac)

## Installation

1. Clone the repository:
```bash
git clone https://github.com/MantasGud/NetworkTrafficCatcher.git
```
2. Change to the project directory:
```bash
cd NetworkTrafficCatcher
```
3. Build the project:
```bash
mvn clean install
```

## Usage

1. Run the application:
```bash
java -jar target/NetworkTrafficCatcher-1.0-SNAPSHOT.jar
```
2. The application will display a list of available network interfaces. 

3. The application will capture and display packet details in the console.

## TODO
- GUI not working (need to fix it), information only shown in console.
- More detailed/advanced network information.
- Filter in GUI(by IP, PORTS, Protocols, sources, destinations, etc.).
- Additional function - Block ip address list from .txt file to whole PC when the application is running.(if possible?)
- Reports/Exports(Export to CSV, JSON or other format.)
- Data storage in database.
- Tests for whole application.
- Support for capturing packets on remote machines using a client-server model.(if possible?)
- Support for capturing/analyzing encrypted traffic?(HTTPS?)(if possible?)
- Charts/Graphs/Some other type of visualization?
- Information show for potential threats in incoming traffic?(add some kind of integration?)(if possible?)
