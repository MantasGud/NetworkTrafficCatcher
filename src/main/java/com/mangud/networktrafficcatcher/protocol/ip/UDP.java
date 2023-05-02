package com.mangud.networktrafficcatcher.protocol.ip;/*
 * @created 02/05/2023
 * @project NetworkTrafficCatcher
 * @author  Mantas
 */
import com.mangud.networktrafficcatcher.protocol.Protocol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UDP extends Protocol {

    private int sourcePort;
    private int destinationPort;
    private int length;
    private int checksum;

    @Override
    public String getName() {
        return "UDP";
    }

    @Override
    public void decode(byte[] rawData) {
        ByteBuffer buffer = ByteBuffer.wrap(rawData).order(ByteOrder.BIG_ENDIAN);

        sourcePort = buffer.getShort() & 0xFFFF;
        destinationPort = buffer.getShort() & 0xFFFF;
        length = buffer.getShort() & 0xFFFF;
        checksum = buffer.getShort() & 0xFFFF;
    }
}
