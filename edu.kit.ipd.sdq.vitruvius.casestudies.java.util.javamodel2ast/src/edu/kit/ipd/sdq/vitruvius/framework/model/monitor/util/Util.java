package edu.kit.ipd.sdq.vitruvius.framework.model.monitor.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Util {

    public static List<Object> filterBySupertype(Collection<Object> elements, Class<?> supertype) {
        List<Object> filtered = new ArrayList<Object>(elements.size());
        for (Object element : elements) {
            if (supertype.isInstance(element))
                filtered.add(element);
        }
        return filtered;
    }
}
