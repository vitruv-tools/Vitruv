package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changedescription2change.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;

final class Util {
    private Util() {

    }

    public static void addToMap(EObject key, Change change, Map<EObject, List<Change>> target) {
        if (!target.containsKey(key)) {
            target.put(key, new ArrayList<Change>());
        }
        target.get(key).add(change);
    }

}
