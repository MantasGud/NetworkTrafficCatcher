package com.mangud.networktrafficcatcher.protocol.ethernet;/*
 * @created 02/05/2023
 * @project NetworkTrafficCatcher
 * @author  Mantas
 */
import com.mangud.networktrafficcatcher.Utils.DataTransformation;
import com.mangud.networktrafficcatcher.protocol.Protocol;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import com.mangud.networktrafficcatcher.protocol.ip.IP;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ethernet extends Protocol {

    private String destinationMac;
    private String sourceMac;
    private int etherType;
    private String etherTypeName = "";
    private int frameLength;
    private int priorityCodePoint;
    private int vlanId;
    private int flowLabel;
    private String payloadType;
    private int sequenceNumber;
    private int acknowledgmentNumber;
    private int windowSize;
    private int checksum;
    private String sourceIpAddress = "-";
    private String destinationIpAddress = "-";

    @Override
    public String getName() {
        return "Ethernet II, Src: " + sourceMac + ", Dst: " + destinationMac + ", Type: " + etherTypeName
                + ", Frame length: " + frameLength
                + ", Priority code point: " + priorityCodePoint
                + ", Flow label: " + flowLabel
                + ", Sequence number: " + sequenceNumber
                + ", Acknowledgment number: " + acknowledgmentNumber
                + ", Window size: " + windowSize
                + ", Checksum: " + checksum
                + ", Source IP address: " + sourceIpAddress
                + ", Destination IP address: " + destinationIpAddress;
    }

    @Override
    public void decode(byte[] rawData) {
        ByteBuffer buffer = ByteBuffer.wrap(rawData).order(ByteOrder.BIG_ENDIAN);

        byte[] destMacBytes = new byte[6];
        buffer.get(destMacBytes);
        destinationMac = DataTransformation.bytesToHexString(destMacBytes);

        byte[] srcMacBytes = new byte[6];
        buffer.get(srcMacBytes);
        sourceMac = DataTransformation.bytesToHexString(srcMacBytes);

        etherType = buffer.getShort() & 0xFFFF;

        etherTypeName = getEtherTypeString(etherType);

        // Set additional fields
        frameLength = rawData.length;
        priorityCodePoint = (buffer.get() >> 5) & 0x07;
        if (etherType == 0x8100) {
            vlanId = buffer.getShort() & 0xFFFF;
            etherType = buffer.getShort() & 0xFFFF;
        }
        if (etherType == 0x86DD) {
            // IPv6 payload
            flowLabel = buffer.getInt() & 0x0FFFFFFF;
        }
        else {
            // IPv4 payload
            IP ipv4 = new IP();
            byte[] payload = Arrays.copyOfRange(rawData, 14, rawData.length);
            ipv4.decode(payload);
            payloadType = ipv4.getName();
            sequenceNumber = ipv4.getIdentification();
            acknowledgmentNumber = 0;
            windowSize = ipv4.getTotalLength();
            checksum = ipv4.getHeaderChecksum();
            sourceIpAddress = ipv4.getSourceAddress();
            destinationIpAddress = ipv4.getDestinationAddress();
        }

    }

    private String getEtherTypeString(int etherType) {
        switch (etherType) {
            case 0x0800:
                return "IPv4";
            case 0x86DD:
                return "IPv6";
            case 0x0806:
                return "ARP";
            case 0x0805:
                return "ARP Request";
            case 0x8035:
                return "RARP";
            case 0x809B:
                return "AppleTalk";
            case 0x80F3:
                return "AppleTalk ARP";
            case 0x8100:
                return "802.1Q VLAN Tagging";
            case 0x88A8:
                return "802.1Q Service VLAN Tagging";
            case 0x88E1:
                return "HomePlug 1.0 MME";
            case 0x88E5:
                return "802.1X";
            case 0x8906:
                return "Fibre Channel over Ethernet";
            case 0x8914:
                return "Internet Protocol over Fibre Channel";
            case 0x9000:
                return "Ethernet Configuration Testing Protocol";
            default:
                return "Unknown";
        }
    }

}