package tools.vitruvius.applications.jmljava.synchronizers.java

import com.google.inject.Inject
import tools.vitruvius.domains.jml.language.jML.FormalParameterDecl
import tools.vitruvius.domains.jml.language.jML.JMLPackage
import tools.vitruvius.domains.jml.language.jML.JMLSpecifiedElement
import tools.vitruvius.applications.jmljava.helper.java.shadowcopy.ShadowCopyFactory
import tools.vitruvius.applications.jmljava.synchronizers.SynchronisationAbortedListener
import tools.vitruvius.framework.util.command.TransformationResult
import java.util.ArrayList
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.emftext.language.java.parameters.Parameter
import org.emftext.language.java.parameters.ParametersPackage
import org.emftext.language.java.types.TypeReference

import static extension tools.vitruvius.framework.correspondence.CorrespondenceModelUtil.*

class JavaParameterTransformations extends Java2JMLTransformationBase {
	static val LOGGER = Logger.getLogger(JavaParameterTransformations)

	@Inject
	new(ShadowCopyFactory shadowCopyFactory, SynchronisationAbortedListener synchronisationAbortedListener) {
		super(shadowCopyFactory, synchronisationAbortedListener)
	}

	override protected getLogger() {
		return LOGGER
	}

	override getClassOfMappedEObject() {
		return Parameter
	}

	override setCorrespondenceForFeatures() {
		featureCorrespondenceMap.put(ParametersPackage.eINSTANCE.parameter.getEStructuralFeature("name"),
			JMLPackage.eINSTANCE.formalParameterDecl_Identifier)
		featureCorrespondenceMap.put(ParametersPackage.eINSTANCE.parameter.getEStructuralFeature("typeReference"),
			JMLPackage.eINSTANCE.typed_Type)
	}

	override replaceNonRootEObjectSingle(EObject newAffectedEObject, EObject oldAffectedEObject,
		EReference affectedReference, EObject oldValue, EObject newValue) {
		val changedObjects = new ArrayList<EObject>()
		val javaParameter = newAffectedEObject as Parameter

		LOGGER.trace(
			"UpdateSingleValuedNonContainmentEReference\t" + newAffectedEObject + "." + affectedReference.name + " (" +
				oldValue + " -> " + newValue + ")")

		val jmlFeature = featureCorrespondenceMap.claimValueForKey(affectedReference)

		if (jmlFeature == JMLPackage.eINSTANCE.typed_Type) {
				CommonSynchronizerTransformations.replaceNonRootEObjectSingleType(javaParameter,
					oldValue as TypeReference, newValue as TypeReference, correspondenceModel)
		}
		return new TransformationResult
	}

	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		val changedObjects = new ArrayList<EObject>()

		LOGGER.trace(
			"UpdateSingleValuedEAttribute\t" + affectedEObject + "." + affectedAttribute.name + " (" + oldValue + " -> " +
				newValue + ")")

		val jmlFeature = featureCorrespondenceMap.claimValueForKey(affectedAttribute)

		if (jmlFeature == JMLPackage.eINSTANCE.formalParameterDecl_Identifier) {
			val jmlParameterDecls = correspondenceModel.getCorrespondingEObjects(affectedEObject).filter(
				FormalParameterDecl)
			for (jmlParameterDecl : jmlParameterDecls) {
				LOGGER.trace("Updating " + jmlParameterDecl)

				if (jmlParameterDecl.getParentOfType(JMLSpecifiedElement).jmlSpecifications.size > 0) {
					changedObjects.addAll(
						(affectedEObject as Parameter).renameInAllJMLSpecifications(newValue as String))
				}

				val jmlParameterDeclTUIDOld = correspondenceModel.calculateTUIDFromEObject(jmlParameterDecl)
				jmlParameterDecl.identifier = newValue as String
				correspondenceModel.updateTUID(jmlParameterDeclTUIDOld, correspondenceModel.calculateTUIDFromEObject(jmlParameterDecl))
				changedObjects.add(jmlParameterDecl)
			}
		}
		return new TransformationResult
	}

}
