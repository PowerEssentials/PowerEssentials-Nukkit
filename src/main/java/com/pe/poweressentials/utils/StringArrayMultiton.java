package com.pe.poweressentials.utils;

import java.util.concurrent.ConcurrentHashMap;

public abstract class StringArrayMultiton {

    private static final ConcurrentHashMap<String, StringArrayMultiton> instances = new ConcurrentHashMap<>();

    protected StringArrayMultiton(String key) {
        instances.put(key, this);
    }

    public static StringArrayMultiton getInstance(String key) {
        return instances.get(key);
    }

    public static void removeInstance(String key) {
        instances.remove(key);
    }
}
