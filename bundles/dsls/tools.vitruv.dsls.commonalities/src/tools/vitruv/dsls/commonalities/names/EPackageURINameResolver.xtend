package tools.vitruv.dsls.commonalities.names

import java.util.function.Predicate
import java.util.regex.Pattern
import org.eclipse.emf.ecore.EPackage

final class EPackageURINameResolver {
	
	private new() {}
	
	/**
	 * Predicate deciding whether a part of a model URI looks like a version number
	 */
	final static Predicate<String> IS_MODEL_VERSION_PART = Pattern.compile('[0-9]+(\\.[0-9]+)*').asPredicate

	/** 
	 * Constructs a suitable, friendly name out of a metamodel URI. Chooses the last
	 * part of the URI most of the time. However, some URIs contain a version number at
	 * the end, which will be skipped in favor of the next part from behind.
	 */	
	def static getPackageName(String ePackageURI) {
		val uriParts = ePackageURI.split('/')
		// starting from the end, skip over all parts that look like version numbers
		var i = uriParts.length - 1;
		for (; i >= 0 && IS_MODEL_VERSION_PART.test(uriParts.get(i)); i--) {
		}
		if (i >= 0) uriParts.get(i) else ePackageURI
	}
	
	def static getPackageName(EPackage ePackage) {
		ePackage?.eResource.URI.toString.packageName
	}	
}