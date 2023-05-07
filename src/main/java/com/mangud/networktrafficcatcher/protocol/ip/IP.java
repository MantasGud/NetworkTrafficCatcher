package com.mangud.networktrafficcatcher.protocol.ip;/*
 * @created 02/05/2023
 * @project NetworkTrafficCatcher
 * @author  Mantas
 */
import com.mangud.networktrafficcatcher.protocol.Protocol;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class IP  extends Protocol {
    private byte version;
    private byte ihl;
    private byte tos;
    private int totalLength;
    private int identification;
    private boolean dfFlag;
    private boolean mfFlag;
    private int fragmentOffset;
    private byte ttl;
    private byte protocol;
    private int headerChecksum;
    private InetAddress sourceIP;
    private InetAddress destinationIP;

    @Override
    public String getName() {
        return "IPv4: " + getSourceAddress() + " -> " + getDestinationAddress();
    }

    @Override
    public void decode(byte[] rawData) {
        ByteBuffer buffer = ByteBuffer.wrap(rawData).order(ByteOrder.BIG_ENDIAN);

        byte firstByte = buffer.get();
        version = (byte) ((firstByte >> 4) & 0x0F);
        ihl = (byte) (firstByte & 0x0F);

        tos = buffer.get();
        totalLength = buffer.getShort() & 0xFFFF;
        identification = buffer.getShort() & 0xFFFF;

        int flagsAndOffset = buffer.getShort() & 0xFFFF;
        dfFlag = (flagsAndOffset & 0x4000) != 0;
        mfFlag = (flagsAndOffset & 0x2000) != 0;
        fragmentOffset = flagsAndOffset & 0x1FFF;

        ttl = buffer.get();
        protocol = buffer.get();
        headerChecksum = buffer.getShort() & 0xFFFF;

        byte[] srcIP = new byte[4];
        buffer.get(srcIP);

        byte[] dstIP = new byte[4];
        buffer.get(dstIP);

        try {
            sourceIP = InetAddress.getByAddress(srcIP);
            destinationIP = InetAddress.getByAddress(dstIP);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public String getSourceAddress() {
        return sourceIP.getHostAddress();
    }

    public String getDestinationAddress() {
        return destinationIP.getHostAddress();
    }
}
