package tools.vitruv.applications.pcmjava.reconstructionintegration.composite;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.palladiosimulator.pcm.system.System;

import tools.vitruv.applications.pcmjava.reconstructionintegration.util.PCMChangeBuildHelper;
import tools.vitruv.extensions.constructionsimulation.traversal.ITraversalStrategy;
import tools.vitruv.framework.change.description.VitruviusChange;
import tools.vitruv.framework.util.datatypes.VURI;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.description.VitruviusChangeFactory;

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
     * tools.vitruv.integration.traversal.ITraversalStrategy#traverse(org.eclipse.emf
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
        this.addChange(VitruviusChangeFactory.getInstance().createConcreteChange(systemChange, this.vuri), this.changeList);

    }

}
