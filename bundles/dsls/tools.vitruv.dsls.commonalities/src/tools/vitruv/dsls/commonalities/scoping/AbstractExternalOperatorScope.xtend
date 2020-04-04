package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Inject
import java.util.Map
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.JvmStringAnnotationValue
import org.eclipse.xtext.common.types.access.IJvmTypeProvider
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.xtext.scoping.IScope
import tools.vitruv.extensions.dslruntime.commonalities.operators.OperatorName

abstract class AbstractExternalOperatorScope implements IScope {

	static val OPERATOR_NAME_ANNOTATION = OperatorName.name

	@Inject IJvmTypeProvider.Factory typeProviderFactory
	@Inject extension IQualifiedNameConverter qualifiedNameConverter;

	ResourceSet resourceSet
	Map<QualifiedName, JvmDeclaredType> operators

	// TODO: Find a platform independent way to load relations without hardcoding them (e.g. all in a specific package,
	// or specify the packages via import statements)
	abstract def Iterable<String> getWellKnownOperators()

	def forResourceSet(ResourceSet resourceSet) {
		this.resourceSet = resourceSet
		operators = findOperators()
		return this
	}

	def private findOperators() {
		val extension typeProvider = typeProviderFactory.findOrCreateTypeProvider(resourceSet)
		val operators = wellKnownOperators.map[findTypeByName].filter(JvmDeclaredType).toMap [
			val operatorName = operatorName
			if (operatorName.isNullOrEmpty) {
				throw new IllegalStateException('''Could not find operator name for «it»''')
			}
			operatorName.toQualifiedName
		]
		return operators
	}

	override getAllElements() {
		operators.entrySet.map[EObjectDescription.create(key, value)]
	}

	override getElements(QualifiedName name) {
		#[getSingleElement(name)].filterNull
	}

	override getElements(EObject object) {
		throw new UnsupportedOperationException("I don't know what to do here!")
	}

	override getSingleElement(QualifiedName name) {
		operators.mapValues[EObjectDescription.create(name, it)].get(name)
	}

	override getSingleElement(EObject object) {
		getElements(object).head
	}

	def private getOperatorName(JvmDeclaredType operatorType) {
		operatorType.annotations
			.filter[annotation.qualifiedName == OPERATOR_NAME_ANNOTATION].head
			?.explicitValues
			?.filter[operation.simpleName == 'value']
			?.filter(JvmStringAnnotationValue)?.head
			?.values?.head
	}

	override toString() {
		'''«class.simpleName»: «operators.keySet»'''
	}
}
