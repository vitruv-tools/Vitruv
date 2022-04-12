package tools.vitruv.dsls.reactions.codegen.helper

import org.eclipse.xtext.nodemodel.util.NodeModelUtils
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XBlockExpression
import tools.vitruv.dsls.common.elements.MetaclassReference
import tools.vitruv.dsls.reactions.language.toplevelelements.ReactionsFile
import org.eclipse.emf.ecore.resource.Resource
import static com.google.common.base.Preconditions.*
import tools.vitruv.dsls.reactions.api.generator.ReferenceClassNameAdapter
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EClassifier
import edu.kit.ipd.sdq.activextendannotations.Utility

@Utility
class ReactionsLanguageHelper {
	
	static def dispatch String getXBlockExpressionText(XExpression expression) '''
	{
		«NodeModelUtils.getNode(expression).text»
	}'''

	static def dispatch String getXBlockExpressionText(XBlockExpression expression) {
		NodeModelUtils.getNode(expression).text;
	}

	private static def getOptionalReferenceAdapter(EObject element) {
		element.eAdapters.findFirst [isAdapterForType(ReferenceClassNameAdapter)] as ReferenceClassNameAdapter
	}

	static def getJavaClassName(EClassifier eClassifier) {
		eClassifier.optionalReferenceAdapter?.qualifiedNameForReference ?: eClassifier.instanceClassName
	}
	
	static def getRuntimeClassName(EObject element) {
		element.optionalReferenceAdapter?.qualifiedNameForReference ?: element.class.canonicalName
	}

	static def getJavaClassName(MetaclassReference metaclassReference) {
		metaclassReference.metaclass?.javaClassName;
	}

	def static ReactionsFile getReactionsFile(Resource resource) {
		val firstContentElement = resource?.contents?.head
		checkArgument(firstContentElement instanceof ReactionsFile,
			"The given resource %s was expected to contain a ReactionsFile element! (was %s)", resource,
			firstContentElement?.class?.simpleName);

		return firstContentElement as ReactionsFile;
	}
	
	def static getOptionalReactionsFile(Resource resource) {
		val firstContentElement = resource?.contents?.head
		if (firstContentElement instanceof ReactionsFile) {
			firstContentElement
		} else {
			null
		}
	}
	
	def static containsReactionsFile(Resource resource) {
		resource.optionalReactionsFile !== null
	}
	
}
