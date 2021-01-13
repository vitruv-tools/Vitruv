package tools.vitruv.testutils.activeannotations

import org.eclipse.xtend.lib.macro.Active
import java.lang.annotation.Target
import org.eclipse.xtend.lib.macro.AbstractClassProcessor
import org.eclipse.xtend.lib.macro.declaration.MutableClassDeclaration
import org.eclipse.xtend.lib.macro.TransformationContext
import java.lang.annotation.Retention
import org.eclipse.emf.ecore.EFactory
import java.util.HashMap
import static com.google.common.base.Preconditions.checkArgument
import org.eclipse.emf.ecore.EClass
import org.eclipse.xtend.lib.macro.declaration.ClassDeclaration
import org.eclipse.xtend.lib.macro.RegisterGlobalsContext
import org.junit.jupiter.params.converter.ArgumentConverter
import org.eclipse.emf.ecore.EObject
import org.junit.jupiter.api.^extension.ParameterContext
import org.junit.jupiter.params.converter.ArgumentConversionException
import org.eclipse.emf.ecore.EClassifier
import static com.google.common.base.Preconditions.checkNotNull
import static org.eclipse.xtend.lib.macro.services.Problem.Severity.ERROR
import static org.eclipse.xtend.lib.macro.declaration.Visibility.PRIVATE
import com.google.common.base.Preconditions

/**
 * Creates methods that are shortcuts for the creator methods of the provided {@link #factory}. Additionally
 * creates:
 * 
 * <ul>
 * <li>An {@link ArgumentConverter} called {@code NewEObject} for creating new instances of {@link EObject}s from their 
 * {@link EClass}’ {@linkplain EClass#getName name}.
 * <li> An {@link ArgumentConverter} called {@code Classifier} for getting an EClassifier by its
 *  {@linkplain EClassifier#getName name}.
 * </ul>
 */
@Active(ModelCreatorsProcessor)
@Target(TYPE)
@Retention(SOURCE)
annotation ModelCreators {
	/**
	 * The factory to copy the create methods of.
	 */
	Class<? extends EFactory> factory
	/**
	 * Whether to make the created shortcuts methods static.
	 */
	boolean staticCreators = false
	/**
	 * A prefix to strip from the factory’s create methods’ names. “{@code create}” will always be stripped before
	 * removing the prefix configured here.
	 */
	String stripPrefix = ""
	/**
	 * A prefix to add to all shortcut methods.
	 */
	String prefix = ""
	/**
	 * Replacements for method names. Every odd element is a name to replace that will be replaced by the following even
	 * element. These replacements are performed before adding the prefix according to {@link #prefix}, but after
	 * removing the prefixes “{@code create}” and {@link #stripPrefix}.
	 */
	String[] replace = #[]
}

class ModelCreatorsProcessor extends AbstractClassProcessor {
	static val NEW_EOBJECT_ARGUMENT_CONVERTER = "NewEObject"
	static val CLASSIFIER_ARGUMENT_CONVERTER = "Classifier"

	private def checkPreconditions(ClassDeclaration annotatedClass, extension TransformationContext context) {
		try {
			checkNotNull(newTypeReference(Preconditions))
		} catch (NullPointerException | ClassNotFoundException | NoClassDefFoundError e) {
			annotatedClass.addError("Please add Guava to the classpath!")
		}

		try {
			checkNotNull(newTypeReference(ParameterContext))
		} catch (NullPointerException | ClassNotFoundException | NoClassDefFoundError e) {
			annotatedClass.addError("Please add the JUnit Jupiter API to the classpath!")
		}

		try {
			checkNotNull(newTypeReference(ArgumentConverter))
		} catch (NullPointerException | ClassNotFoundException | NoClassDefFoundError e) {
			annotatedClass.addError("Please add JUnit 5 Params to the classpath!")
		}
	}

	override doRegisterGlobals(ClassDeclaration annotatedClass, extension RegisterGlobalsContext context) {
		context.registerClass('''«annotatedClass.qualifiedName».«NEW_EOBJECT_ARGUMENT_CONVERTER»''')
		context.registerClass('''«annotatedClass.qualifiedName».«CLASSIFIER_ARGUMENT_CONVERTER»''')
	}

