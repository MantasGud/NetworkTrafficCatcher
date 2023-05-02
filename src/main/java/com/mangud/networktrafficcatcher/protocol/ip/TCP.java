package com.mangud.networktrafficcatcher.protocol.ip;/*
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
public class TCP extends Protocol {
    private int sourcePort;
    private int destinationPort;
    private long sequenceNumber;
    private long acknowledgementNumber;
    private byte dataOffset;
    private boolean urgFlag;
    private boolean ackFlag;
    private boolean pshFlag;
    private boolean rstFlag;
    private boolean synFlag;
    private boolean finFlag;
    private int windowSize;
    private int checksum;
    private int urgentPointer;

    @Override
    public String getName() {
        return "TCP";
    }

    @Override
    public void decode(byte[] rawData) {
        ByteBuffer buffer = ByteBuffer.wrap(rawData).order(ByteOrder.BIG_ENDIAN);

        sourcePort = buffer.getShort() & 0xFFFF;
        destinationPort = buffer.getShort() & 0xFFFF;
        sequenceNumber = buffer.getInt() & 0xFFFFFFFFL;
        acknowledgementNumber = buffer.getInt() & 0xFFFFFFFFL;

        byte firstByte = buffer.get();
        dataOffset = (byte) ((firstByte >> 4) & 0x0F);

        byte flags = buffer.get();
        urgFlag = (flags & 0x20) != 0;
        ackFlag = (flags & 0x10) != 0;
        pshFlag = (flags & 0x08) != 0;
        rstFlag = (flags & 0x04) != 0;
        synFlag = (flags & 0x02) != 0;
        finFlag = (flags & 0x01) != 0;

        windowSize = buffer.getShort() & 0xFFFF;
        checksum = buffer.getShort() & 0xFFFF;
        urgentPointer = buffer.getShort() & 0xFFFF;
    }
}
