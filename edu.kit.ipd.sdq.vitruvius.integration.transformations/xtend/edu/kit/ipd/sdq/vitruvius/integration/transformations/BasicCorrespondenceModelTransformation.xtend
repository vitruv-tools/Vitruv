package edu.kit.ipd.sdq.vitruvius.integration.transformations

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.correspondence.Correspondence
import edu.kit.ipd.sdq.vitruvius.framework.util.bridges.EMFBridge
import java.util.HashSet
import org.apache.log4j.Logger
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.IPath
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject

import static extension edu.kit.ipd.sdq.vitruvius.framework.contracts.util.datatypes.CorrespondenceInstanceUtil.*
import static extension edu.kit.ipd.sdq.vitruvius.framework.util.bridges.CollectionBridge.*
import edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes.CorrespondenceModel

/**
 * Class that provides general methods for creating correspondences between two metamodels. 
 * Should be extended by custom transformations.
 * 
 * @author Benjamin Hettwer
 */
abstract class BasicCorrespondenceModelTransformation implements ICreateCorrespondenceModel { 

	// Used to avoid duplicated entries
	private HashSet<String> existingEntries = new HashSet; 
	protected Logger logger = Logger.getRootLogger
	
	/**
	 * Returns the current used {@link CorrespondenceInstance}
	 */
	public abstract def CorrespondenceModel getCorrespondenceInstance()

	protected def Correspondence addCorrespondence(EObject pcmObject, EObject jamoppObject) {
		return addCorrespondence(pcmObject, jamoppObject, null);
	}

	/**
	 * Creates an {@link EObjectCorrespondence} between the given EObjects
	 * and adds it to the {@link CorrespondenceInstance}
	 */
	protected def Correspondence addCorrespondence(EObject objectA, EObject objectB, Correspondence parent) {
		if (objectA == null || objectB == null)
			throw new IllegalArgumentException("Corresponding elements must not be null!")

		// deresolve Objects
		var deresolvedA = deresolveIfNesessary(objectA)
		var deresolvedB = deresolveIfNesessary(objectB)

		// check if the correspondence was already created, because the SCDM may contain duplicate entries
		var identifier = getCorrespondenceInstance.calculateTUIDFromEObject(deresolvedA).toString +
			getCorrespondenceInstance.calculateTUIDFromEObject(deresolvedB).toString
		if (!existingEntries.contains(identifier)) {
			var correspondence = getCorrespondenceInstance.createAndAddCorrespondence(deresolvedA, deresolvedB);
			existingEntries.add(identifier);
			logger.info("Created Correspondence for element: " + objectA + " and Element: " + objectB);
			return correspondence;
		}
	}

	/**
	 * Converts the absolute resource URI of given EObject to platform URI
	 * or does nothing if it already has one.
	 */
	private def EObject deresolveIfNesessary(EObject object) {
		var uri = object.eResource.URI
		if (!uri.platform) {
			var base = URI.createFileURI(
				ResourcesPlugin.getWorkspace().getRoot().getLocation().toString() + IPath.SEPARATOR)
			var relativeUri = uri.deresolve(base)
			object.eResource.URI = EMFBridge.createPlatformResourceURI(relativeUri.toString)
		}
		return object
	}

	/**
	 * Returns the SameTypeCorrespondence for the given eObjects a and b and throws a
     * {@link RuntimeException} if there is no such correspondence. Note that a has to be an element
     * of metamodel a and b an instance of metamodel b.
	 */
	protected def Correspondence getUniqueSameTypeCorrespondence(EObject objectA, EObject objectB) {
		var deresolvedA = deresolveIfNesessary(objectA)
		var deresolvedB = deresolveIfNesessary(objectB)
		return getCorrespondenceInstance.claimUniqueCorrespondence(deresolvedA.toList, deresolvedB.toList)
	}

}
