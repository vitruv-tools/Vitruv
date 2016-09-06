package edu.kit.ipd.sdq.vitruvius.applications.jmljava.helper.java.shadowcopy;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil.UnresolvedProxyCrossReferencer;
import org.emftext.commons.layout.LayoutInformation;

/**
 * An unresolved proxy cross reference finder for JaMoPP. It is basically a
 * @UnresolvedProxyCrossReferencer, but it ignores some missing references,
 * which are usually notinteresting. This includes missing references in layout
 * information and unresolved references to classes for the standard library,
 * which are not imported (e.g. Object or String).
 */
public class JaMoPPUnresolvedProxyCrossReferencer extends
		UnresolvedProxyCrossReferencer {

	private static final long serialVersionUID = -3080512676646941926L;
	private static final Collection<String> IGNORED_CLASSNAMES = Arrays.asList(
			"Object", "String");

	/**
	 * Constructs a unresolved proxy cross referencer for a resource set.
	 * 
	 * @param resourceSet
	 *            The resource set to check.
	 */
	protected JaMoPPUnresolvedProxyCrossReferencer(ResourceSet resourceSet) {
		super(resourceSet);
	}

	/**
	 * Finds and returns unresolved cross references in the given resource set.
	 * 
	 * @param resourceSet
	 *            The resource set to check.
	 * @return A map of a unresolved reference target object to all of its
	 *         references, where it could not be resolved.
	 */
	public static Map<EObject, Collection<EStructuralFeature.Setting>> find(
			ResourceSet resourceSet) {
		return new JaMoPPUnresolvedProxyCrossReferencer(resourceSet)
				.findUnresolvedProxyCrossReferences();
	}

	@Override
	protected boolean crossReference(EObject eObject, EReference eReference,
			EObject crossReferencedEObject) {
		if (!super.crossReference(eObject, eReference, crossReferencedEObject)) {
			return false;
		}

		// ignore objecs, which JaMoPP can never resolve
		if (crossReferencedEObject instanceof org.emftext.language.java.classifiers.Class) {
			org.emftext.language.java.classifiers.Class crossReferencedClass = (org.emftext.language.java.classifiers.Class) crossReferencedEObject;
			// TODO check more aspects than name
			if (IGNORED_CLASSNAMES.contains(crossReferencedClass.getName())) {
				return false;
			}
		}

		// ignore layout information
		if (eObject instanceof LayoutInformation) {
			return false;
		}

		return true;
	}

}
