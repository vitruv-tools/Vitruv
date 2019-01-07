package tools.vitruv.testutils.activeannotations

import org.eclipse.xtend.lib.macro.Active
import java.lang.annotation.Target
import org.eclipse.xtend.lib.macro.AbstractClassProcessor
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration
import org.eclipse.xtend.lib.macro.TransformationContext
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.impl.EFactoryImpl
import java.lang.annotation.Retention
import org.eclipse.emf.ecore.EFactory

/**
 * Can be applied to a custom {@link EFactory} implementation to have EObjects’ identifier initialized with
 * {@link EcoreUtil#generateUUID}. When this annotation is added, all {@code create}-methods of the package that
 * return an instance of the {@code identifierMetaclass} will be overridden to return an instance where the 
 * {@code identifierFeature} is initialized to the return value of an invocation of {@link EcoreUtil#generateUUID}. 
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

	/**
	 * The feature that holds the identifier and will be initialized. Must be a feature of {@code identifierMetaclass}
	 */
	int identifierFeature
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
		val identifierFeatureId = annotation.getIntValue("identifierFeature")

		val createMethods = extendedFactory.declaredResolvedMethods.filter [
			declaration.simpleName.startsWith("create") && declaration.parameters.isEmpty &&
				identifierMetaclass.isAssignableFrom(declaration.returnType)
		]
		if (createMethods.isEmpty) {
			annotatedClass.addWarning("Could not find a single create method to override. Please check your arguments!")
		}

		for (createMethod : createMethods) {
			val createdMetaClass = createMethod.resolvedReturnType
			val metaClassImplName = createdMetaClass.name.removeFromEnd(createdMetaClass.simpleName) + "impl." +
				createdMetaClass.simpleName + "Impl"
			val metaClassImpl = context.newTypeReference(metaClassImplName)
			if (metaClassImpl === null) {
				annotatedClass.addError('''Cannot find the implementation class of «createdMetaClass.simpleName».«
					» Tried «metaClassImplName»''')
			}
			annotatedClass.addMethod(createMethod.declaration.simpleName) [
				returnType = createdMetaClass
				body = '''
					return new «metaClassImpl»() {
						private «createdMetaClass» __set_generated_id() {
							eSet(«identifierFeatureId», «EcoreUtil».generateUUID());
							return this;
						}
					}.__set_generated_id();
				'''
			]
		}
	}

	def removeFromEnd(String string, String substring) {
		val end = string.substring(string.length - substring.length, string.length)
		if (end != substring) throw new IllegalArgumentException('''«string» does not end with «substring»!''')
		return string.substring(0, string.length - substring.length)
	}
}
