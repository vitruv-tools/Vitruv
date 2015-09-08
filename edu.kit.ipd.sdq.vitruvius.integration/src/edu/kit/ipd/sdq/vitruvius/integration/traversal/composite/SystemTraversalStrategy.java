package edu.kit.ipd.sdq.vitruvius.integration.traversal.composite;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;

import org.palladiosimulator.pcm.system.System;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.EMFModelChange;
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.meta.change.EChange;
import edu.kit.ipd.sdq.vitruvius.integration.traversal.ITraversalStrategy;
import edu.kit.ipd.sdq.vitruvius.integration.traversal.util.PCMChangeBuildHelper;

 
/**
 * This class provides methods for traversing PCM systems.
 *
 * @author Sven Leonhardt
 */

public class SystemTraversalStrategy extends ComposedEntitiesTraversalStrategy implements ITraversalStrategy<System> {

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.kit.ipd.sdq.vitruvius.integration.traversal.ITraversalStrategy#traverse(org.eclipse.emf
     * .ecore.EObject, org.eclipse.emf.common.util.URI, org.eclipse.emf.common.util.EList)
     */
    @Override
    public EList<Change> traverse(final System entity, final URI uri, final EList<Change> existingChanges)
            throws UnsupportedOperationException {

        final System system = entity;
        this.vuri = VURI.getInstance(uri);
        this.changeList = existingChanges;

        if (this.changeList != null) {
            throw new UnsupportedOperationException("A system cannot be built upon existing changes");
        }
        this.changeList = new BasicEList<Change>();

        this.traverseSystem(system);

        this.traverseComposedEntity(system);

        return this.changeList;
    }

    /**
     * Traverses the system root element.
     *
     * @param system
     *            to be traversed
     */
    private void traverseSystem(final System system) {

        final EChange systemChange = PCMChangeBuildHelper.createChangeFromSystem(system);
        this.addChange(new EMFModelChange(systemChange, this.vuri), this.changeList);

    }

}
