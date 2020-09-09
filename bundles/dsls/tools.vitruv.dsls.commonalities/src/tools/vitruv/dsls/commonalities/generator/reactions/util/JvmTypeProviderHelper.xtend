package tools.vitruv.dsls.commonalities.generator.reactions.util

import edu.kit.ipd.sdq.activextendannotations.Utility
import java.util.Collection
import java.util.function.Predicate
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.JvmExecutable
import org.eclipse.xtext.common.types.JvmField
import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.xtext.common.types.JvmIdentifiableElement
import org.eclipse.xtext.common.types.JvmMember
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.common.types.JvmType
import org.eclipse.xtext.common.types.JvmTypeParameter
import org.eclipse.xtext.common.types.access.IJvmTypeProvider

@Utility
class JvmTypeProviderHelper {

	static def findType(IJvmTypeProvider typeProvider, Class<?> clazz) {
		val result = typeProvider.findTypeByName(clazz.name)
		if (result !== null) {
			return result
		}
		throw new NoSuchJvmElementException('''Could not find type “«clazz.name»”!''')
	}

	static def findDeclaredType(IJvmTypeProvider typeProvider, Class<?> clazz) {
		val result = typeProvider.findType(clazz)
		if (result instanceof JvmDeclaredType) {
			return result
		}
		throw new NoSuchJvmElementException('''Could not find declared type “«clazz.name»”!''')
	}

	private static def checkGenericType(JvmType type) {
		if (type instanceof JvmGenericType) {
			return type
		}
		if (type === null) {
			throw new NoSuchJvmElementException('''Type not found!''')
		}
		throw new NoSuchJvmElementException('''Expected ‹«type.qualifiedName»› to resolve to a JvmGenericType!''')
	}

	static def JvmField findAttribute(JvmDeclaredType declaredType, String attributeName) {
		val result = (declaredType.members.filter[simpleName == attributeName].filter(JvmField) +
			declaredType.superTypes.map[type].filter(JvmDeclaredType).map[findAttribute(attributeName)]
		).head
		if (result !== null) {
			return result
		}
		throw new NoSuchJvmElementException('''Could not find the attribute “«attributeName»” in ‹«declaredType.qualifiedName»›!''')
	}

	static def findMethod(JvmDeclaredType type, String methodName, Class<?>... parameterTypeRestrictions) {
		type.findMethod(methodName, -1, parameterTypeRestrictions)
	}

	static def findImplementedMethod(JvmDeclaredType declaredType, String methodName, int parameterCount,
		Class<?>... parameterTypeRestrictions) {
		declaredType.getImplementedMethodList(methodName, parameterCount, parameterTypeRestrictions)
			.onlyResultFor("implemented method", methodName, parameterTypeRestrictions, parameterCount, declaredType)
	}

	static def findOptionalImplementedMethod(JvmDeclaredType declaredType, String methodName,
		Class<?>... parameterTypeRestrictions) {
		declaredType.findOptionalImplementedMethod(methodName, -1, parameterTypeRestrictions)
	}

	static def findOptionalImplementedMethod(JvmDeclaredType declaredType, String methodName,
		int parameterCount, Class<?>... parameterTypeRestrictions) {
		declaredType.getImplementedMethodList(methodName, parameterCount, parameterTypeRestrictions)
			.maxOneResultFor("implemented method", methodName, parameterTypeRestrictions, parameterCount, declaredType)
			.head
	}

	private static def getImplementedMethodList(JvmDeclaredType declaredType, String methodName, int parameterCount,
		Class<?>... parameterTypeRestrictions) {
		val extension argumentChecker = new ArgumentRestrictionChecker(parameterTypeRestrictions, parameterCount)
		declaredType.members.filter(JvmOperation).filter [
			simpleName == methodName && confirmsToArgumentRestrictions
		].toList
	}

	static def findImplementedMethod(JvmDeclaredType type, String methodName,
		Class<?>... parameterTypeRestrictions) {
		type.findImplementedMethod(methodName, -1, parameterTypeRestrictions)
	}

