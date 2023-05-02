package com.mangud.networktrafficcatcher.gui;/*
 * @created 02/05/2023
 * @project NetworkTrafficCatcher
 * @author  Mantas
 */
import com.mangud.networktrafficcatcher.protocol.Protocol;
import com.mangud.networktrafficcatcher.storage.PacketStorage;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;

public class PacketTable extends JTable {

    private PacketStorage packetStorage;
    private DefaultTableModel tableModel;

    public PacketTable(PacketStorage packetStorage) {
        this.packetStorage = packetStorage;

        String[] columnNames = {"Timestamp", "Source IP", "Destination IP", "Protocol", "Length"};
        tableModel = new DefaultTableModel(columnNames, 0);
        setModel(tableModel);

        for (Protocol packet : packetStorage.getPackets()) {
            addPacketToTable(packet);
        }
    }

    public void addPacket(Protocol packet) {
        packetStorage.addPacket(packet);
        addPacketToTable(packet);
    }

    private void addPacketToTable(Protocol packet) {
        Object[] rowData = {
                packet.getName(),
                "",
                "",
                "",
                ""
        };
        tableModel.addRow(rowData);
    }

}
