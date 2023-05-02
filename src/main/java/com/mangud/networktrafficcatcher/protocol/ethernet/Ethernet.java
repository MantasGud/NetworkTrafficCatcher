package com.mangud.networktrafficcatcher.protocol.ethernet;/*
 * @created 02/05/2023
 * @project NetworkTrafficCatcher
 * @author  Mantas
 */
import com.mangud.networktrafficcatcher.protocol.Protocol;

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
public class Ethernet extends Protocol {

    private String destinationMac;
    private String sourceMac;
    private int etherType;
    private String etherTypeString = "";

    @Override
    public String getName() {
        return "Ethernet: " + destinationMac + " " + sourceMac + " " + etherTypeString;
    }

    @Override
    public void decode(byte[] rawData) {
        ByteBuffer buffer = ByteBuffer.wrap(rawData).order(ByteOrder.BIG_ENDIAN);

        byte[] destMacBytes = new byte[6];
        buffer.get(destMacBytes);
        destinationMac = bytesToHexString(destMacBytes);

        byte[] srcMacBytes = new byte[6];
        buffer.get(srcMacBytes);
        sourceMac = bytesToHexString(srcMacBytes);

        etherType = buffer.getShort() & 0xFFFF;

        switch (etherType) {
            case 0x0800: // IPv4
                etherTypeString = "IPv4";
                break;
            case 0x86DD : // IPv6
                etherTypeString = "IPv6";
                break;
            case 0x0806: // ARP
                etherTypeString = "ARP";
                break;
            default:
                etherTypeString = "Unknown";
                break;
        }
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(String.format("%02X", bytes[i]));
            if (i < bytes.length - 1) {
                sb.append(":");
            }
        }
        return sb.toString();
    }

}