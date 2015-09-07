package edu.kit.ipd.sdq.vitruvius.integration.traversal;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.Change;

 
/**
 * Generic interface that should be implemented by custom traversal strategies.
 *
 * @author Benjamin Hettwer
 * @param <T>
 *            the generic type
 */
public interface ITraversalStrategy<T extends EObject> {

    /**
     * Traverses an EMF-based model to generate a collection of change elements that can be used
     * with Vitruvius. It serves for the purpose of model integration
     *
     * @param entity
     *            root element of the model
     * @param vuri
     *            path to resource file
     * @param existingChanges
     *            the existing changes
     * @return collection of change elements
     * @throws UnsupportedOperationException
     *             : existingChanges not as expected
     */
    EList<Change> traverse(T entity, URI vuri, EList<Change> existingChanges) throws UnsupportedOperationException;

}
