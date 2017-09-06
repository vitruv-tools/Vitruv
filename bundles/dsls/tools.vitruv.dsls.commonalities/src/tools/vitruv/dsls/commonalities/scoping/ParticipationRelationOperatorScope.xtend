package tools.vitruv.dsls.commonalities.scoping

import com.google.inject.Inject
import com.google.inject.Singleton
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

@Singleton
class ParticipationRelationOperatorScope implements IScope {

	@Inject IJvmTypeProvider.Factory typeProviderFactory
	@Inject extension IQualifiedNameConverter qualifiedNameConverter;

	ResourceSet resourceSet

	static val DEFAULT_PACKAGE = 'tools.vitruv.extensions.dslruntime.commonalities.participationrelations'
	static val RELATION_NAME_ANNOTATION = DEFAULT_PACKAGE + '.RelationName'
	// TODO: Find a platform independent way to load relations without hardcoding them (e.g. all in a specific package)
	static val WELL_KNOWN_RELATIONS = #[
		DEFAULT_PACKAGE + '.ContainmentRelation'
	]
	Map<QualifiedName, JvmDeclaredType> relations

	def forResourceSet(ResourceSet resourceSet) {
		this.resourceSet = resourceSet
		relations = readRelations()
		return this
	}

	def private readRelations() {
		val extension typeProvider = typeProviderFactory.findOrCreateTypeProvider(resourceSet)
		newHashMap(
			WELL_KNOWN_RELATIONS.map[findTypeByName].filter(JvmDeclaredType).map[relationName.toQualifiedName -> it]
		)
	}

	override getAllElements() {
		relations.entrySet.map[EObjectDescription.create(key, value)]
	}

	override getElements(QualifiedName name) {
		#[getSingleElement(name)].filterNull
	}

	override getElements(EObject object) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	override getSingleElement(QualifiedName name) {
		relations.mapValues[EObjectDescription.create(name, it)].get(name)
	}

	override getSingleElement(EObject object) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
	}

	def private getRelationName(JvmDeclaredType type) {
		type.annotations
			 .filter [annotation.qualifiedName == RELATION_NAME_ANNOTATION].head
			 ?.explicitValues
			 ?.filter [operation.simpleName == 'value']
			 ?.filter(JvmStringAnnotationValue)?.head
			 ?.values?.head
	}
	
	override toString() {
		'''«ParticipationRelationOperatorScope.simpleName»«
		»[«FOR name : relations.keySet SEPARATOR ', '»«name.lastSegment»«ENDFOR»]'''
	}

}
