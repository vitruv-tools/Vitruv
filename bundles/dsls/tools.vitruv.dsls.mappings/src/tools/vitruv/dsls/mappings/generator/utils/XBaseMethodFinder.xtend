package tools.vitruv.dsls.mappings.generator.utils

import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.JvmMember
import tools.vitruv.dsls.mirbase.mirBase.MetaclassFeatureReference
import tools.vitruv.dsls.reactions.builder.TypeProvider

class XBaseMethodFinder {

	val static String PACKAGE_OPTIONAL = 'java.util.Optional'
	val static String PACKAGE_LIST = 'java.util.List'
	val static String PACKAGE_OBJECT = 'org.eclipse.xtext.xbase.lib.ObjectExtensions'
	val static String PACKAGE_BOOLEAN = 'org.eclipse.xtext.xbase.lib.BooleanExtensions'
	val static String PACKAGE_ITERATOR = 'org.eclipse.xtext.xbase.lib.IteratorExtensions'

	static def optionalIsPresent(TypeProvider typeProvider) {
		typeProvider.findXbaseMethod(PACKAGE_OPTIONAL, 'isPresent')
	}

	static def optionalGet(TypeProvider typeProvider) {
		typeProvider.findXbaseMethod(PACKAGE_OPTIONAL, 'get')
	}

	// should find the correct filter method because it is listed first in the class
	static def listFilter(TypeProvider typeProvider) {
		typeProvider.findXbaseMethod(PACKAGE_ITERATOR, 'filter')
	}
	
	static def listContains(TypeProvider typeProvider) {
		typeProvider.findXbaseMethod(PACKAGE_LIST, 'contains')
	}

	// should find first add
	static def collectionAdd(TypeProvider typeProvider) {
		typeProvider.findXbaseMethod(PACKAGE_LIST, 'add')
	}

	static def and(TypeProvider typeProvider) {
		typeProvider.findXbaseMethod(PACKAGE_BOOLEAN, 'operator_and')
	}

	static def or(TypeProvider typeProvider) {
		typeProvider.findXbaseMethod(PACKAGE_BOOLEAN, 'operator_or')
	}

	static def tripleEquals(TypeProvider typeProvider) {
		typeProvider.findXbaseMethod(PACKAGE_OBJECT, 'operator_tripleEquals')
	}

	static def tripleNotEquals(TypeProvider typeProvider) {
		typeProvider.findXbaseMethod(PACKAGE_OBJECT, 'operator_tripleNotEquals')
	}

	static def findXbaseMethod(TypeProvider typeProvider, String pkg, String method) {
		val member = (typeProvider.findTypeByName(pkg) as JvmDeclaredType).members.findFirst [
			it.simpleName == method
		]
		if (member === null) {
			throw new MethodNotFoundException('''Could not find method «method» in type «pkg»''')
		}
		member
	}

	static def findMetaclassMethodGetter(TypeProvider typeProvider, EClass metaclass,
		EStructuralFeature feature) throws MethodNotFoundException {
		val name = '''get«feature.name.toFirstUpper»'''
		typeProvider.findMetaclassMethodCandidate(metaclass, name, feature.many)
	}

	static def findMetaclassMethodSetter(TypeProvider typeProvider, EClass metaclass,
		EStructuralFeature feature) throws MethodNotFoundException {
		val name = '''set«feature.name.toFirstUpper»'''
		typeProvider.findMetaclassMethodCandidate(metaclass, name, feature.many)
	}

	private static def findMetaclassMethodCandidate(TypeProvider typeProvider, EClass metaclass, String method,
		boolean many) {
		if (many) {
			try {
				return typeProvider.findMetaclassMethod(metaclass, method + "s")
			} catch (MethodNotFoundException e) {
				// some many-features dont have the "s", so just continue trying to retrieve 
			}
		}
		typeProvider.findMetaclassMethod(metaclass, method)
	}

	static def findMetaclassMethodGetter(TypeProvider typeProvider,
		MetaclassFeatureReference ref) throws MethodNotFoundException {
		typeProvider.findMetaclassMethodGetter(ref.metaclass, ref.feature)
	}

	static def findMetaclassMethod(TypeProvider typeProvider, EClass metaclass,
		String method) throws MethodNotFoundException {
		val package = metaclass.instanceTypeName
		val type = typeProvider.findTypeByName(package) as JvmDeclaredType
		findMethod(type, type, method)
	}

	private static def JvmMember findMethod(JvmDeclaredType type, JvmDeclaredType originalType,
		String member) throws MethodNotFoundException {
		var method = type.members.findFirst[it.simpleName == member]
		if (method !== null) {
			return method
		}
		for (superTypeRef : type.superTypes) {
			val superType = superTypeRef.type
			if (superType instanceof JvmDeclaredType) {
				try {
					method = findMethod(superType, originalType, member)
					if (method !== null) {
						return method
					}
				} catch (MethodNotFoundException e) {
					//did not find it in this super type either
				}
			}
		}
		//not found in members nor supertypes
		throw new MethodNotFoundException('''Could not find method «member» in type «originalType»''')
	}

	private static class MethodNotFoundException extends Exception {
		new(String msg) {
			super(msg)
		}
	}

}
