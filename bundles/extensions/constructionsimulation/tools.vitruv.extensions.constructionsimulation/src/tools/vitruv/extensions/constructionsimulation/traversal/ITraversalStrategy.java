package tools.vitruv.extensions.constructionsimulation.traversal;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.change.description.VitruviusChange;

 
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
     * @param uri
     *            path to resource file
     * @param existingChanges
     *            the existing changes
     * @return collection of change elements
     * @throws UnsupportedOperationException
     *             : existingChanges not as expected
     */
    EList<VitruviusChange> traverse(T entity, URI uri, EList<VitruviusChange> existingChanges) throws UnsupportedOperationException;

}
