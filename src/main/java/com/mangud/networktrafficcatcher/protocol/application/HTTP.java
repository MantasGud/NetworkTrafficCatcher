package com.mangud.networktrafficcatcher.protocol.application;/*
 * @created 02/05/2023
 * @project NetworkTrafficCatcher
 * @author  Mantas
 */

import com.mangud.networktrafficcatcher.protocol.Protocol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.charset.StandardCharsets;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HTTP extends Protocol {

    private String data;

    @Override
    public String getName() {
        return "HTTP";
    }

    @Override
    public void decode(byte[] rawData) {
        data = new String(rawData, StandardCharsets.UTF_8);
    }
}
