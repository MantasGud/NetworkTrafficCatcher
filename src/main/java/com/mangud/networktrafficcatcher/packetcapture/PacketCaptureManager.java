package com.mangud.networktrafficcatcher.packetcapture;/*
 * @created 02/05/2023
 * @project NetworkTrafficCatcher
 * @author  Mantas
 */
import com.mangud.networktrafficcatcher.protocol.Protocol;
import com.mangud.networktrafficcatcher.storage.PacketStorage;
import org.pcap4j.core.*;
import org.pcap4j.packet.Packet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class PacketCaptureManager {

    private static final int SNAPLEN = 65536;
    private PcapHandle handle;
    private PacketStorage packetStorage;
    private PacketFilter packetFilter;
    private Consumer<Protocol> onPacketCaptured;

    private List<PcapHandle> handles = new ArrayList<>();

    public PacketCaptureManager(PacketStorage packetStorage, PacketFilter packetFilter) {
        this.packetStorage = packetStorage;
        this.packetFilter = packetFilter;
        this.onPacketCaptured = onPacketCaptured;
    }

    public PacketStorage getPacketStorage() {
        return packetStorage;
    }



    public void startCapture(String deviceName, int numPackets) throws IOException, PcapNativeException, NotOpenException {
        List<PcapNetworkInterface> devices = Pcaps.findAllDevs();
        PcapNetworkInterface.PromiscuousMode mode = PcapNetworkInterface.PromiscuousMode.PROMISCUOUS;
        int timeoutMillis = 10;

        // Start packet capture for each interface
        for (PcapNetworkInterface device : devices) {
            PcapHandle handle = device.openLive(SNAPLEN, mode, timeoutMillis);
            handle.setFilter(packetFilter.getFilter(), BpfProgram.BpfCompileMode.OPTIMIZE);
            handles.add(handle);
        }

        List<PacketListener> listeners = new ArrayList<>();
        for (PcapHandle handle : handles) {
            PacketListener listener = new PacketListener() {
                @Override
                public void gotPacket(Packet packet) {
                    Protocol decodedPacket = packetFilter.apply(packet);
                    if (decodedPacket != null) {
                        System.out.println("Captured packet: " + decodedPacket);
                        packetStorage.addPacket(decodedPacket);
                        onPacketCaptured.accept(decodedPacket);

                    }
                }
            };
            listeners.add(listener);
        }

        for (int i = 0; i < handles.size(); i++) {
            PcapHandle handle = handles.get(i);
            PacketListener listener = listeners.get(i);
            new Thread(() -> {
                try {
                    handle.loop(-1, listener);
                } catch (PcapNativeException | InterruptedException | NotOpenException e) {
                    e.printStackTrace();
                } finally {
                    handle.close();
                }
            }).start();
        }


        /*
        try {
            //PcapNetworkInterface device = Pcaps.getDevByName(deviceName);
            int snapLen = 65536;
            PcapNetworkInterface.PromiscuousMode mode = PcapNetworkInterface.PromiscuousMode.PROMISCUOUS;
            int timeoutMillis = 10;
            handle = device.openLive(snapLen, mode, timeoutMillis);

            PacketListener listener = new PacketListener() {
                @Override
                public void gotPacket(Packet packet) {
                    Protocol decodedPacket = packetFilter.apply(packet);
                    if (decodedPacket != null) {
                        System.out.println("Captured packet: " + decodedPacket);
                        packetStorage.addPacket(decodedPacket);
                        onPacketCaptured.accept(decodedPacket);

                    }
                }
            };

            handle.loop(numPackets, listener);

        } catch (PcapNativeException | InterruptedException | NotOpenException e) {
            throw new IOException("Error starting packet capture", e);
        }*/
    }

    public void stopCapture() throws NotOpenException {
        if (handle != null) {
            handle.breakLoop();
            handle.close();
        }
    }
}
