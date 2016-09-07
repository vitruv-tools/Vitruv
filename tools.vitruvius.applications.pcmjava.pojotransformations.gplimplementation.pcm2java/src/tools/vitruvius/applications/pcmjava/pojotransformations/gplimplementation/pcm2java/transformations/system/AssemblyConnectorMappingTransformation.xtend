package tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.pcm2java.transformations.system

import tools.vitruvius.applications.pcmjava.pojotransformations.gplimplementation.util.transformationexecutor.EmptyEObjectMappingTransformation
import java.util.List
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.classifiers.Class
import org.emftext.language.java.instantiations.NewConstructorCall
import org.emftext.language.java.members.Constructor
import org.emftext.language.java.members.Field
import org.emftext.language.java.parameters.Parameter
import org.palladiosimulator.pcm.core.composition.AssemblyConnector
import org.palladiosimulator.pcm.core.composition.AssemblyContext
import org.palladiosimulator.pcm.core.entity.NamedElement

import static extension tools.vitruvius.framework.util.bridges.CollectionBridge.*
import static extension tools.vitruvius.framework.correspondence.CorrespondenceModelUtil.*
import tools.vitruvius.applications.pcmjava.util.pcm2java.PCM2JaMoPPUtils

class AssemblyConnectorMappingTransformation extends EmptyEObjectMappingTransformation {

	private val Logger logger = Logger.getLogger(AssemblyConnectorMappingTransformation.simpleName)

	override getClassOfMappedEObject() {
		return AssemblyConnector
	}

	override setCorrespondenceForFeatures() {
	}

	/**
	 * called when an assembly connector has been created.
	 * update the constructor call for the field
	 */
	override createEObject(EObject eObject) {
		val assemblyConnector = eObject as AssemblyConnector
		val pcmElement = eObject as NamedElement
		val assemblyContext = assemblyConnector.providingAssemblyContext_AssemblyConnector
		updateConstructorForCorrespondingField(assemblyContext, pcmElement)
	}
	
	private def EObject[] updateConstructorForCorrespondingField(AssemblyContext assemblyContext, NamedElement pcmElement) {
		try {
			val field = correspondenceModel.getCorrespondingEObjectsByType(assemblyContext, Field).claimOne
			val fields = (field.containingConcreteClassifier as Class).fields
			var List<Parameter> parameters = newArrayList()
			val constructors = correspondenceModel.getCorrespondingEObjectsByType(assemblyContext, Constructor)
			if (!constructors.nullOrEmpty) {
				parameters = constructors.get(0).parameters
			}
			val constructorCall = correspondenceModel.
				getCorrespondingEObjectsByType(assemblyContext, NewConstructorCall).claimOne
			PCM2JaMoPPUtils.updateArgumentsOfConstructorCall(field, fields, parameters, constructorCall)
		} catch (RuntimeException re) {
			logger.trace("Can not create corresponding objects for PCMElement " + pcmElement.entityName)
		}
		return null
	}

	override removeEObject(EObject eObject) {
		
	}

}
