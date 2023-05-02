package com.mangud.networktrafficcatcher.storage;/*
 * @created 02/05/2023
 * @project NetworkTrafficCatcher
 * @author  Mantas
 */

import com.mangud.networktrafficcatcher.protocol.Protocol;
import java.util.ArrayList;
import java.util.List;

public class PacketStorage {
    private List<Protocol> packets;

    public PacketStorage() {
        packets = new ArrayList<>();
    }

    public void addPacket(Protocol packet) {
        packets.add(packet);
    }

    public List<Protocol> getPackets() {
        return packets;
    }

    public void clear() {
        packets.clear();
    }
}
