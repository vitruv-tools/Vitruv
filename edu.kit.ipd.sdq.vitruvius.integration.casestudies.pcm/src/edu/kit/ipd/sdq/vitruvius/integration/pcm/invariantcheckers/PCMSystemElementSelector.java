package edu.kit.ipd.sdq.vitruvius.integration.pcm.invariantcheckers;

import org.palladiosimulator.pcm.core.composition.AssemblyContext;
import org.palladiosimulator.pcm.core.composition.Connector;
import org.palladiosimulator.pcm.core.entity.Entity;
import org.palladiosimulator.pcm.repository.ProvidedRole;
import org.palladiosimulator.pcm.repository.RequiredRole;
import org.palladiosimulator.pcm.system.System;

//TODO: Auto-generated Javadoc
/**
 * Traverses PCM System elements. Order is not that important.
 *
 * @author Sven Leonhardt
 *
 */
public class PCMSystemElementSelector extends PCMElementSelector<System> {

    /**
     * Std.
     */
    public PCMSystemElementSelector() {
        super();
        this.extractor = new PCMSystemExtractor();
    }

    /**
     * Identify and solve naming conflicts: Finds AssemblyContexts, Roles and Connectors
     */
    @Override
    public void traverseModelAndSolveConflics() {

        // inspect the system element
        inspectSystemElements();

        for (AssemblyContext context : root.getAssemblyContexts__ComposedStructure()) {
            checkAndSolveConflict(context);
        }

        for (Connector connector : root.getConnectors__ComposedStructure()) {
            checkAndSolveConflict(connector);
        }

        for (ProvidedRole providedRole : root.getProvidedRoles_InterfaceProvidingEntity()) {
            checkAndSolveConflict(providedRole);
        }

        for (RequiredRole requiredRole : root.getRequiredRoles_InterfaceRequiringEntity()) {
            checkAndSolveConflict(requiredRole);
        }
    }

    /**
     * checks the system elements for naming conflicts and resolves them
     * 
     * @param entity
     *            * with possible conflict
     */
    private void checkAndSolveConflict(Entity entity) {
        String entityName = entity.getEntityName();

        if (checkForNameConflict(entityName)) {
            logger.info("-identified@entity " + entity.getEntityName());
            entity.setEntityName(renameElement(entityName));
        }
    }

    /**
     * checks and solves naming conflict for the root element system
     */
    private void inspectSystemElements() {
        // TODO: logger support and testcases
        if (this.checkForNameConflict(this.root.getEntityName())) {
            this.root.setEntityName(this.renameElement(this.root.getEntityName()));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.integration.invariantChecker.PCMJaMoPPEnforcer.
     * PCMSimpleTraversalStrategy#loadModelRoot()
     */
    @Override
    public void loadModelRoot() {
        this.root = this.extractor.getImpl(this.parentEnforcer.getModel());

    }

}