	static def findMethod(JvmDeclaredType type, String methodName, int parameterCount,
		Class<?>... parameterTypeRestrictions) {
		val extension argumentChecker = new ArgumentRestrictionChecker(parameterTypeRestrictions, parameterCount)
		val results = type.findAllMethods [
			simpleName == methodName && confirmsToArgumentRestrictions
		].toList
		return results.onlyResultFor("method", methodName, parameterTypeRestrictions, parameterCount, type)
	}

	private static def <T extends JvmMember> onlyResultFor(Collection<T> result, String memberType, String memberName,
		Class<?>[] parameterTypeRestrictions, int parameterCount, JvmDeclaredType type) {
		result.maxOneResultFor(memberType, memberName, parameterTypeRestrictions, parameterCount, type)
			.atLeastOneResultFor(memberType, memberName, parameterTypeRestrictions, parameterCount, type)
	}

	private static def <T extends JvmMember> atLeastOneResultFor(Collection<T> result, String memberType,
		String memberName, Class<?>[] parameterTypeRestrictions, int parameterCount, JvmDeclaredType type) {
		if (result.size === 0) {
			val description = description(memberType, memberName, parameterTypeRestrictions, parameterCount, type)
			throw new NoSuchJvmElementException('''Could not find a «description»!''')
		}
		return result.get(0)
	}

	private static def <T extends JvmMember> maxOneResultFor(Collection<T> result, String memberType, String memberName,
		Class<?>[] parameterTypeRestrictions, int parameterCount, JvmDeclaredType type) {
		if (result.size > 1) {
			val description = description(memberType, memberName, parameterTypeRestrictions, parameterCount, type)
			throw new NoSuchJvmElementException('''
				Found more than one «description»:
				«FOR resultElement : result»
					«resultElement.identifier»
				«ENDFOR»
			''')
		}
		return result
	}

	private static def description(String memberType, String memberName, Class<?>[] parameterTypeRestrictions,
		int parameterCount, JvmDeclaredType type) '''
		«memberType»«IF memberName !== null» “«memberName»”«ENDIF»«
			»«IF parameterTypeRestrictions.length > 0»«
				» with the parameters («
				»«FOR restriction : parameterTypeRestrictions SEPARATOR ", "»«
					»‹«IF (restriction == TypeVariable)»Type Variable«ELSE»«restriction»«ENDIF»›«
				»«ENDFOR»)«
			»«ENDIF»«
			»«IF parameterCount != -1»«» having exactly «parameterCount» parameters«ENDIF»«
			» in ‹«type.qualifiedName»›!
	'''

	private static def Iterable<JvmOperation> findAllMethods(JvmDeclaredType declaredType,
		Predicate<JvmOperation> inclusionCondition) {
		val thisMethods = declaredType.members.filter(JvmOperation).filter(inclusionCondition).toList
		val superMethods = declaredType.superTypes.map[type].filter(JvmDeclaredType).flatMap [
			findAllMethods(inclusionCondition)
		// exclude overridden methods. This does not find overriding methods
		// that declare super type arguments. That is, however, not so bad as
		// those can easily be queried by their parameter types
		].filter[superMethod|thisMethods.findFirst[hasSameSignatureAs(superMethod)] === null]

		return thisMethods + superMethods
	}

	private static def hasSameSignatureAs(JvmOperation a, JvmOperation b) {
		if (a.simpleName != b.simpleName) return false
		if (a.parameters.size != b.parameters.size) return false
		if (a.static != b.static) return false
		val bIter = b.parameters.iterator()
		for (aParameter : a.parameters) {
			if (aParameter.parameterType.qualifiedName != bIter.next.parameterType.qualifiedName) {
				return false
			}
		}
		return true
	}

	static def findMethod(IJvmTypeProvider typeProvider, String typeQualifiedName, String methodName,
		Class<?>... parameterTypeRestrictions) {
		typeProvider.findMethod(typeQualifiedName, methodName, -1, parameterTypeRestrictions)
	}

