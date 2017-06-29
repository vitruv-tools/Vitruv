package tools.vitruv.dsls.mapping.helpers

import tools.vitruv.dsls.mapping.helpers.JavaGeneratorHelper.ImportHelper
import edu.kit.ipd.sdq.commons.util.java.Pair
import java.util.Collection
import java.util.List
import java.util.Map
import java.util.function.BiFunction
import org.eclipse.xtext.generator.IFileSystemAccess

import static extension tools.vitruv.framework.util.bridges.JavaHelper.*

class TemplateGenerator {
	// simple templating mechanism to allow for addition of code
	private Map<Object, List<BiFunction<ImportHelper, TemplateGenerator, CharSequence>>> templateKey2content = newHashMap
	private Collection<Pair<CharSequence, BiFunction<ImportHelper, TemplateGenerator, CharSequence>>> templates = newHashSet

	public def void addTemplateJavaFile(CharSequence fqn,
		BiFunction<ImportHelper, TemplateGenerator, CharSequence> generatorFunction) {
		templates.add(new Pair(fqn, generatorFunction))
	}

	public def void generateAllTemplates(IFileSystemAccess fsa) {
		for (template : templates) {
			val fqn = template.first
			val generatorFunction = template.second

			var content = JavaGeneratorHelper.generate(fqn.toString.classNameToPackageName, [ ih | generatorFunction.apply(ih, this) ])
			fsa.generateFile(fqn.toString.classNameToJavaPath, content)
		}
	}

	public def List<BiFunction<ImportHelper, TemplateGenerator, CharSequence>> getTemplateContent(Object key) {
		println('''requested key: «key»''')
		templateKey2content.getOrPut(key, [newArrayList])
	}

	public def void appendToTemplateExpression(Object key, BiFunction<ImportHelper, TemplateGenerator, CharSequence> content) {
		getTemplateContent(key).add(content)
	}
	
	public def void prependToTemplateExpression(Object key, BiFunction<ImportHelper, TemplateGenerator, CharSequence> content) {
		getTemplateContent(key).add(0, content)
	}
	
	public def CharSequence expandTemplate(ImportHelper ih, Object key) {
		templateKey2content.getOrDefault(key, #[]).map[it.apply(ih, this)].join
	}
}