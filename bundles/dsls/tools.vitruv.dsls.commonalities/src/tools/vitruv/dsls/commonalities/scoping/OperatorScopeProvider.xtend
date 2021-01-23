package tools.vitruv.dsls.commonalities.scoping

import org.eclipse.emf.ecore.EReference

import org.eclipse.xtext.scoping.impl.ImportedNamespaceAwareLocalScopeProvider
import java.util.List
import tools.vitruv.extensions.dslruntime.commonalities.operators.CommonalitiesOperatorConventions
import static extension edu.kit.ipd.sdq.commons.util.java.lang.IterableUtil.*
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.scoping.IScope
import tools.vitruv.dsls.commonalities.generator.util.guice.InjectingFactoryBase
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

final class OperatorScopeProvider extends ImportedNamespaceAwareLocalScopeProvider {
	// TODO: We currently don’t filter by operator type, which can lead to collisions between operators.
	// Going forward, there should be no distinction between different operator types, and hence the
	// need to differentiate should disappear.
	// Thus: either remove different operator types or filter the scope by this class.
	var Class<?> operatorBaseType
	var List<String> translatedImports
	
	private new(Class<?> operatorBaseType, List<String> defaultImports) {
		this.operatorBaseType = operatorBaseType
		this.translatedImports = defaultImports.mapFixed [
			CommonalitiesOperatorConventions.toOperatorTypeQualifiedName(it)
		]
	}
	
	override protected getImplicitImports(boolean ignoreCase) {
		translatedImports.map [createImportedNamespaceResolver(it, ignoreCase)]
	}
	
	override protected IScope getLocalElementsScope(IScope parent, EObject context, EReference reference) {
		// no new operators can be defined locally
		parent
	}
	
	override protected IScope getResourceScope(IScope parent, EObject context, EReference reference) {
		// only find the import declarations
		val ignoreCase = isIgnoreCase(reference)
		val namespaceResolvers = getImportedNamespaceResolvers(context, ignoreCase)
		if (!namespaceResolvers.isEmpty()) {
			createImportScope(parent, namespaceResolvers, null, reference.getEReferenceType(), ignoreCase)
		} else {
			parent
		}
	}
	
	override getWildCard() {
		'_'
	}
	
	override protected isRelativeImport() {
		false
	}
	
	static class Factory extends InjectingFactoryBase {
		def OptionalImportsFactory forOperatorType(Class<?> operatorType) {
			new OptionalImportsFactory(operatorType).injectMembers
		}
	}
	
	@FinalFieldsConstructor
	static class OptionalImportsFactory extends InjectingFactoryBase {
		val Class<?> operatorBaseType
		
		def withDefaultImports(String... defaultImports) {
			new OperatorScopeProvider(operatorBaseType, defaultImports).injectMembers
		}
		
		def build() {
			withDefaultImports()
		}
	}
}
