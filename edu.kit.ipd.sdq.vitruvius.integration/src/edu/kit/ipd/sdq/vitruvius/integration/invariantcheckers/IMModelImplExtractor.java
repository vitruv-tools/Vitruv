package edu.kit.ipd.sdq.vitruvius.integration.invariantcheckers;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

 
/**
 * Konkrete Implementierung des Ecore-based-MM extrahieren.
 *
 * @author Johannes Hoor
 * @param <T>
 *            the generic type
 */
public interface IMModelImplExtractor<T extends EObject> {

    /**
     * Gets the impl.
     *
     * @param model
     *            the model
     * @return the impl
     */
    public T getImpl(Resource model);

}
