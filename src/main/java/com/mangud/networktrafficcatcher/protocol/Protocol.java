package com.mangud.networktrafficcatcher.protocol;/*
 * @created 02/05/2023
 * @project NetworkTrafficCatcher
 * @author  Mantas
 */

public abstract class Protocol {

    public abstract String getName();

    public abstract void decode(byte[] rawData);

    @Override
    public String toString() {
        return getName();
    }
}
