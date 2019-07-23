package tools.vitruv.dsls.mappings.generator.utils

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.JvmMember
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider

class XBaseMethodFinder {

	private final static String PACKAGE_OBJECT = 'org.eclipse.xtext.xbase.lib.ObjectExtensions'
	private final static String PACKAGE_BOOLEAN = 'org.eclipse.xtext.xbase.lib.BooleanExtensions'

	
	public static def and(RoutineTypeProvider typeProvider) {
		typeProvider.findXbaseMethod(PACKAGE_BOOLEAN, 'operator_and')
	}

	public static def tripleEquals(RoutineTypeProvider typeProvider) {
		typeProvider.findXbaseMethod(PACKAGE_OBJECT, 'operator_tripleEquals')
	}

	public static def tripleNotEquals(RoutineTypeProvider typeProvider) {
		typeProvider.findXbaseMethod(PACKAGE_OBJECT, 'operator_tripleNotEquals')
	}

	public static def findXbaseMethod(RoutineTypeProvider typeProvider, String pkg, String method) {
		(typeProvider.findTypeByName(pkg) as JvmDeclaredType).members.findFirst [
			it.simpleName == method
		]
	}

	public static def findFeatureMethod(RoutineTypeProvider provider, EClass metaclass, EStructuralFeature feature) {
		throw new UnsupportedOperationException("TODO: auto-generated method stub")
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
