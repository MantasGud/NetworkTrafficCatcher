package com.mangud.networktrafficcatcher.gui;/*
 * @created 02/05/2023
 * @project NetworkTrafficCatcher
 * @author  Mantas
 */

import com.mangud.networktrafficcatcher.packetcapture.PacketCaptureManager;
import com.mangud.networktrafficcatcher.protocol.Protocol;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
public class MainFrame extends JFrame {
    private final JPanel contentPane;
    //private final PacketTable packetTable;
    private PacketTable packetTable;
    private PacketCaptureManager packetCaptureManager;
/*
    public MainFrame(PacketCaptureManager packetCaptureManager) {
        this.packetCaptureManager = packetCaptureManager;
        setTitle("Network Traffic Monitor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(800, 600));
        setLocationRelativeTo(null);

        packetTable = new PacketTable(packetCaptureManager.getPacketStorage());
        add(new JScrollPane(packetTable), BorderLayout.CENTER);
    }
*/
public MainFrame(PacketCaptureManager packetCaptureManager) {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 800, 600);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(new BorderLayout(0, 0));
    setContentPane(contentPane);

    JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    contentPane.add(controlPanel, BorderLayout.NORTH);

    JButton refreshButton = new JButton("Refresh");
    controlPanel.add(refreshButton);

    packetTable = new PacketTable(packetCaptureManager.getPacketStorage());
    contentPane.add(new JScrollPane(packetTable), BorderLayout.CENTER);

    refreshButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            List<Protocol> packets = packetCaptureManager.getPacketStorage().getPackets();
            packets.forEach(packet -> packetTable.addPacket(packet));
            packetTable.updateUI();
        }
    });
}
    public void addPacket(Protocol packet) {
        packetTable.addPacket(packet);
    }
}
