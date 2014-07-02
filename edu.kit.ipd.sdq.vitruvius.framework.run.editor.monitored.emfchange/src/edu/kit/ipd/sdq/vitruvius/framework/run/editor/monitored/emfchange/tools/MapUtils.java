package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.tools;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class MapUtils {
    private MapUtils() {
    }

    public static <U, V> void addToSetMap(U key, V value, Map<U, Set<V>> map) {
        if (!map.containsKey(key)) {
            map.put(key, new HashSet<V>());
        }
        map.get(key).add(value);
    }

}
