package tools.vitruv.change.utils.activeannotations

import org.eclipse.xtend.lib.macro.Active
import java.lang.annotation.Target
import org.eclipse.xtend.lib.macro.AbstractClassProcessor
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration
import org.eclipse.xtend.lib.macro.TransformationContext
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.impl.EFactoryImpl
import java.lang.annotation.Retention
import org.eclipse.emf.ecore.EFactory
import org.eclipse.xtend.lib.macro.declaration.MethodDeclaration

import static org.eclipse.xtend.lib.macro.declaration.Visibility.PRIVATE
import org.eclipse.xtend2.lib.StringConcatenationClient

/**
 * Can be applied to a custom {@link EFactory} implementation to have EObjects’ identifier initialized with
 * {@link EcoreUtil#generateUUID}. When this annotation is added to a factory, it will be modified in a way such that
 * all metaclasses inheriting from the {@code identifierMetaclass} will have their id attribute initialized with the 
 * result of invoking {@link EcoreUtil#generateUUID}
 */
@Active(WithGeneratedIdsProcessor)
@Target(TYPE)
@Retention(SOURCE)
annotation WithGeneratedRandomIds {
	/**
	 * The generated Java type of the metaclass that declares the identifier feature. All subclasses of this metaclasses
	 * will have their identifier initialized.
	 */
	Class<?> identifierMetaclass
}

class WithGeneratedIdsProcessor extends AbstractClassProcessor {

	override doTransform(MutableClassDeclaration annotatedClass, @Extension TransformationContext context) {
		val extendedFactory = annotatedClass.extendedClass
		if (extendedFactory === null || !EFactoryImpl.newTypeReference().isAssignableFrom(extendedFactory)) {
			annotatedClass.addError( ''''A class annotated with «WithGeneratedRandomIds.simpleName» must extend an extension «
				»of«EFactoryImpl.simpleName».''')
			return
		}

		val annotation = annotatedClass.findAnnotation(findTypeGlobally(WithGeneratedRandomIds))
		if (annotation === null) {
			annotatedClass.
				addError('''Cannot find «WithGeneratedRandomIds.simpleName» annotation on «annotatedClass.simpleName»''')
			return
		}
		val identifierMetaclass = annotation.getClassValue("identifierMetaclass")

		val createMethods = extendedFactory.declaredResolvedMethods.filter [
			declaration.simpleName.startsWith("create") && declaration.parameters.isEmpty &&
				identifierMetaclass.isAssignableFrom(declaration.returnType)
		]
		if (createMethods.isEmpty) {
			annotatedClass.addWarning("Could not find a single create method to override. Please check your arguments!")
		}

		for (createMethod : createMethods) {
			val existingMethod = annotatedClass.declaredMethods.findFirst [
				it.simpleName == createMethod.declaration.simpleName && it.parameters.isEmpty && !it.static
			]

			val String creatorMethod = if (existingMethod !== null) {
					var newName = existingMethod.simpleName
					do {
						newName = "_" + newName
					} while (annotatedClass.declaredMethods.existsWithName(newName))
					annotatedClass.addMethod(newName) [
						visibility = PRIVATE
						returnType = existingMethod.returnType
						body = existingMethod.body
						primarySourceElement = existingMethod
					]
					newName
				} else {
					"super." + createMethod.declaration.simpleName
				}

			val createdMetaClass = createMethod.resolvedReturnType
			val StringConcatenationClient idSetterBody = '''
				final «createdMetaClass» created = «creatorMethod»();
				«EcoreUtil».setID(created, «EcoreUtil».generateUUID());
				return created;
			'''
			if (existingMethod !== null) {
				existingMethod.body = idSetterBody
			} else {
				annotatedClass.addMethod(createMethod.declaration.simpleName) [
					returnType = createdMetaClass
					body = idSetterBody
					primarySourceElement = annotatedClass
				]
			}
		}
	}

	def removeFromEnd(String string, String substring) {
		val end = string.substring(string.length - substring.length, string.length)
		if (end != substring) throw new IllegalArgumentException('''«string» does not end with «substring»!''')
		return string.substring(0, string.length - substring.length)
	}

	def boolean existsWithName(Iterable<? extends MethodDeclaration> methods, String name) {
		return methods.exists[it.simpleName == name]
	}
}
