package tools.vitruv.framework.util.command;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;

import tools.vitruv.framework.util.datatypes.VURI;

/**
 * Represents the result of a change propagation regarding necessary persistence.
 * Essentially, the mapping of {@link EObject}s to {@link VURI}s to persist them in is managed.
 *  
 * @author Heiko Klare
 *
 */
public class ChangePropagationResult {

    private final Map<EObject, VURI> elementToPersistenceVuriMap;

    public ChangePropagationResult() {
        this.elementToPersistenceVuriMap = new HashMap<EObject, VURI>();
    }

    /**
     * Revokes the registration for persistence of the specified {@link EObject}.
     * This is only useful, if an element was registered for persistence using the method
     * {@link ChangePropagationResult#registerForEstablishPersistence(EObject, VURI)}, which
     * shall be revoked before it was processed by a persistence mechanisms. 
     *  
     * @param persistenceRootElement the {@link EObject} of which the persistence registration shall be revoked
     */
    public void revokeRegistrationForPersistence(final EObject persistenceRootElement) {
    	this.elementToPersistenceVuriMap.put(persistenceRootElement, null);
    }

    public Map<EObject, VURI> getElementToPersistenceMap() {
        return this.elementToPersistenceVuriMap;
    }

    /**
     * Registers the specified {@link EObject} for being persisted by the mechanisms that finally
     * processes this {@link ChangePropagationResult}. Persistence will be performed as root element
     * in a resource with the specified {@link VURI}.
     * If the persistence registration shall be revoked before persistence is executed, the method
     * {@link ChangePropagationResult#revokeRegistrationForPersistence(EObject)} has to be called.
     * 
     * @param persistenceRootElement the {@link EObject} to persist as root
     * @param persistenceVuri the {@link VURI} of the resource to persist element in
     */
    public void registerForEstablishPersistence(final EObject persistenceRootElement, final VURI persistenceVuri) {
    	this.elementToPersistenceVuriMap.put(persistenceRootElement, persistenceVuri);
    }
    
    public void integrateResult(ChangePropagationResult transformationResult) {
    	if (transformationResult == null) {
    		return;
    	}
    	for (EObject persistenceRootElement : transformationResult.elementToPersistenceVuriMap.keySet()) {
    		this.registerForEstablishPersistence(persistenceRootElement, transformationResult.elementToPersistenceVuriMap.get(persistenceRootElement));
    	}
    }
}