	override doTransform(MutableClassDeclaration annotatedClass, extension TransformationContext context) {
		checkPreconditions(annotatedClass, context)
		val annotation = annotatedClass.findAnnotation(findTypeGlobally(ModelCreators))
		if (annotation === null) {
			annotatedClass.
				addError('''Cannot find «ModelCreators.simpleName» annotation on «annotatedClass.simpleName»''')
		}
		if (annotatedClass.problems.exists[severity == ERROR]) return;

		val factory = annotation.getClassValue("factory")
		val createStatic = annotation.getBooleanValue("staticCreators")
		val stripPrefix = annotation.getStringValue("stripPrefix")
		val prefix = annotation.getStringValue("prefix")
		val rawReplacements = annotation.getStringArrayValue("replace")
		checkArgument(rawReplacements.size % 2 == 0, '''The replace array must have an even number of elements!''')
		val replacements = new HashMap<String, String>()
		for (var i = 0; i < rawReplacements.size; i += 2) {
			replacements.put(rawReplacements.get(i), rawReplacements.get(i + 1))
		}

		factory.declaredResolvedMethods.map[declaration].filter [
			simpleName.startsWith("create") && parameters.isEmpty
		].forEach [ createMethod |
			val baseName = createMethod.simpleName.removePrefix("create").removePrefix(stripPrefix)
			val targetName = (prefix + replacements.getOrDefault(baseName, baseName).toFirstUpper())
			annotatedClass.addMethod(targetName) [
				static = createStatic
				returnType = createMethod.returnType
				body = '''return «factory».eINSTANCE.«createMethod.simpleName»();'''
				primarySourceElement = annotatedClass
			]
		]

		annotatedClass.addMethod("_getClassifier") [
			returnType = newTypeReference(EClassifier)
			static = true
			visibility = PRIVATE
			addParameter("classifierName", newTypeReference(String))
			body = '''
				return «Preconditions».checkNotNull(
					«factory».eINSTANCE.getEPackage().getEClassifier(classifierName),
					"There is no classifier called '%s' in '%s'!", classifierName, «factory».eINSTANCE.getEPackage().getName()
				);
			'''
		]

		annotatedClass.addMethod("classifier") [
			returnType = newTypeReference(EClassifier)
			addParameter("classifierName", newTypeReference(String))
			body = '''return _getClassifier(classifierName);'''
		]

		annotatedClass.addMethod("_createInstance") [
			returnType = newTypeReference(EObject)
			static = true
			visibility = PRIVATE
			addParameter("className", newTypeReference(String))
			body = '''
				«EClassifier» requestedClassifier = _getClassifier(className);
				«Preconditions».checkArgument(
					requestedClassifier instanceof «EClass»,
					"%s is not an EClass and can thus not be instantiated!", className
				);
				return «factory».eINSTANCE.create((«EClass») requestedClassifier);
			'''
		]

		annotatedClass.addMethod("create") [
			returnType = newTypeReference(EObject)
			addParameter("className", newTypeReference(String))
			body = '''return _createInstance(className);'''
		]

		annotatedClass.addMethod("create") [
			val type = addTypeParameter('M', newTypeReference(EObject))
			returnType = type.newSelfTypeReference
			addParameter("clazz", newTypeReference(Class, newWildcardTypeReference(type.newSelfTypeReference)))
			body = '''return clazz.cast(_createInstance(clazz.getSimpleName()));'''
		]

		if (annotatedClass.declaredConstructors.isEmpty) {
			annotatedClass.addConstructor [
				visibility = PRIVATE
			]
		}

		findClass('''«annotatedClass.qualifiedName».«NEW_EOBJECT_ARGUMENT_CONVERTER»''') => [
			implementedInterfaces = #[newTypeReference(ArgumentConverter)]
			addMethod("convert") [
				returnType = newTypeReference(EObject)
				addParameter("source", newTypeReference(Object))
				addParameter("context", newTypeReference(ParameterContext))
				exceptions = #[newTypeReference(ArgumentConversionException)]
				body = '''
					try {
						return _createInstance((String) source);
					} catch (IllegalArgumentException | NullPointerException e) {
						throw new «ArgumentConversionException»(e.getMessage(), e);
					}
				'''
			]
		]

		findClass('''«annotatedClass.qualifiedName».«CLASSIFIER_ARGUMENT_CONVERTER»''') => [
			implementedInterfaces = #[newTypeReference(ArgumentConverter)]
			addMethod("convert") [
				returnType = newTypeReference(EObject)
				addParameter("source", newTypeReference(Object))
				addParameter("context", newTypeReference(ParameterContext))
				exceptions = #[newTypeReference(ArgumentConversionException)]
				body = '''
					try {
						return _getClassifier((String) source);
					} catch (NullPointerException e) {
						throw new «ArgumentConversionException»(e.getMessage(), e);
					}
				'''
			]

		]
	}

	def private static removePrefix(String target, String prefix) {
		if (target.startsWith(prefix)) target.substring(prefix.length) else target
	}
}
