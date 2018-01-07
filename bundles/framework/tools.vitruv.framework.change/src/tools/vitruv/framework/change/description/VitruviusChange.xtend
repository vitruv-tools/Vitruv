package tools.vitruv.framework.change.description

import java.util.List
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.URIHaving
import org.eclipse.emf.ecore.EObject
import tools.vitruv.framework.uuid.UuidResolver

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
	 * Resolves the change and applies it forward so that the model is in the state after the change afterwards.
	 * It has to be ensured that the model is in a state the change can be applied to before calling this method.
	 * If the change cannot be resolved or is already resolved, an Exception is thrown.
	 */
	def void resolveBeforeAndApplyForward(UuidResolver uuidResolver);
	
	/**
	 * Resolves the change and applies it backward so that the model is in the state before the change afterwards.
	 * It has to be ensured that the model is in a state the change can be applied to before calling this method.
	 * If the change cannot be resolved or is already resolved, an Exception is thrown.
	 */
	def void resolveAfterAndApplyBackward(UuidResolver uuidResolver);
	
	def void unresolveIfApplicable();
	
	def Iterable<EObject> getAffectedEObjects();
	
	def Iterable<String> getAffectedEObjectIds();
}