	static def findMethod(IJvmTypeProvider typeProvider, String typeQualifiedName, String methodName,
		int parameterCount, Class<?>... parameterTypeRestrictions) {
		typeProvider.findTypeByName(typeQualifiedName).checkGenericType
			.findMethod(methodName, parameterCount, parameterTypeRestrictions)
	}

	static def findMethod(IJvmTypeProvider typeProvider, Class<?> clazz, String methodName,
		Class<?>... parameterTypeRestrictions) {
		typeProvider.findMethod(clazz, methodName, -1, parameterTypeRestrictions)
	}

	static def findMethod(IJvmTypeProvider typeProvider, Class<?> clazz, String methodName, int parameterCount,
		Class<?>... parameterTypeRestrictions) {
		typeProvider.findType(clazz).checkGenericType.findMethod(methodName, parameterCount, parameterTypeRestrictions)
	}

	static def findAttribute(IJvmTypeProvider typeProvider, Class<?> clazz, String attributeName) {
		typeProvider.findType(clazz).checkGenericType.findAttribute(attributeName)
	}

	static def findConstructor(JvmDeclaredType type, Class<?>... parameterTypeRestrictions) {
		findConstructor(type, -1, parameterTypeRestrictions)
	}

	static def findNoArgsConstructor(JvmDeclaredType type) {
		findConstructor(type, 0)
	}

	static def findConstructor(JvmDeclaredType type, int parameterCount,
		Class<?>... parameterTypeRestrictions) {
		val extension argumentChecker = new ArgumentRestrictionChecker(parameterTypeRestrictions, parameterCount)
		val results = type.declaredConstructors.filter[confirmsToArgumentRestrictions].toList
		return results.onlyResultFor('constructor', null, parameterTypeRestrictions, parameterCount, type)
	}

	static def Class<?> typeVariable() {
		TypeVariable
	}

	/**
	 * Helper method to get array class references, as you cannot write
	 * "Type[].class" in Xtend.
	 */
	static def Class<?> getArrayClass(Class<?> componentClass) {
		// see Class#getName and
		// https://stackoverflow.com/questions/4901128/obtaining-the-array-class-of-a-component-type
		val className = switch componentClass {
			case componentClass.isArray: '[' + componentClass.name
			case boolean: '[Z'
			case byte: '[B'
			case char: '[C'
			case double: '[D'
			case float: '[F'
			case int: '[I'
			case long: '[J'
			case short: '[S'
			default: '[L' + componentClass.name + ';'
		}
		return Class.forName(className, false, componentClass.classLoader)
	}

	private static class ArgumentRestrictionChecker {

		val Class<?>[] parameterTypeRestrictions
		val int parameterCount

		private new(Class<?>[] parameterTypeRestrictions, int parameterCount) {
			this.parameterTypeRestrictions = parameterTypeRestrictions
			this.parameterCount = parameterCount
		}

		private def confirmsToArgumentRestrictions(JvmExecutable executable) {
			if (parameterCount !== -1 && executable.parameters.size != parameterCount) return false
			val parameterIter = executable.parameters.iterator()
			for (restriction : parameterTypeRestrictions) {
				if (!parameterIter.hasNext) {
					return false
				}
				val parameterType = parameterIter.next.parameterType.type
				if (parameterType.qualifiedName != restriction.qualifiedName &&
					!(parameterType instanceof JvmTypeParameter && restriction == TypeVariable)) {
					return false
				}
			}
			return true
		}

		/**
		 * Gets a name for the given class to use for the comparison with
		 * {@link JvmIdentifiableElement#getQualifiedName qualified names} of
		 * JVM types.
		 * <p>
		 * Unlike {@link Class#getCanonicalName} this uses <code>$</code> as
		 * delimiter for inner classes. And unlike {@link Class#getName} this
		 * appends <code>[]</code> to the component type name of array types.
		 */
		private def String getQualifiedName(Class<?> clazz) {
			if (clazz.isArray) {
				return clazz.getComponentType().qualifiedName + "[]"
			} else {
				return clazz.name
			}
		}
	}

	private static class TypeVariable {
	}
}

class NoSuchJvmElementException extends IllegalStateException {

	new(String message) {
		super(message)
	}
}
