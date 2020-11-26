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

/**
 * Creates methods that are shortcuts for the creator methods of the provided {@link #factory}.
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

	override doTransform(MutableClassDeclaration annotatedClass, @Extension TransformationContext context) {
		val annotation = annotatedClass.findAnnotation(findTypeGlobally(ModelCreators))
		if (annotation === null) {
			annotatedClass.
				addError('''Cannot find «ModelCreators.simpleName» annotation on «annotatedClass.simpleName»''')
			return
		}

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
	}

	def private static removePrefix(String target, String prefix) {
		if (target.startsWith(prefix)) target.substring(prefix.length) else target
	}
}
