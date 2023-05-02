package com.mangud.networktrafficcatcher;

import com.mangud.networktrafficcatcher.packetcapture.PacketCaptureManager;
import com.mangud.networktrafficcatcher.packetcapture.PacketFilter;
import com.mangud.networktrafficcatcher.storage.PacketStorage;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.Pcaps;

import javax.swing.*;
import java.util.List;
public class Main
{
    public static void main( String[] args )
    {
        try {
            List<PcapNetworkInterface> devices = Pcaps.findAllDevs();
            System.out.println("Devices list : " + devices.toString());


            //PcapNetworkInterface device = devices.get(4);

            PacketStorage packetStorage = new PacketStorage();
            PacketFilter packetFilter = new PacketFilter();

            SwingUtilities.invokeLater(() -> {
                PacketCaptureManager packetCaptureManager = new PacketCaptureManager(packetStorage, packetFilter);
                //MainFrame mainFrame = new MainFrame(packetCaptureManager);
                //mainFrame.setVisible(true);



                // Start capturing packets in a separate thread
                new Thread(() -> {
                    try {
                        packetCaptureManager.startCapture(null/*device.getName()*/, -1);
                    } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

        });

        } catch (Exception e) {
        e.printStackTrace();
        }
    }
}
        /*
        try {
            List<PcapNetworkInterface> devices = Pcaps.findAllDevs();
            if (devices.isEmpty()) {
                System.out.println("No devices found.");
                return;
            }

            // Choose the first device as an example
            PcapNetworkInterface device = devices.get(0);
            System.out.println("Capturing from device: " + device.getName());

            PacketStorage packetStorage = new PacketStorage();
            PacketFilter packetFilter = new PacketFilter();

            PacketCaptureManager captureManager = new PacketCaptureManager(packetStorage, packetFilter);

            int numPackets = 1000; // Capture 10 packets as an example
            captureManager.startCapture(device.getName(), numPackets);
            captureManager.stopCapture();

            System.out.println("Captured " + packetStorage.getPackets().size() + " packets.");

        } catch (IOException | PcapNativeException e) {
            e.printStackTrace();
        } catch (NotOpenException e) {
            throw new RuntimeException(e);
        }*/
