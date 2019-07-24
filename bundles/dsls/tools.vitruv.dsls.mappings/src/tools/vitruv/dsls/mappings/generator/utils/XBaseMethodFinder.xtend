package tools.vitruv.dsls.mappings.generator.utils

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.JvmMember
import org.eclipse.xtext.xbase.XExpression
import org.eclipse.xtext.xbase.XbaseFactory
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.reactions.builder.FluentRoutineBuilder.RoutineTypeProvider

class XBaseMethodFinder {

	private final static String PACKAGE_OBJECT = 'org.eclipse.xtext.xbase.lib.ObjectExtensions'
	private final static String PACKAGE_BOOLEAN = 'org.eclipse.xtext.xbase.lib.BooleanExtensions'
	private final static String PACKAGE_ITERATOR = 'org.eclipse.xtext.xbase.lib.IteratorExtensions'

	// should find the correct filter method because it is listed first in the class
	public static def listFilter(RoutineTypeProvider typeProvider) {
		typeProvider.findXbaseMethod(PACKAGE_ITERATOR, 'filter')
	}
	
	// should find first add
	public static def collectionAdd(RoutineTypeProvider typeProvider) {
		typeProvider.findXbaseMethod(PACKAGE_ITERATOR, 'operator_add')
	}

	public static def and(RoutineTypeProvider typeProvider) {
		typeProvider.findXbaseMethod(PACKAGE_BOOLEAN, 'operator_and')
	}

	public static def tripleEquals(RoutineTypeProvider typeProvider) {
		typeProvider.findXbaseMethod(PACKAGE_OBJECT, 'operator_tripleEquals')
	}

	public static def tripleNotEquals(RoutineTypeProvider typeProvider) {
		typeProvider.findXbaseMethod(PACKAGE_OBJECT, 'operator_tripleNotEquals')
	}

	public static def andChain(RoutineTypeProvider provider, XExpression... expressions) {
		if (expressions.empty) {
			return XbaseFactory.eINSTANCE.createXBooleanLiteral => [
				isTrue = true
			]
		}
		var leftExpression = expressions.get(0)
		for (expression : expressions.drop(1)) {
			val andExpression = XbaseFactory.eINSTANCE.createXBinaryOperation
			andExpression.leftOperand = leftExpression
			andExpression.rightOperand = expression
			andExpression.feature = provider.and
			leftExpression = andExpression
		}
		return leftExpression
	}

	public static def findXbaseMethod(RoutineTypeProvider typeProvider, String pkg, String method) {
		(typeProvider.findTypeByName(pkg) as JvmDeclaredType).members.findFirst [
			it.simpleName == method
		]
	}

	public static def findMetaclassMethod(RoutineTypeProvider typeProvider, EClass metaclass,
		EStructuralFeature feature) {
		val name = '''get«feature.name.toFirstUpper»«IF feature.many»s«ENDIF»'''
		typeProvider.findMetaclassMethod(metaclass, name)
	}

	public static def findMetaclassMethod(RoutineTypeProvider typeProvider, MetaclassFeatureReference ref) {
		typeProvider.findMetaclassMethod(ref.metaclass, ref.feature)
	}

	public static def findMetaclassMethod(RoutineTypeProvider typeProvider, EClass metaclass, String method) {
		val package = metaclass.instanceTypeName
		val type = typeProvider.findTypeByName(package) as JvmDeclaredType
		findMethod(type, method)
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
