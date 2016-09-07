package tools.vitruvius.framework.util.command;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionChangeDescription;

public class EMFChangeBridge {
    private EMFChangeBridge() {
    }

    public static Collection<EObject> getAffectedObjects(final TransactionChangeDescription changeDescription) {
        // TODO insert more intelligence here
        /*
         * this is an extremely conservative implementation, that tries to capture *all* fields in
         * *all* referenced changes that point to an EObject, which might be too much.
         */

        Collection<EObject> result = new HashSet<EObject>();
        result.addAll(changeDescription.getObjectsToAttach());
        // result.addAll(changeDescription.getObjectsToDetach());

        changeDescription.getObjectChanges().stream().map(oc -> oc.getKey()).forEach(result::add);

        changeDescription.getObjectChanges().stream().flatMap(oc -> oc.getValue().stream())
                .flatMap(fc -> Stream.concat(Stream.of(fc.getReferenceValue()),
                        fc.getListChanges().stream().flatMap(lc -> lc.getReferenceValues().stream())))
                .forEach(result::add);

        changeDescription.getResourceChanges().stream().flatMap(rc -> rc.getListChanges().stream())
                .flatMap(lc -> lc.getReferenceValues().stream()).forEach(result::add);

        return result;
    }
}
