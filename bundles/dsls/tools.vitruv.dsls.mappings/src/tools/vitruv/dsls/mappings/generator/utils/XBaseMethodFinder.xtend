package tools.vitruv.dsls.mappings.generator.utils

import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.JvmMember
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider

class XBaseMethodFinder {

	private final static String PACKAGE = 'org.eclipse.xtext.xbase.lib.ObjectExtensions'

	public static def tripleEquals(RoutineTypeProvider typeProvider) {
		typeProvider.findXbaseMethod('operator_tripleEquals')
	}

	public static def tripleNotEquals(RoutineTypeProvider typeProvider) {
		typeProvider.findXbaseMethod('operator_tripleNotEquals')
	}

	public static def findXbaseMethod(RoutineTypeProvider typeProvider, String method) {
		(typeProvider.findTypeByName(PACKAGE) as JvmDeclaredType).members.findFirst [
			it.simpleName == method
		]
	}

	public static def findMetaclassMethod(RoutineTypeProvider typeProvider, MetaclassFeatureReference ref) {
		val package = ref.metaclass.instanceTypeName
		val type = typeProvider.findTypeByName(package) as JvmDeclaredType
		val name = '''get«ref.feature.name.toFirstUpper»«IF ref.feature.many»s«ENDIF»'''
		findMethod(type, name)
	}

	private static def JvmMember findMethod(JvmDeclaredType type, String member) {
		var method = type.members.findFirst[it.simpleName == member]
		if (method !== null) {
			return method
		}
		for (superTypeRef : type.superTypes) {
			val superType = superTypeRef.type
			if (superType instanceof JvmDeclaredType) {
				method = findMethod(superType, member)
				if (method !== null) {
					return method
				}
			}
		}
		null
	}

}
