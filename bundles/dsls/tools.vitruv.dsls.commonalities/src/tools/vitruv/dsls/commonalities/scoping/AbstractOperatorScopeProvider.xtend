package tools.vitruv.dsls.commonalities.scoping

import com.google.common.base.Predicate
import com.google.common.base.Predicates
import com.google.inject.Inject
import java.util.HashMap
import java.util.HashSet
import java.util.Map
import java.util.Set
import java.util.WeakHashMap
import org.apache.log4j.Logger
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.access.IJvmTypeProvider
import org.eclipse.xtext.common.types.xtext.AbstractTypeScopeProvider
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.scoping.IGlobalScopeProvider
import org.eclipse.xtext.scoping.impl.FilteringScope
import org.eclipse.xtext.scoping.impl.SimpleScope

import static extension tools.vitruv.dsls.commonalities.language.extensions.CommonalitiesLanguageModelExtensions.*

abstract class AbstractOperatorScopeProvider implements IGlobalScopeProvider {

	static val Logger logger = Logger.getLogger(AbstractOperatorScopeProvider)

	@Inject AbstractTypeScopeProvider typeScopeProvider
	@Inject IJvmTypeProvider.Factory typeProviderFactory
	@Inject extension IQualifiedNameConverter qualifiedNameConverter

	// Weak caching by ResourceSet: This ensures that the cache does not persist across Xtext builds.
	// TODO As long as the editor is open for some commonality file, content assist will use the same ResourceSet
	// instance.
	Map<ResourceSet, Map<QualifiedName, JvmDeclaredType>> operators = new WeakHashMap()

	protected abstract def String getOperatorTypeName()

	protected abstract def Iterable<String> getDefaultOperatorImports()

	private def isNamespaceImport(String operatorImport) {
		return operatorImport.endsWith('.*')
	}

	private def getDefaultOperatorTypeImports() {
		return defaultOperatorImports.filter[!isNamespaceImport]
	}

	private def getDefaultOperatorNamespaceImports() {
		return defaultOperatorImports.filter[isNamespaceImport]
	}

	override getScope(Resource resource, EReference reference, Predicate<IEObjectDescription> filter) {
		val commonalityFile = resource.optionalContainedCommonalityFile
		if (commonalityFile === null) {
			throw new IllegalStateException("resource must contain a commonality file")
		}

		val importedTypes = (defaultOperatorTypeImports + commonalityFile.operatorTypeImports.map [
			// TODO: This sometimes returns a JvmAnnotationType with null QN.
			importedType.getQualifiedName('.')
		]).filterNull.map[toQualifiedName].toList
		val importedNamespaces = (defaultOperatorNamespaceImports + commonalityFile.operatorNamespaceImports.map [
			importedNamespace
		]).map[toQualifiedName.skipLast(1)].toList // skips the '.*' segment at the end of each namespace import

		val Predicate<IEObjectDescription> importFilter = [
			val qualifiedName = (it.EObjectOrProxy as JvmDeclaredType).getQualifiedName('.').toQualifiedName
			if (importedTypes.exists[qualifiedName.equals(it)]) {
				return true
			}
			if (importedNamespaces.exists[qualifiedName.startsWith(it)]) {
				return true
			}
			return false
		]
		val combinedFilter = Predicates.and(importFilter, filter ?: Predicates.alwaysTrue)

		val resourceSet = resource.resourceSet
		return new FilteringScope(resourceSet.allOperatorsScope, combinedFilter)
	}

	private def getAllOperatorsScope(ResourceSet resourceSet) {
		val operatorsMap = operators.computeIfAbsent(resourceSet) [
			val operatorsMap = findOperators(resourceSet)
			logger.debug('''Found «operatorTypeName» operators: «operatorsMap.keySet.map[toString].toList»''')
			operatorsMap
		]
		return new SimpleScope(operatorsMap.entrySet.map[EObjectDescription.create(key, value)].toList)
	}

	private def findOperators(ResourceSet resourceSet) {
		val extension typeProvider = typeProviderFactory.findOrCreateTypeProvider(resourceSet)
		val typeScope = typeScopeProvider.createTypeScope(typeProvider, null)
		val allTypes = typeScope.allElements
		val Map<QualifiedName, JvmDeclaredType> operators = new HashMap()
		val Set<QualifiedName> foundTypes = new HashSet()
		allTypes
			// TODO This heuristic name filter fixes an issue that would otherwise causes the Eclipse runtime
			// application to freeze (probably due to a recursion caused by resolving the JVM types).
			.filter[qualifiedName.lastSegment.endsWith('Operator')]
			.filter [
				// We sometimes find the same type multiple times. This filter
				// ensures that we only handle it once.
				foundTypes.add(qualifiedName)
			]
			.map[EcoreUtil2.resolve(it.EObjectOrProxy, resourceSet)]
			.filter(JvmDeclaredType)
			.filter[isOperatorType]
			.map [
				val operatorName = operatorName
				if (operatorName.isNullOrEmpty) {
					return null
				} else {
					operatorName.toQualifiedName -> it
				}
			].filterNull
			.forEach [
				// Note: Operators with duplicate names silently replace each other:
				// TODO Add validation to avoid importing operators with name clashes
				operators.put(key, value)
			]
		return operators
	}

	protected abstract def Class<?> getOperatorBaseType()

	protected def boolean isOperatorType(JvmDeclaredType type) {
		val operatorBaseTypeName = operatorBaseType.name
		return type.allSuperTypes.exists[it.qualifiedName == operatorBaseTypeName]
	}

	private def Set<JvmDeclaredType> getAllSuperTypes(JvmDeclaredType type) {
		val declaredSuperTypes = type.superTypes.map[it.type].filter(JvmDeclaredType)
		// toSet: Filters duplicates in case the same type occurs multiple times inside the inheritance hierarchy.
		return (declaredSuperTypes + declaredSuperTypes.flatMap[allSuperTypes]).toSet
	}

	protected abstract def String getOperatorName(JvmDeclaredType operatorType)
}
