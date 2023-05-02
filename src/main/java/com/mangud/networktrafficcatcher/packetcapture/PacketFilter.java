package com.mangud.networktrafficcatcher.packetcapture;/*
 * @created 02/05/2023
 * @project NetworkTrafficCatcher
 * @author  Mantas
 */

import com.mangud.networktrafficcatcher.protocol.Protocol;
import com.mangud.networktrafficcatcher.protocol.ethernet.Ethernet;
import com.mangud.networktrafficcatcher.protocol.ip.IP;
import com.mangud.networktrafficcatcher.protocol.ip.TCP;
import com.mangud.networktrafficcatcher.protocol.ip.UDP;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.pcap4j.packet.Packet;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PacketFilter {

    private String filter = "";

    public Protocol apply(Packet packet) {
        Protocol decodedPacket = null;


        if (packet instanceof org.pcap4j.packet.IpV4Packet) {
            decodedPacket = new IP();
        } else if (packet instanceof org.pcap4j.packet.TcpPacket) {
            decodedPacket = new TCP();
        } else if (packet instanceof org.pcap4j.packet.UdpPacket) {
            decodedPacket = new UDP();
        } else if (packet instanceof org.pcap4j.packet.EthernetPacket) {
            decodedPacket = new Ethernet();
        }

        if (decodedPacket != null) {
            decodedPacket.decode(packet.getRawData());
        }

        return decodedPacket;
    }

}
