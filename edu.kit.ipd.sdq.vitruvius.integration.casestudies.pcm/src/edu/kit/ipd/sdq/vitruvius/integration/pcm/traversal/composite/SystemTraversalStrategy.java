package edu.kit.ipd.sdq.vitruvius.integration.pcm.traversal.composite;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.palladiosimulator.pcm.system.System;

import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChange;
import edu.kit.ipd.sdq.vitruvius.framework.util.datatypes.VURI;
import edu.kit.ipd.sdq.vitruvius.framework.change.echange.EChange;
import edu.kit.ipd.sdq.vitruvius.framework.change.description.VitruviusChangeFactory;
import edu.kit.ipd.sdq.vitruvius.integration.pcm.traversal.util.PCMChangeBuildHelper;
import edu.kit.ipd.sdq.vitruvius.integration.traversal.ITraversalStrategy;

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
    public EList<VitruviusChange> traverse(final System entity, final URI uri, final EList<VitruviusChange> existingChanges)
            throws UnsupportedOperationException {

        final System system = entity;
        this.vuri = VURI.getInstance(uri);
        this.changeList = existingChanges;

        if (this.changeList != null) {
            throw new UnsupportedOperationException("A system cannot be built upon existing changes");
        }
        this.changeList = new BasicEList<VitruviusChange>();

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
        this.addChange(VitruviusChangeFactory.getInstance().createGeneralChange(systemChange, this.vuri), this.changeList);

    }

}
