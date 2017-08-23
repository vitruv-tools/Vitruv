package tools.vitruv.framework.change.description

import java.util.List
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.URIHaving
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.change.uuid.UuidResolver

/** 
 * Base interface for all kinds of changes in Vitruvius.
 * 
 * @author Heiko Klare
 */
interface VitruviusChange extends URIHaving {
	/** 
	 * Returns whether the change contains any concrete change or consists only of composite ones.
	 */
	def boolean containsConcreteChange();
	
	/** 
	 * Validates the change by checking if at least one concrete change is contained and
	 * the URIs of all contained changes are same. 
	 */
	def boolean validate();
	
	/** 
	 * Returns the {@link EChange}s that describe this change. Requires the change to be prepared so
	 * that the original change information is transformed into {@link EChange}s.  
	 */
	def List<EChange> getEChanges();

	/**
	 * Applies this change backward so that the model is in the state before the change afterwards.
	 * It has to be ensured that the model is in the state right after the change before calling this method.
	 * If the method has already been called without calling {@link #applyForward applyForward} in the meantime, an 
	 * {@link IllegalStateException} will be thrown.
	 * 
	 * @throws IllegalStateException if this method was already called without calling {@link applyForward} in the meantime
	 */
	def void applyBackward() throws IllegalStateException;
	
	/**
	 * Applies this change forward so that the model is in the state after the change afterwards.
	 * It has to be ensured that the model is in the state right before the change before calling this method.
	 * This especially means that {@link #applyBackward applyBackward} has to be called before.
	 * Otherwise or if the method has been called without calling {@link #applyBackward applyBackward} in the meantime, an 
	 * {@link IllegalStateException} will be thrown.
	 * 
	 * @throws IllegalStateException if this method was called before without calling {@link applyBackward} in the meantime
	 */
	def void applyForward() throws IllegalStateException;
	
	def void resolveBeforeAndApplyForward(UuidResolver uuidResolver);
	
	def void resolveAfterAndApplyBackward(UuidResolver uuidResolver);
	
	def void applyBackwardIfLegacy();
	
	def Iterable<EObject> getAffectedEObjects();
}