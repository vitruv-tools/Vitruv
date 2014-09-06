package edu.kit.ipd.sdq.vitruvius.framework.run.editor.monitored.emfchange.changeapplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.Switch;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.FeaturePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.attribute.AttributePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.ReferencePackage;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.feature.reference.containment.ContainmentPackage;

/**
 * {@link ChangeApplicator} applies a list of {@link Change} objects (relative to a model instance
 * A) to a model instance B.
 */
public class ChangeApplicator {
    private final Resource source;
    private final List<Change> changesInSource;

    /**
     * Constructs a new {@link ChangeApplicator} instance.
     * 
     * @param source
     *            The resource of the model instance to which <code>changesInSource</code> is
     *            relative. This model instance must be in a state where
     *            <code>changesInSource</code> contains the most recent changes.
     * @param changesInSource
     *            The list of changes to be applied, relative to the model instance represented by
     *            <code>source</code>. The changes must be ordered such that, if linearly applied,
     *            no affected object is referenced by any change such that the object does not exist
     *            yet after having applied the changes up to that point.
     */
    public ChangeApplicator(Resource source, List<Change> changesInSource) {
        this.source = source;
        this.changesInSource = changesInSource;
    }

    private Map<EPackage, Switch<EObject>> createSubChangeApplicators(Resource target) {
        Map<EPackage, Switch<EObject>> result = new HashMap<>();

        ApplicatorConfiguration ar = new ApplicatorConfiguration(source, target, changesInSource);
        result.put(AttributePackage.eINSTANCE, new AttributePackageChangeApplicator(ar));
        result.put(ContainmentPackage.eINSTANCE, new ContainmentPackageReferenceChangeApplicator(ar));
        result.put(FeaturePackage.eINSTANCE, new FeaturePackageChangeApplicator(ar));
        result.put(ReferencePackage.eINSTANCE, new ReferencePackageChangeApplicator(ar));

        return result;
    }

    private void dispatchChangesToSubChangeApplicators(Map<EPackage, Switch<EObject>> subApplicators) {
        for (Change c : changesInSource) {
            if (c instanceof EMFModelChange) {
                EChange update = ((EMFModelChange) c).getEChange();
                EPackage updateKind = update.eClass().getEPackage();
                if (subApplicators.containsKey(updateKind)) {
                    subApplicators.get(updateKind).doSwitch(update);
                } else {
                    throw new IllegalArgumentException("Cannot dispatch objects of package " + updateKind.getName());
                }
            }
        }
    }

    /**
     * Applies the changes supplied to {@link #ChangeApplicator(Resource, List)} to the given target
     * model instance.
     * 
     * @param target
     *            The resource of the model instance receiving the changes. All pre-existing objects
     *            referenced by the changes must be contained in this instance at structurally
     *            equivalent positions.
     */
    public void applyChanges(Resource target) {
        if (changesInSource.isEmpty()) {
            return;
        }

        Map<EPackage, Switch<EObject>> subApplicators = createSubChangeApplicators(target);
        dispatchChangesToSubChangeApplicators(subApplicators);
    }

}
