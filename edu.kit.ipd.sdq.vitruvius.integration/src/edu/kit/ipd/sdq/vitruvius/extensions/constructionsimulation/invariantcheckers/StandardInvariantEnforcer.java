package edu.kit.ipd.sdq.vitruvius.extensions.constructionsimulation.invariantcheckers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

 
/**
 *
 * Baseclass for "standard" invariant enforcers. This type of enforcer does rely on a fixed
 * (traversal) strategy for a model type. Hence, subclasses must commit to a certain type of model
 * (type parameter T).
 * 
 *
 * @author Johannes Hoor
 *
 * @param <T>
 *            T meaning the source model as "modelImpl" class,
 */
public abstract class StandardInvariantEnforcer<T extends EObject> extends InvariantEnforcer {

    /**
     * Instantiates a new fixed invariant enforcer.
     */

    protected IMModelImplExtractor<T> extractor;

    protected T root;

    protected Resource model;

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.kit.ipd.sdq.vitruvius.integration.invariantChecker.InvariantEnforcer#loadModelRoot(org
     * .eclipse.emf.ecore.resource.Resource)
     */
    @Override
    public void loadModelRoot(final Resource model) {
        this.model = model;
        this.setRoot(this.extractor.getImpl(model));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * edu.kit.ipd.sdq.vitruvius.integration.invariantChecker.InvariantEnforcer#enforceInvariant()
     */
    @Override
    public abstract void enforceInvariant();

    /*
     * (non-Javadoc)
     * 
     * @see edu.kit.ipd.sdq.vitruvius.integration.invariantChecker.InvariantEnforcer#returnModel()
     */
    @Override
    public abstract Resource returnModel();

    /**
     * Gets the root.
     *
     * @return the root
     */
    public T getRoot() {
        return this.root;
    }

    /**
     * Sets the root.
     *
     * @param root
     *            the new root
     */
    public void setRoot(final T root) {
        this.root = root;
    }

}
