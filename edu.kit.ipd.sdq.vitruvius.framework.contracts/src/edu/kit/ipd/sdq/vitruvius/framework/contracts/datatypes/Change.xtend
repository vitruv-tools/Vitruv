package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes

/** 
 * Base interface for all kinds of changes in Vitruvius.
 * 
 * @author Heiko Klare
 */
interface Change extends URIHaving {
	/** 
	 * Returns whether the change contains any concrete change or consists only of composite ones.
	 */
	def boolean containsConcreteChange();
	
	/** 
	 * Validates the change by checking of at least one concrete change is contained and
	 * the URIs of all contained changes are same. 
	 */
	def boolean validate();
}