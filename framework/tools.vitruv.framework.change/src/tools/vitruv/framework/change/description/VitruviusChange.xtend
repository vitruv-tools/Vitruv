package tools.vitruv.framework.change.description

import java.util.List
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.util.datatypes.URIHaving
import tools.vitruv.framework.change.preparation.ChangeToEChangeConverter

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
	 * Indicates whether this or all contained changes are prepared and contain the {@link EChange}s
	 * that describe the change.
	 */
	def boolean isPrepared();

	/**
	 * Calls the given {@link ChangePreparer} to generate the {@link EChange}s for this change.
	 */
	def void prepare(ChangeToEChangeConverter  preparer);

	/** 
	 * Returns the {@link EChange}s that describe this change. Requires the change to be prepared so
	 * that the original change information is transformed into {@link EChange}s.  
	 */
	def List<EChange> getEChanges();

}
