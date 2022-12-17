package fun.billy.fbi.util;

import java.util.TreeMap;

public class CustomMap<K, V> extends TreeMap<K, V> {
    int maxSize;

    public CustomMap(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public V put(K key, V value) {
        if (size() >= maxSize) remove(firstKey());
        return super.put(key, value);
    }
}